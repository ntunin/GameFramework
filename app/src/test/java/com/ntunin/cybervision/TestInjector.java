package com.ntunin.cybervision;

import com.ntunin.cybervision.featureddetector.divider.ninepointsdivider.NinePointsDividerTest;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.injector.MapInjector;
import com.ntunin.cybervision.io.FileIO;
import com.ntunin.cybervision.journal.HashMapJournal;
import com.ntunin.cybervision.journal.Journal;
import com.ntunin.cybervision.journal.breakingnews.HashedNewsFactory;
import com.ntunin.cybervision.journal.breakingnews.NewsFactory;
import com.ntunin.cybervision.journal.cameracapturing.FileCapturing;
import com.ntunin.cybervision.journal.featureddetector.divider.ninepointsdivider.NinePointsDividerFactory;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge.EdgeFactory;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge.EdgeNodeFactory;

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
        FileCapturing capturing = new FileCapturing().init("9_1.f");
        Journal journal = new HashMapJournal();


        Map<String, ReleasableFactory> factoryMap = new HashMap<>();
        factoryMap.put("Int Point", new IntPointFactory());
        factoryMap.put("Int Size", new IntSizeFactory());
        factoryMap.put("Edge", new EdgeFactory());
        factoryMap.put("Edge Node", new EdgeNodeFactory());
        factoryMap.put("Nine Points", new NinePointsDividerFactory());
        ObjectFactory factory = new ObjectFactory(factoryMap);

        HashMap<String, Object> instances = new HashMap<>();
        instances.put("Object Factory", factory);
        instances.put("News Factory", newsFactory);
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
