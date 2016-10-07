package eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder;

/**
 * Created by nikolay on 05.10.16.
 */

class XFloatFactory implements  XVariable, XVariableFactory {

    @Override
    public XNamedVariable read(XDocumentContext context) {
        return null;
    }

    @Override
    public XVariable instantiate(XDocumentContext context) {
        return new XFloat(context);
    }

    @Override
    public XVariable instantiate() {
        return new XFloat();
    }
}
