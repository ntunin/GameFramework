package eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder.XStreamVariables;

import java.util.HashMap;
import java.util.Map;

import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder.XStreamVariables.XFloatFactory;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder.XStreamVariables.XTemplateFactory;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder.XStreamVariables.XVariableFactory;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder.XVariable;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextStreamReader;

/**
 * Created by nikolay on 06.10.16.
 */

public class XDocumentContext {
    Map<String, XVariable> factories;
    XTextStreamReader stream;
    String lastToken;

    public XDocumentContext() {
        factories = new HashMap<>();
        factories.put("FLOAT", new XFloatFactory());
        factories.put("template", new XTemplateFactory());
    }
    public Map<String, XVariable> getFactories() {
        return factories;
    }

    public void setStream(XTextStreamReader stream) {
        this.stream = stream;
    }
    public XTextStreamReader getStream() {
        return stream;
    }
    public String getLastToken() {
        return lastToken;
    }
    public void setLastToken(String token) {
        this.lastToken = token;
    }
}
