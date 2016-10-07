package eye.engine.nik.gameframework.GameFramework.IO.XFile.XStreamBuilder;

/**
 * Created by nikolay on 08.10.16.
 */

public class XTyped {
    private String type;
    private String name;
    private Object value;

    public XTyped(String type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public String getType() {
        return type;
    }
}
