package eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextStreamReader;

/**
 * Created by nikolay on 08.10.16.
 */

public class XNode extends XNamedVariable implements XVariable {
    private List<XVariable> definitions;
    String type;

    public XNode(String name) {
        super(name);
    }
    public XNode(String name, String type) {
        super(name);
        this.type = type;
    }
    public void setDefinitions(List<XVariable> definitions) {
        this.definitions = definitions;
    }

    @Override
    public XNamedVariable read(XDocumentContext context) {
        Map<String, Object> child = new HashMap<>();
        XTextStreamReader stream = context.getStream();
        context.setCurrentNode(child);
        for(XVariable var: definitions) {
            XNamedVariable variable = var.read(context);
            String _name = variable.getName();
            Object value = variable.getValue();
            XStreamTokenizer.read(context.getStream(), ';');
            child.put(_name, value);
        }
        XNamedVariable result = new XNamedVariable(name);
        result.setValue(new XTyped(type, name, child));
        return result;
    }
}
