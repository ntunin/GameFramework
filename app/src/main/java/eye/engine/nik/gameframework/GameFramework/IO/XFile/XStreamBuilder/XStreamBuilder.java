package eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder;

import java.util.HashMap;
import java.util.Map;

import eye.engine.nik.gameframework.GameFramework.Graphics.Frame;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextStreamReader;

/**
 * Created by nikolay on 05.10.16.
 */

public class XStreamBuilder {
    private StringBuilder token = new StringBuilder();
    private Map<String, XVariable> factories;
    private XTextStreamReader stream;
    private Map<String, Object> node = new HashMap<>();
    private XDocumentContext context;

    public static Frame read(XTextStreamReader stream) {
        XStreamBuilder builder = new XStreamBuilder(stream);
        return builder.read();
    }

    private XStreamBuilder(XTextStreamReader stream) {
        this.context = new XDocumentContext();
        this.context.setStream(stream);
        this.stream = stream;
        this.factories = context.getFactories();
    }

    private Frame read() {
        XTextStreamReader stream = context.getStream();
        while(stream.hasNext()) {
            char c = stream.getChar();
            handle(c);
        }
        return null;
    }

    private void handle(char c) {
        switch (c) {
            case '\n':
            case '\t':
            case '\r': {
                if(token.length() > 0) token = new StringBuilder();
                return;
            }
            case ' ': {
                if(token.length() == 0) break;
                String _token = token.toString();
                token = new StringBuilder();
                handleToken(_token);
                break;
            }
            default: token.append(c);

        }
    }

    private void handleToken(String token) {
        XVariable var = factories.get(token);
        if(var != null) {
            XNamedVariable variable = var.read(context);
            if(variable != null) {
                String name = variable.getName();
                Object value = variable.getValue();
                node.put(name, value);
            }
        }
    }
}