package eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader;

/**
 * Created by nikolay on 15.08.16.
 */
class ShortArray implements Array {
    private short[] array;
    private int index;
    public ShortArray(int size) {
        array = new short[size];
    }
    @Override
    public void addValue(Object value) {
        array[index++] = (short)value;
    }
}
