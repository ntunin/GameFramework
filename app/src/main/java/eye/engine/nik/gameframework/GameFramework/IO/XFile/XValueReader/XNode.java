package eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Николай on 20.07.2016.
 */
public class XNode implements XTypedItem {
    Map<String, Object> map = new HashMap<>();
    private String name;
    public XNode(String name) {
        this.name = name;
    }
    public void set(String key, Object value) {
        map.put(key, value);
    }
    public Set<String> getVariableNames() {
        return map.keySet();
    }
    public Object get(String key) {
        return map.get(key);
    }

    @Override
    public String getType() {
        return name;
    }

    @Override
    public String getName() {
        return name;
    }
}
