package eye.engine.nik.gameframework.GameFramework.IO.XFile;

import java.util.HashMap;
import java.util.Map;

import eye.engine.nik.gameframework.GameFramework.Graphics.Frame;
import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XBinStreamReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextFileReader;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamReader.XTextStreamReader;

/**
 * Created by nik on 19.06.16.
 */
public class XFile {
    private static Map<String, XFactory> factories;
    public static Frame loadFrame(String path){
        factories = loadFactories();
        XTextStreamReader reader = new XTextFileReader(path);
        return loadWithReader(reader);
    }
    private static Map<String, XFactory> loadFactories() {
        Map<String, XFactory> factories = new HashMap<String, XFactory>();
        factories.put("txt", new XText());
        return factories;
    }
    private static Frame loadWithReader(XTextStreamReader reader) {
        String magicWord = reader.getString(4);
        int formatVersionNumber = Integer.parseInt( reader.getString(2) );
        int formatSubVersionNumber = Integer.parseInt( reader.getString(2) );
        String formatType =reader.getString(4).trim();
        int floatSize = Integer.parseInt( reader.getString(4) );
        XFactory factory = factories.get(formatType);
        return factory.loadFrame(reader);
    }

}
