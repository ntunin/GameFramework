package eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder.XStreamVariables;

import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder.XVariable;

/**
 * Created by nikolay on 06.10.16.
 */

public interface XVariableFactory {
    XVariable instantiate(XDocumentContext context);
}
