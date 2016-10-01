package eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader;

/**
 * Created by nikolay on 15.08.16.
 */
class FloatArray implements Array {
    private int index;
    private float[] array;

    public FloatArray(int size) {
        array = new float[size];
    }
    @Override
    public void addValue(Object value) {
        array[index++] = (float)value;
    }
}
