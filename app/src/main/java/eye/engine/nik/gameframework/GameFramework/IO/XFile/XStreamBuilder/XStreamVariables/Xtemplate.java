package eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder.XStreamVariables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder.XStreamBuilder;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder.XVariable;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextStreamReader;

/**
 * Created by nikolay on 06.10.16.
 */

public class XTemplate extends XNamedVariable implements XVariable {

    private String type;
    private String id;
    private List<XVariable> definitions;

    public XTemplate(XDocumentContext context) {
        type = readName(context);
        find(context, '{');
        id = readId(context);
        definitions = readContent(context);
        context.getFactories().put(type, this);
    }

    private String readName(XDocumentContext context) {
        XTextStreamReader stream = context.getStream();
        StringBuilder builder = new StringBuilder();
        char c;
        while(true) {
            c = stream.getChar();
            if(c == ' ') {
                return builder.toString();
            } else {
                builder.append(c);
            }
        }

    }

    private void find(XDocumentContext context, char stop) {
        XTextStreamReader stream = context.getStream();
        for(char c = '\10'; c!=stop; c = stream.getChar());
    }

    private String readId(XDocumentContext context) {
        XTextStreamReader stream = context.getStream();
        find(context, '<');
        StringBuilder builder = new StringBuilder();
        while(true) {
            char c = stream.getChar();
            if(c == '>') break;
            builder.append(c);
        }
        return builder.toString();

    }
    private List<XVariable> readContent(XDocumentContext context) {
        XTextStreamReader stream = context.getStream();
        StringBuilder definition = new StringBuilder();
        List<XVariable> definitions = new ArrayList<>();
        while(true) {
            char c = stream.getChar();
            switch (c) {
                case '}': {
                    return definitions;
                }
                case ' ': {
                    String type = definition.toString().trim();
                    definitions.add(parseDefinition(type, context));
                    definition = new StringBuilder();
                    break;
                }
                case '\n': {
                    break;
                }
                default: {
                    definition.append(c);

                }
            }
        }
    }

    private XVariable parseDefinition(String type, XDocumentContext context) {
        XVariableFactory factory = (XVariableFactory) context.getFactories().get(type);
        if(factory == null) return null;
        XVariable var = factory.instantiate(context);
        return var;
    }

    @Override
    public void read(XDocumentContext context, Map<String, Object> node) {
        Map<String, Object> child = new HashMap<>();
        String name = readName(context, ' ');
        find(context, '{');
        for(XVariable var: definitions) {
            StringBuilder builder = new StringBuilder();
            XTextStreamReader stream = context.getStream();
            while(true) {
                char c = stream.getChar();
                if(c == ';') break;
                builder.append(c);
            }
            context.setLastToken(builder.toString());
            builder = new StringBuilder();
            var.read(context, child);
        }
        node.put(name, child);
        find(context, '}');
    }


}
