package com.ntunin.cybervision.injector;

import com.ntunin.cybervision.objectfactory.ObjectFactory;
import com.ntunin.cybervision.R;
import com.ntunin.cybervision.releasable.ReleasableFactory;
import com.ntunin.cybervision.res.Res;
import com.ntunin.cybervision.res.ResMap;
import com.ntunin.cybervision.io.FileIO;
import com.ntunin.cybervision.journal.HashMapJournal;
import com.ntunin.cybervision.journal.Journal;
import com.ntunin.cybervision.journal.breakingnews.HashedNewsFactory;
import com.ntunin.cybervision.journal.breakingnews.NewsFactory;
import com.ntunin.cybervision.journal.cameracapturing.FileCapturing;
import com.ntunin.cybervision.journal.cameracapturing.YCbCrFrameFactory;
import com.ntunin.cybervision.journal.featureddetector.divider.ninepointsdivider.NinePointsDividerFactory;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge.EdgeFactory;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge.EdgeNodeFactory;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge.EdgeRegisterFactory;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge.EdgeRootFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import math.intpoint.IntPointFactory;
import math.intsize.IntSizeFactory;

/**
 * Created by nikolay on 14.03.17.
 */

public class TestInjector extends MapInjector {

    public TestInjector() {
        super();
        this.instances = getInstances();
    }

    HashMap<String, Object> getInstances() {
        NewsFactory newsFactory = new HashedNewsFactory();


        ResMap<String, ReleasableFactory> factoryMap = new ResMap<>();
        factoryMap.put("Int Point", new IntPointFactory());
        factoryMap.put("Int Size", new IntSizeFactory());
        factoryMap.put("Edge", new EdgeFactory());
        factoryMap.put("Edge Node", new EdgeNodeFactory());
        factoryMap.put("Edge Root", new EdgeRootFactory());
        factoryMap.put("Edge Register", new EdgeRegisterFactory());
        factoryMap.put("Nine Points", new NinePointsDividerFactory());
        factoryMap.put("Image Frame", new YCbCrFrameFactory());
        factoryMap.put(Res.string(R.string.news), newsFactory);
        ObjectFactory factory = new ObjectFactory();
        ResMap<String, Object> args = new ResMap<>();
        Journal journal = new HashMapJournal();
        args.put(R.string.camera_action, "Camera");
        args.put(R.string.object_factory, factory);
        args.put("testFile", "9_1.f");
        args.put(R.string.journal, journal);

        FileCapturing capturing = new FileCapturing();
        capturing.init(args);
        args.put(R.string.factories, factoryMap);
        factory.init(args);

        HashMap<String, Object> instances = new HashMap<>();
        instances.put(Res.string(R.string.object_factory), factory);
        instances.put(Res.string(R.string.news), newsFactory);
        instances.put("Journal", journal);
        instances.put("Capturing", capturing);
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

        return instances;
    }

    public TestInjector(Map<String, Object> instances) {
        super(instances);
    }

}
