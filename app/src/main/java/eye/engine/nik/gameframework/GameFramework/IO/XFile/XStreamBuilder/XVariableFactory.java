package eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder;

/**
 * Created by nikolay on 06.10.16.
 */

interface XVariableFactory {
    XVariable instantiate(XDocumentContext context);
    XVariable instantiate();
}
