package eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader;

/**
 * Created by nikolay on 15.08.16.
 */
public class ObjectArray implements Array {
    private int index;
    private int size;
    Object[] array;
    public ObjectArray(int size) {
        this.size = size;
        this.array = new Object[size];
    }
    @Override
    public void addValue(Object value) {
        array[index++] = value;
    }

    public Object[] getArray() {
        return array;
    }
}
