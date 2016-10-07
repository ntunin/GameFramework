package eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder.XStreamVariables;

import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextStreamReader;

/**
 * Created by nikolay on 06.10.16.
 */

public class XNamedVariable {
    protected String readName(XDocumentContext context, char stop) {
        XTextStreamReader stream = context.getStream();
        StringBuilder builder = new StringBuilder();
        while(true) {
            char c = stream.getChar();
            if(c== stop) break;
            builder.append(c);
        }
        return builder.toString();
    }
}
