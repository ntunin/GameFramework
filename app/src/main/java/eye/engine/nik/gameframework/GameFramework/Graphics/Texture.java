package eye.engine.nik.gameframework.GameFramework.Graphics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.opengl.GLUtils;


import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import eye.engine.nik.gameframework.GameFramework.AndroidGameFramework.Graphics.TGAReader;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.screen.GLGame;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.screen.GLGraphics;
import eye.engine.nik.gameframework.GameFramework.IO.FileIO;

/**
 * Created by nik on 07.04.16.
 */
public class Texture {
    GLGraphics glGraphics;
    FileIO fileIO;
    String fileName;
    int textureId;
    int minFilter;
    int magFilter;
    public int width;
    public int height;
    boolean mipmapped;

    public Texture(String fileName) {
        this(fileName, false);
    }
    public Texture(String fileName, boolean mipmapped) {
        GLGame glGame = GLGame.current();
        this.glGraphics = glGame.getGLGraphics();
        this.fileIO = glGame.getFileIO();
        this.fileName = fileName;
        this.mipmapped = mipmapped;
        load();
    }

    private void load() {
        GL10 gl = glGraphics.getGL();
        int[] textureIds = new int[1];
        gl.glGenTextures(1, textureIds, 0);
        textureId = textureIds[0];
        InputStream in = null;
        try {
            in = fileIO.readAsset(fileName);
            String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
            Bitmap bitmap = null;
            switch (extension) {
                case "png": {
                    bitmap = BitmapFactory.decodeStream(in);
                }
                case "tga": {
                    byte [] buffer = new byte[in.available()];
                    in.read(buffer);
                    int [] pixels = TGAReader.read(buffer, TGAReader.ARGB);
                    int width = TGAReader.getWidth(buffer);
                    int height = TGAReader.getHeight(buffer);

                    bitmap = Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.ARGB_8888);
                }

                if (mipmapped) {
                    createMipmaps(gl, bitmap);
                } else {
                    gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
                    GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
                    setFilters(GL10.GL_NEAREST, GL10.GL_NEAREST);
                    gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
                    width = bitmap.getWidth();
                    height = bitmap.getHeight();
                    bitmap.recycle();
                }
            }

            in.close();
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load texture '" + fileName
                    + "'", e);
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }
    }

    private void createMipmaps(GL10 gl, Bitmap bitmap) {
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        setFilters(GL10.GL_LINEAR_MIPMAP_NEAREST, GL10.GL_LINEAR);
        int level = 0;
        int newWidth = width;
        int newHeight = height;
        while (true) {
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, level, bitmap, 0);
            newWidth = newWidth / 2;
            newHeight = newHeight / 2;
            if (newWidth <= 0)
                break;
            Bitmap newBitmap = Bitmap.createBitmap(newWidth, newHeight,
                    bitmap.getConfig());
            Canvas canvas = new Canvas(newBitmap);
            canvas.drawBitmap(bitmap,
                    new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),
                    new Rect(0, 0, newWidth, newHeight), null);
            bitmap.recycle();
            bitmap = newBitmap;
            level++;
        }
        gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
        bitmap.recycle();
    }
    public void reload() {
        load();
        bind();
        setFilters(minFilter, magFilter);
        glGraphics.getGL().glBindTexture(GL10.GL_TEXTURE_2D, 0);
    }
    public void setFilters(int minFilter, int magFilter) {
        this.minFilter = minFilter;
        this.magFilter = magFilter;
        GL10 gl = glGraphics.getGL();
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                minFilter);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                magFilter);
    }
    public void bind() {
        GL10 gl = glGraphics.getGL();
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
    }
    public void dispose() {
        GL10 gl = glGraphics.getGL();
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
        int[] textureIds = { textureId };
        gl.glDeleteTextures(1, textureIds, 0);
    }
}
