package eye.engine.nik.gameframework.XFIleTests;

import org.junit.Test;

import eye.engine.nik.gameframework.GameFramework.Delegate;
import eye.engine.nik.gameframework.GameFramework.DelegateException;
import eye.engine.nik.gameframework.GameFramework.Graphics.Frame;
import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;

import java.util.ArrayList;
import java.util.List;

import eye.engine.nik.gameframework.GameFramework.IO.XFile.XFile;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XDocument;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XStringStreamReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader.XFloatReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader.XIntReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader.XDocumentRegister;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader.XTemplate;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XWatchBatcher;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextStreamReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XWatcher;

/**
 * Created by nikolay on 10.08.16.
 */
public class XValueTests {
    XTextStreamReader reader;
    XWatchBatcher builder;

    @Test

    public void templateCreationTest() {

        reader = new XStringStreamReader(
                        "template MeshFace {\n" +
                        " <3d82ab5f-62da-11cf-ab39-0020af71e433>\n" +
                        " DWORD nFaceVertexIndices;\n" +
                        " array DWORD faceVertexIndices[nFaceVertexIndices];\n" +
                        "}");
        XWatchBatcher builder = getBuilder("template", "createTemplate");
        try {
            builder.read();
        } catch (GameIOException e) {
            assert false;
        }
    }

    public void createTemplate() {
        try {
            new XTemplate(null, reader);
        } catch (GameIOException e) {
            assert false;
        }
    }

    @Test

    public void templateReadingTest() {

        XDocumentRegister table = XDocumentRegister.table();
        try {
            table.register("FLOAT", new XFloatReader());
            table.register("DWORD", new XIntReader());
        } catch (GameIOException e) {
            assert false;
        }

        reader = new XStringStreamReader(
                "template Matrix4x4 {\n" +
                " <f6f23f45-7686-11cf-8f52-0040333594a3>\n" +
                " array FLOAT matrix[16];\n" +
                "}" +
                "Matrix4x4 {" +
                "   1.000000,0.000000,0.000000,0.000000,0.000000,1.000000,0.000000,0.000000,0.000000,0.000000,1.000000,0.000000,-1.396093,0.000000,1.417028,1.000000;;" +
                "}");
        try {
            table.register(new XWatcher("template", new Delegate(this, "testTemplate")));
        } catch (DelegateException e) {
            assert false;
        }
        builder = new XWatchBatcher(reader) {
            @Override
            protected List<XWatcher> getPrimitiveWatchers() throws DelegateException {
                XDocumentRegister table = XDocumentRegister.table();
                return new ArrayList<>(table.getWatchers());
            }
        };
        try {
            try {
                builder.setStreamOut(new Delegate(this, "templateReadingOut"));
            } catch (DelegateException e) {
                assert false;
            }
            builder.read();
        } catch (GameIOException e) {
            assert false;
        }
    }


    @Test
    public void runtimeTemplateReadingTest() {

        XDocumentRegister table = XDocumentRegister.table();
        try {
            table.register("FLOAT", new XFloatReader());
            table.register("DWORD", new XIntReader());
        } catch (GameIOException e) {
            assert false;
        }

        reader = new XStringStreamReader(
                        "template Vector {\n" +
                        " <3d82ab5e-62da-11cf-ab39-0020af71e433>\n" +
                        " FLOAT x;\n" +
                        " FLOAT y;\n" +
                        " FLOAT z;\n" +
                        "}" +
                        "template Mesh {\n" +
                        " <3d82ab44-62da-11cf-ab39-0020af71e433>\n" +
                        " DWORD nVertices;\n" +
                        " array Vector vertices[nVertices];\n" +
                        "}" +
                        "Mesh mesh_B {\n" +
                                "  24;\n" +
                                "  -5.291189;0.000000;-8.104310;,\n" +
                                "  -5.291189;0.000000;8.104310;,\n" +
                                "  5.291189;0.000000;8.104310;,\n" +
                                "  5.291189;0.000000;-8.104310;,\n" +
                                "  -5.291189;12.547115;-8.104310;,\n" +
                                "  5.291189;12.547115;-8.104310;,\n" +
                                "  5.291189;12.547115;8.104310;,\n" +
                                "  -5.291189;12.547115;8.104310;,\n" +
                                "  -5.291189;0.000000;-8.104310;,\n" +
                                "  5.291189;0.000000;-8.104310;,\n" +
                                "  5.291189;12.547115;-8.104310;,\n" +
                                "  -5.291189;12.547115;-8.104310;,\n" +
                                "  5.291189;0.000000;-8.104310;,\n" +
                                "  5.291189;0.000000;8.104310;,\n" +
                                "  5.291189;12.547115;8.104310;,\n" +
                                "  5.291189;12.547115;-8.104310;,\n" +
                                "  5.291189;0.000000;8.104310;,\n" +
                                "  -5.291189;0.000000;8.104310;,\n" +
                                "  -5.291189;12.547115;8.104310;,\n" +
                                "  5.291189;12.547115;8.104310;,\n" +
                                "  -5.291189;0.000000;8.104310;,\n" +
                                "  -5.291189;0.000000;-8.104310;,\n" +
                                "  -5.291189;12.547115;-8.104310;,\n" +
                                "  -5.291189;12.547115;8.104310;;" +
                                "}");
        try {
            table.register(new XWatcher("template", new Delegate(this, "testTemplate")));
        } catch (DelegateException e) {
            assert false;
        }
        builder = new XWatchBatcher(reader) {
            @Override
            protected List<XWatcher> getPrimitiveWatchers() throws DelegateException {
                XDocumentRegister table = XDocumentRegister.table();
                return new ArrayList<>(table.getWatchers());
            }
        };
        try {
            try {
                builder.setStreamOut(new Delegate(this, "templateReadingOut"));
            } catch (DelegateException e) {
                assert false;
            }
            builder.read();
        } catch (GameIOException e) {
            assert false;
        }
    }


