package eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader;

/**
 * Created by nikolay on 15.08.16.
 */
public class IntArray implements Array {
    private int[] array;
    private int index;
    public IntArray(int size) {
        array = new int[size];
    }
    @Override
    public void addValue(Object value) {
        array[index++] = (int)value;
    }

    public int[] getArray() {
        return array;
    }
}
