package eye.engine.nik.gameframework.GameFramework.AndroidGameFramework.IO;

/**
 * Created by nikolay on 02.02.17.
 */

public class DeviceAcceleration {
    private float x;
    private float y;
    private float z;

    public  DeviceAcceleration(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}
