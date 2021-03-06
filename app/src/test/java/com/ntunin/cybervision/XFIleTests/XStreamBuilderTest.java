package com.ntunin.cybervision.XFIleTests;

import com.ntunin.cybervision.virtualmanagement.crvactor.CRVSkin.CRVSkin;
import com.ntunin.cybervision.io.xfile.xsreambuilder.XStreamBuilder;
import com.ntunin.cybervision.io.xfile.xstreamreader.XStringStreamReader;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Created by nikolay on 05.10.16.
 */

public class XStreamBuilderTest {

    @Test
    public void complexDefinitionTest() {
        CRVSkin f = XStreamBuilder.read(new XStringStreamReader(
                "template     Vector     {" +
                        " <f6f23f45-7686-11cf-8f52-0040333594a3>\n" +
                        "    FLOAT x;\n    " +
                        "    FLOAT y;\n\t\r   " +
                        "    \n\t\r   FLOAT z;\n\t\r\n" +
                 "}\n\t\r" +
                "template      \n\t\r   Complex {\n" +
                        " <f6f23f45-7686-11cf-8f52-0040333594a3>\n" +
                        "DWORD count;\n" +
                        "    \narray  \t\r  FLOAT  \t\r    matrix[count];\n\n\n" +
                        "WORD faceCount;\n\t\r" +
                        "STRING definition;\n\t\r" +
                        "array Vector    \n\t\r    vectors[3];\n\t\r" +
                 "}\n\n" +
                "Complex m {" +
                        "16;" +
                        "1.000000,   0.000000, \n\r 0.000000,   0.000000,   0.000000,-0.008237,-0.999966,0.000000,0.000000,0.999966,-0.008237,0.000000,0.458263,0.000000,37.731718,1.000000;" +
                        "\n\t\r    245;" +
                        "\"this is definition\"     \n   \t     \r;" +
                        "1;2;3;,\n\t\r4;5;6;,\t\n\r7;8;9;;" +
                "}\n\n\n\t\t\t\r \r\r  \n   " +
                "Vector a {" +
                        "123;\n, 456;\n, 789\n;" +
                        "}"
        ));
    }

    @Test
    public void simpleDynamicTest() {
        new XStringStreamReader(
                "template Frame {" +
                        " <f6f23f45-7686-11cf-8f52-0040333594a3>\n" +
                "[...]" +
                "}" +
                "template Vector {" +
                        " <f6f23f45-7686-11cf-8f52-0040333594a3>\n" +
                        "FLOAT x;" +
                        "FLOAT y;" +
                        "FLOAT z;" +
                "}" +
                "Frame f {" +
                        "Vector a {" +
                            "1; 2; 3;" +
                        "}" +
                        "Vector b {" +
                            "5; 6; 7;" +
                        "}" +
                "}"
        );
    }

    @Test
    public void fileTest() {
        try {
            FileInputStream inputStream = new FileInputStream("animation.X");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            XStringStreamReader stream = new XStringStreamReader(new String(buffer));
            CRVSkin f = XStreamBuilder.read(stream);
        } catch (FileNotFoundException e) {
            assert false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
