package eye.engine.nik.gameframework;

import org.junit.Test;

import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XBinFileReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XBinStreamReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextFileReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextStreamReader;

/**
 * Created by nik on 19.06.16.
 */
public class XFileStreamReaderTests {
    @Test
    public void getIntTest() {
        try {
            XBinStreamReader reader = new XBinFileReader(new byte[]{0, 0, 0, 1});
            int a = reader.getInt();
            assert  a == 1;
        } catch (GameIOException e) {
            assert false;
        }
    }
}
