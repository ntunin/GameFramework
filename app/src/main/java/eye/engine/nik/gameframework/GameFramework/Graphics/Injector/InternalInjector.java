package eye.engine.nik.gameframework.GameFramework.Graphics.Injector;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import eye.engine.nik.gameframework.GameFramework.Graphics.Journal.BreakingNews.HashedNewsFactory;
import eye.engine.nik.gameframework.GameFramework.Graphics.Journal.BreakingNews.NewsFactory;
import eye.engine.nik.gameframework.GameFramework.Graphics.Journal.FeaturedPointsDetector.NautilusAlgorithm;
import eye.engine.nik.gameframework.GameFramework.Graphics.Journal.HashMapJournal;
import eye.engine.nik.gameframework.GameFramework.Graphics.Journal.Journal;
import eye.engine.nik.gameframework.GameFramework.Graphics.Journal.CameraCapturing.JournalingCameraCapturing;
import eye.engine.nik.gameframework.GameFramework.Graphics.Journal.CameraPositioner.JournalingCameraPositioner;
import eye.engine.nik.gameframework.GameFramework.Graphics.Journal.Journalist;

/**
 * Created by nikolay on 26.01.17.
 */

public class InternalInjector extends Injector{
    private HashMap<String, Object> instances;
    private static InternalInjector injector;

    public static InternalInjector main() {
        if(injector == null) {
            injector = new InternalInjector();
        }
        return injector;
    }

    private InternalInjector() {
        NewsFactory newsFactory = new HashedNewsFactory();
        Journalist cameraCapturing = new JournalingCameraCapturing(0);
        Journalist cameraPositioner = new JournalingCameraPositioner();
        Journalist nautilus = new NautilusAlgorithm();
        Map<String, Object> settings = new HashMap<>();
        List<Journalist> journalists = new LinkedList<>();
        journalists.add(cameraCapturing);
        journalists.add(cameraPositioner);
        journalists.add(nautilus);
        Journal journal = new HashMapJournal(journalists);

        instances = new HashMap<>();
        instances.put("News Factory", newsFactory);
        instances.put("Journal", journal);
        instances.put("Camera", cameraCapturing);
        instances.put("Settings", settings);
        instances.put("Journalists", journalists);

    }

    public Object getInstance(String token) {
        return instances.get(token);
    }

    @Override
    public void setInstance(String token, Object instance) {
        instances.put(token, instance);
    }
}
