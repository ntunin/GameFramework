package eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder.XStreamVariables;

import java.util.Map;

import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder.XVariable;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextStreamReader;

/**
 * Created by nikolay on 05.10.16.
 */

public class XFloatFactory implements  XVariable, XVariableFactory {

    @Override
    public void read(XDocumentContext context, Map<String, Object> node) {

    }

    @Override
    public XVariable instantiate(XDocumentContext context) {
        return new XFloat(context);
    }
}
