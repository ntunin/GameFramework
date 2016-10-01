package eye.engine.nik.gameframework;

import org.junit.Test;

import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XStringStreamReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextStreamReader;

/**
 * Created by Николай on 30.07.2016.
 */
public class XStringReaderTest {
    @Test
    public void getCharTest() {
        String testString = "abc";
        XTextStreamReader reader = new XStringStreamReader(testString);
        assert testString(reader, testString);
    }


    @Test
    public void indexOutTest() {
        String testString1 = "abc";
        String testString2 = "abcd";
        XTextStreamReader reader = new XStringStreamReader(testString1);
        assert !testString(reader, testString2);

    }
    private boolean testString(XTextStreamReader reader, String testString) {

        for(int i = 0; i < testString.length(); i++) {
            try {
                char c = reader.getChar();
                if(c != testString.charAt(i)) return false;
            } catch (GameIOException e) {
                return false;
            }
        }

        return true;
    }
}
