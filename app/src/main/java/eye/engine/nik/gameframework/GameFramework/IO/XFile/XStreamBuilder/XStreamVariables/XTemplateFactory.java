package eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder.XStreamVariables;

import java.util.Map;

import eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder.XVariable;

/**
 * Created by nikolay on 06.10.16.
 */

public class XTemplateFactory implements XVariable {


    @Override
    public void read(XDocumentContext context, Map<String, Object> node) {
        new XTemplate(context);
    }

}
