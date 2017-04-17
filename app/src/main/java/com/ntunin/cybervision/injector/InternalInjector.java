package com.ntunin.cybervision.injector;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.objectfactory.ObjectFactory;
import com.ntunin.cybervision.releasable.ReleasableFactory;
import com.ntunin.cybervision.res.Res;
import com.ntunin.cybervision.res.ResMap;
import com.ntunin.cybervision.io.FileIO;
import com.ntunin.cybervision.journal.breakingnews.HashedNewsFactory;
import com.ntunin.cybervision.journal.breakingnews.NewsFactory;
import com.ntunin.cybervision.journal.cameracapturing.CameraCapturing;
import com.ntunin.cybervision.journal.cameracapturing.ImageFrameFactory;
import com.ntunin.cybervision.journal.cameracapturing.YCbCrFrameFactory;
import com.ntunin.cybervision.journal.HashMapJournal;
import com.ntunin.cybervision.journal.Journal;
import com.ntunin.cybervision.journal.camerapositioneer.JournalingCameraTransformLocator;
import com.ntunin.cybervision.journal.featureddetector.Detector;
import com.ntunin.cybervision.journal.featureddetector.divider.ninepointsdivider.NinePointsDividerFactory;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge.EdgeFactory;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge.EdgeNodeFactory;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge.EdgeRegisterFactory;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge.EdgeRootFactory;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.nautilus.NautilusFactory;

import math.intpoint.IntPointFactory;
import math.intsize.IntSizeFactory;

/**
 * Created by nikolay on 26.01.17.
 */

public class InternalInjector extends Injector{
    private HashMap<String, Object> instances;


    protected InternalInjector() {
        ImageFrameFactory imageFrameFactory = new YCbCrFrameFactory();
        NewsFactory newsFactory = new HashedNewsFactory();
        CameraCapturing cameraCapturing = new CameraCapturing();
        ResMap<String, Object> args = new ResMap<>();
        args.put("cameraId", -1);
        cameraCapturing.init(args);
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
        factoryMap.put("Edge", new EdgeFactory());
        factoryMap.put("Edge Node", new EdgeNodeFactory());
        factoryMap.put("Edge Root", new EdgeRootFactory());
        factoryMap.put("Edge Register", new EdgeRegisterFactory());

        ObjectFactory factory = new ObjectFactory();
        args.put("factories", factoryMap);
        factory.init(args);

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
    public Object getInstance(int id) {
        return getInstance(Res.string(id));
    }

    @Override
    public void setInstance(String token, Object instance) {
        instances.put(token, instance);
    }

    @Override
    public void setInstance(int id, Object instance) {
        setInstance(Res.string(id), instance);
    }
}
