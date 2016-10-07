package eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder;

import java.util.Map;

import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder.XStreamVariables.XDocumentContext;


/**
 * Created by nikolay on 05.10.16.
 */

public interface XVariable {
    public void read(XDocumentContext context, Map<String, Object> node);
}
