package eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import eye.engine.nik.gameframework.GameFramework.ERRNO;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.GLGame;
import eye.engine.nik.gameframework.GameFramework.IO.FileIO;
import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;

/**
 * Created by Николай on 30.07.2016.
 */
public class XTextFileReader extends XStringStreamReader {

    public XTextFileReader(String path) {
        super("");
        try {
            FileIO io = GLGame.current().getFileIO();
            InputStream stream = io.readAsset (path);
            byte[] buffer = new byte[stream.available()];
            stream.read(buffer);
            stream.close();
            ByteBuffer in = ByteBuffer.wrap(buffer);
            in.order(ByteOrder.LITTLE_ENDIAN);
            this.stream = new StringBuilder(new String(in.array()));

        } catch (FileNotFoundException e) {
            ERRNO.write("File not found");
        } catch (IOException e) {
            ERRNO.write("Cannot read file");
        } catch (Exception e) {
            ERRNO.write("Exception");
        }
    }
}
