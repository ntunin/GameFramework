package com.ntunin.cybervision.injector;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ntunin.cybervision.journal.breakingnews.HashedNewsFactory;
import com.ntunin.cybervision.journal.breakingnews.NewsFactory;
import com.ntunin.cybervision.journal.cameracapturing.CameraFrameFactory;
import com.ntunin.cybervision.journal.cameracapturing.YCbCrFrameFactory;
import com.ntunin.cybervision.journal.featureddetector.NautilusAlgorithm;
import com.ntunin.cybervision.journal.HashMapJournal;
import com.ntunin.cybervision.journal.Journal;
import com.ntunin.cybervision.journal.cameracapturing.JournalingCameraCapturing;
import com.ntunin.cybervision.journal.camerapositioneer.JournalingCameraTransformLocator;
import com.ntunin.cybervision.journal.Journalist;

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
        CameraFrameFactory cameraFrameFactory = new YCbCrFrameFactory();
        NewsFactory newsFactory = new HashedNewsFactory();
        Journalist cameraCapturing = new JournalingCameraCapturing(0);
        Journalist cameraPositioner = new JournalingCameraTransformLocator();
        Journalist nautilus = new NautilusAlgorithm();
        Map<String, Object> settings = new HashMap<>();
        List<Journalist> journalists = new LinkedList<>();
        journalists.add(cameraCapturing);
        journalists.add(cameraPositioner);
        journalists.add(nautilus);

        Journal journal = new HashMapJournal(journalists);

        instances = new HashMap<>();
        instances.put("Frame Factory", cameraFrameFactory);
        instances.put("News Factory", newsFactory);
        instances.put("Journal", journal);
        instances.put("Camera", cameraCapturing);
        instances.put("Settings", settings);
        instances.put("Journalists", journalists);
        instances.put("Nautilus Algorithm", nautilus);

    }

    public Object getInstance(String token) {
        return instances.get(token);
    }

    @Override
    public void setInstance(String token, Object instance) {
        instances.put(token, instance);
    }
}