    @Test
    public void readDynamicTest() {

        XDocumentRegister table = XDocumentRegister.table();
        try {
            table.register("FLOAT", new XFloatReader());
            table.register("DWORD", new XIntReader());
        } catch (GameIOException e) {
            assert false;
        }

        reader = new XStringStreamReader(
                        "template Vector {\n" +
                        " <3d82ab5e-62da-11cf-ab39-0020af71e433>\n" +
                        " FLOAT x;\n" +
                        " FLOAT y;\n" +
                        " FLOAT z;\n" +
                        "}" +
                        "template Frame {\n" +
                        " <3d82ab46-62da-11cf-ab39-0020af71e433>\n" +
                        "Vector defined;\n" +
                        " [...]\n" +
                        "}" +
                        "Frame testFrame {" +
                                "5.291189;0.000000;-8.104310;" +
                                "Vector a{-5.291189;0.000000;-8.104310;}\n" +
                                "Vector b{-5.291189;0.000000;8.104310;}\n" +
                                "Vector c{5.291189;0.000000;8.104310;}" +
                        "}"
        );
        try {
            table.register(new XWatcher("template", new Delegate(this, "testTemplate")));
        } catch (DelegateException e) {
            assert false;
        }
        builder = new XWatchBatcher(reader) {
            @Override
            protected List<XWatcher> getPrimitiveWatchers() throws DelegateException {
                XDocumentRegister table = XDocumentRegister.table();
                return new ArrayList<>(table.getWatchers());
            }
        };
        try {
            try {
                builder.setStreamOut(new Delegate(this, "templateReadingOut"));
            } catch (DelegateException e) {
                assert false;
            }
            builder.read();
        } catch (GameIOException e) {
            assert false;
        }

    }


    @Test
    public void readDynamicHierarchyTest() {

        reader = new XStringStreamReader(
                "template Vector {\n" +
                        " <3d82ab5e-62da-11cf-ab39-0020af71e433>\n" +
                        " FLOAT x;\n" +
                        " FLOAT y;\n" +
                        " FLOAT z;\n" +
                        "}" +
                        "template Frame {\n" +
                        " <3d82ab46-62da-11cf-ab39-0020af71e433>\n" +
                        "Vector defined;\n" +
                        " [...]\n" +
                        "}"  +
                        "" +
                        "Frame testFrame {" +
                        "   5.291189;0.000000;-8.104310;" +
                        "   Frame internal {" +
                        "       5;0;-8;" +
                        "       Frame final1 {" +
                        "           1;2;3;" +
                        "       }" +
                        "       Frame final2 {" +
                        "           3;4;5;" +
                        "       }" +
                        "       Vector final3 {" +
                        "           5;6;7;" +
                        "       }" +
                        "   }" +
                        "}"
        );

        try {
            Frame frame = XDocument.getFrame(reader);
        } catch (GameIOException e) {
            assert false;
        }

    }

    @Test
    public void fileTest() {
        try {
            Frame frame = XFile.loadFrame("animation.x");
        } catch (GameIOException e) {
            assert false;
        }
    }
    public void testTemplate() throws GameIOException {
        XTemplate template = new XTemplate(null, reader);
        template.subscribe(reader);
        try {
            builder.subscribe(new Delegate(this, "updateBuilder"));
        } catch (DelegateException e) {
            assert false;
        }

    }


    public void updateBuilder() {
        builder.updateWatchers(XDocumentRegister.table().getWatchers());
    }

    public void templateReadingOut() {
        builder.stop();
    }

    private XWatchBatcher getBuilder(final String type, final String delegate) {
        return  new XWatchBatcher(reader) {
            @Override
            protected List<XWatcher> getPrimitiveWatchers() throws DelegateException {
                return getWatchers(type, delegate);
            }
        };
    }

    private List<XWatcher> getWatchers(String type, String delegate) throws DelegateException {
        final List<XWatcher> watchers = new ArrayList<>();
        watchers.add(new XWatcher(type, new Delegate(this, delegate)));
        return watchers;
    }


}
