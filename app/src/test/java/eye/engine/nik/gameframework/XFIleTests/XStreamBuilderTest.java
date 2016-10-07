package eye.engine.nik.gameframework.XFIleTests;

import org.junit.Test;

import eye.engine.nik.gameframework.GameFramework.Graphics.Frame;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder.XStreamBuilder;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XStringStreamReader;

/**
 * Created by nikolay on 05.10.16.
 */

public class XStreamBuilderTest {
    @Test
    public void initTest() {
        Frame f = XStreamBuilder.read(new XStringStreamReader(
                "template Vector {" +
                        "<3d82ab5e-62da-11cf-ab39-0020af71e433>" +
                        "FLOAT x;\n" +
                        "FLOAT y;\n" +
                        "FLOAT z;\n" +
                        "}\n" +
                        "Vector a {" +
                        "1;2;3;" +
                        "}" +
                        "Vector b {" +
                        "5;6;7;" +
                        "}"
        ));
    }
}
