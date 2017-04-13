package com.ntunin.cybervision.io;

import com.ntunin.cybervision.ResMap;
import com.ntunin.cybervision.injector.Injectable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Created by nikolay on 01.04.17.
 */

public class ClassLoaderIO implements FileIO, Injectable{
    @Override
    public InputStream readAsset(String fileName) throws IOException {
        return this.getClass().getClassLoader().getResourceAsStream(fileName);
    }

    @Override
    public InputStream readFile(String fileName) throws IOException {
        return this.getClass().getClassLoader().getResourceAsStream(fileName);
    }

    @Override
    public OutputStream writeFile(String fileName) throws IOException {
        return null;
    }

    @Override
    public void init(ResMap<String, Object> data) {
        return;
    }
}
