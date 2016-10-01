package eye.engine.nik.gameframework.GameFramework.AndroidGameFramework.Graphics;

import android.graphics.Bitmap;

import eye.engine.nik.gameframework.GameFramework.Graphics.Graphics;
import eye.engine.nik.gameframework.GameFramework.Graphics.Pixmap;

public class AndroidPixmap implements Pixmap {
    Bitmap bitmap;
    Graphics.PixmapFormat format;

    public AndroidPixmap(Bitmap bitmap, Graphics.PixmapFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    @Override
    public Graphics.PixmapFormat getFormat() {
        return format;
    }

    @Override
    public void dispose() {
        bitmap.recycle();
    }
}