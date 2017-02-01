package eye.engine.nik.gameframework.GameFramework.Graphics.Journal.CameraPositioner;

import android.content.Context;

import eye.engine.nik.gameframework.GameFramework.AndroidGameFramework.IO.AccelerometerHandler;
import eye.engine.nik.gameframework.GameFramework.AndroidGameFramework.IO.DeviceAcceleration;
import eye.engine.nik.gameframework.GameFramework.Graphics.Injector.Injector;
import eye.engine.nik.gameframework.GameFramework.Graphics.Journal.BreakingNews.BreakingNews;
import eye.engine.nik.gameframework.GameFramework.Graphics.Journal.Journal;
import eye.engine.nik.gameframework.GameFramework.Graphics.Journal.JournalSubscriber;
import eye.engine.nik.gameframework.GameFramework.Graphics.Journal.Journalist;

/**
 * Created by nikolay on 01.02.17.
 */

public class JournalingCameraPositioner extends JournalSubscriber implements Journalist {

    private Journal journal;
    private Injector injector;
    private AccelerometerHandler accelerometer;

    public JournalingCameraPositioner() {
    }

    @Override
    public void start() {
        injector = Injector.main();
        accelerometer = (AccelerometerHandler) injector.getInstance("Accelerometer");
        journal = (Journal) injector.getInstance("Journal");
        journal.subscribe("Camera", this);
    }

    @Override
    public void stop() {

    }

    @Override
    public void breakingNews(BreakingNews news) {
        DeviceAcceleration acceleration = accelerometer.getAcceleration();
        news.write("Acceleration", acceleration);
        journal.release("Acceleration", news);
    }
}
