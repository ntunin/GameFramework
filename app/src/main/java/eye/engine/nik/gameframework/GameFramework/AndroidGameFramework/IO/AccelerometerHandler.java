package eye.engine.nik.gameframework.GameFramework.AndroidGameFramework.IO;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;





public class AccelerometerHandler implements SensorEventListener {

    DeviceAcceleration acceleration;

    public AccelerometerHandler(Context context) {
        SensorManager manager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
            Sensor accelerometer = manager.getSensorList(
                    Sensor.TYPE_ACCELEROMETER).get(0);
            manager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_GAME);
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
// Здесь ничего не делаем
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] a = event.values;
        acceleration = new DeviceAcceleration(a[0], a[1], a[2]);
    }
    public float getAccelX() {
        return acceleration.getX();
    }
    public float getAccelY() {
        return acceleration.getY();
    }
    public float getAccelZ() {
        return acceleration.getZ();
    }

    public DeviceAcceleration getAcceleration() {
        return acceleration;
    }
}