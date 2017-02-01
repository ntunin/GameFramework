package eye.engine.nik.gameframework.GameFramework.Graphics.Journal.CameraCapturing;

import org.opencv.core.Size;

import java.util.Map;

import eye.engine.nik.gameframework.GameFramework.Graphics.Injector.Injector;
import eye.engine.nik.gameframework.GameFramework.Graphics.Injector.InternalInjector;
import eye.engine.nik.gameframework.GameFramework.Graphics.Journal.BreakingNews.BreakingNews;
import eye.engine.nik.gameframework.GameFramework.Graphics.Journal.BreakingNews.NewsFactory;
import eye.engine.nik.gameframework.GameFramework.Graphics.Journal.CameraCapturing.CameraCapturing;
import eye.engine.nik.gameframework.GameFramework.Graphics.Journal.Journal;
import eye.engine.nik.gameframework.GameFramework.Graphics.Journal.Journalist;

/**
 * Created by nikolay on 30.01.17.
 */

public class JournalingCameraCapturing extends CameraCapturing implements Journalist {

    private Injector injector;
    private Journal journal;
    private NewsFactory newsFactory;

    public JournalingCameraCapturing(int cameraId) {
        super(cameraId);
    }

    @Override
    protected void handleFrame(CameraFrame frame) {
        BreakingNews news = newsFactory.create();
        news.write("Camera Frame", frame);
        journal.release("Camera", news);
    }

    @Override
    public void start() {
        injector = InternalInjector.main();
        newsFactory = (NewsFactory) injector.getInstance("News Factory");
        journal = (Journal) injector.getInstance("Journal");
        Map<String, Object> settings = (Map<String, Object>) injector.getInstance("Settings");
        Size size = (Size) settings.get("Camera Size");
        connectCamera((int)size.width, (int)size.height);
    }

    @Override
    public void stop() {
        disconnectCamera();
    }
}
