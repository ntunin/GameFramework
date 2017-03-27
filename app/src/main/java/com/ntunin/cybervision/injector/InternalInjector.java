package com.ntunin.cybervision.injector;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ntunin.cybervision.ObjectFactory;
import com.ntunin.cybervision.ReleasableFactory;
import com.ntunin.cybervision.io.FileIO;
import com.ntunin.cybervision.journal.breakingnews.HashedNewsFactory;
import com.ntunin.cybervision.journal.breakingnews.NewsFactory;
import com.ntunin.cybervision.journal.cameracapturing.CameraCapturing;
import com.ntunin.cybervision.journal.cameracapturing.ImageFrameFactory;
import com.ntunin.cybervision.journal.cameracapturing.YCbCrFrameFactory;
import com.ntunin.cybervision.journal.HashMapJournal;
import com.ntunin.cybervision.journal.Journal;
import com.ntunin.cybervision.journal.cameracapturing.JournalingCameraCapturing;
import com.ntunin.cybervision.journal.camerapositioneer.JournalingCameraTransformLocator;
import com.ntunin.cybervision.journal.featureddetector.Detector;
import com.ntunin.cybervision.journal.featureddetector.divider.ninepointsdivider.NinePointsDividerFactory;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.nautilus.NautilusFactory;

import math.intpoint.IntPointFactory;
import math.intsize.IntSizeFactory;

/**
 * Created by nikolay on 26.01.17.
 */

public class InternalInjector extends Injector{
    private HashMap<String, Object> instances;

    public static Injector main() {
        if(injector == null) {
            injector = new InternalInjector();
        }
        return injector;
    }

    private InternalInjector() {
        ImageFrameFactory imageFrameFactory = new YCbCrFrameFactory();
        NewsFactory newsFactory = new HashedNewsFactory();
        CameraCapturing cameraCapturing = new JournalingCameraCapturing(0);
        JournalingCameraTransformLocator cameraPositioner = new JournalingCameraTransformLocator();
        Detector detector = new Detector();
        Map<String, Object> settings = new HashMap<>();

        Journal journal = new HashMapJournal();


        Map<String, ReleasableFactory> factoryMap = new HashMap<>();
        factoryMap.put("Int Point", new IntPointFactory());
        factoryMap.put("Int Size", new IntSizeFactory());
        factoryMap.put("Camera Frame", new YCbCrFrameFactory());
        factoryMap.put("Divider", new NinePointsDividerFactory());
        factoryMap.put("Point Fetcher", new NautilusFactory());
        ObjectFactory factory = new ObjectFactory(factoryMap);

        instances = new HashMap<>();
        instances.put("Object Factory", factory);
        instances.put("Frame Factory", imageFrameFactory);
        instances.put("News Factory", newsFactory);
        instances.put("Journal", journal);
        instances.put("Camera", cameraCapturing);
        instances.put("Settings", settings);
        instances.put("Detector", detector);
        instances.put("IO", new FileIO() {
            @Override
            public InputStream readAsset(String fileName) throws IOException {
                return null;
            }

            @Override
            public InputStream readFile(String fileName) throws IOException {
                return this.getClass().getClassLoader().getResourceAsStream(fileName);
            }

            @Override
            public OutputStream writeFile(String fileName) throws IOException {
                return null;
            }
        });

    }

    public Object getInstance(String token) {
        return instances.get(token);
    }

    @Override
    public void setInstance(String token, Object instance) {
        instances.put(token, instance);
    }
}
