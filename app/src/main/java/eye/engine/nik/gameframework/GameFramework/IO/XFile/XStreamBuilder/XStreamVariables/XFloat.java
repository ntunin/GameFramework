package eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder.XStreamVariables;

import java.util.Map;

import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder.XVariable;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextStreamReader;

/**
 * Created by nikolay on 06.10.16.
 */

public class XFloat extends XNamedVariable implements XVariable {
    String name;

    public XFloat(XDocumentContext context) {
        name = readName(context, ';');
    }

    @Override
    public void read(XDocumentContext context, Map<String, Object> node) {
        String string = context.getLastToken();
        Float value = Float.parseFloat(string);
        node.put(name, value);
    }

}
