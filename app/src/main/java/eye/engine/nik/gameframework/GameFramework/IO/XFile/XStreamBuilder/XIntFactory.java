package eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder;

/**
 * Created by nikolay on 07.10.16.
 */

class XIntFactory implements XVariable, XVariableFactory {

    @Override
    public XNamedVariable read(XDocumentContext context) {
        return null;
    }

    @Override
    public XVariable instantiate(XDocumentContext context) {
        return new XInt(context);
    }

    @Override
    public XVariable instantiate() {
        return new XInt();
    }
}