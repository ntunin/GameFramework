package eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader;

import eye.engine.nik.gameframework.GameFramework.DelegateException;
import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextStreamReader;

/**
 * Created by nikolay on 11.08.16.
 */
public class XIntReader extends XValueReader{

    @Override
    public String getType() {
        return "DWORD";
    }

    public XIntReader() throws GameIOException {
        super();
    }

    @Override
    public void parseContent() throws DelegateException {
        super.parseContent();
        String textValue = builder.getContent();
        value = Integer.parseInt(textValue);
    }
}
