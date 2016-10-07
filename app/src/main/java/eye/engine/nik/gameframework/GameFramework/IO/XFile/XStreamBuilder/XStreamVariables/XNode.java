package eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder.XStreamVariables;

import java.util.List;
import java.util.Map;

import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder.XVariable;

/**
 * Created by nikolay on 06.10.16.
 */

public class XNode implements XVariable {
    List<XVariable> definitions;
    public XNode(List<XVariable> definitions) {
        this.definitions = definitions;
    }
    @Override
    public void read(XDocumentContext context, Map<String, Object> node) {

    }

}
