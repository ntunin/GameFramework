package com.ntunin.cybervision.journal.cameracapturing;

import android.graphics.Color;

import com.ntunin.cybervision.ERRNO;
import com.ntunin.cybervision.ErrCodes;
import com.ntunin.cybervision.ObjectFactory;
import com.ntunin.cybervision.R;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.injector.InternalInjector;
import com.ntunin.cybervision.io.FileIO;
import com.ntunin.cybervision.journal.Journal;
import com.ntunin.cybervision.journal.breakingnews.BreakingNews;
import com.ntunin.cybervision.journal.breakingnews.NewsFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by nikolay on 12.03.17.
 */

public class FileCapturing {

    private ImageFrame frame;
    private Injector injector;
    private Journal journal;
    private NewsFactory newsFactory;
    private String src;
    private ObjectFactory factory;

    public FileCapturing init(String src) {
        this.src = src;
        return this;
    }

    public void start() {
        injector = InternalInjector.main();
        newsFactory = (NewsFactory) injector.getInstance("News Factory");
        journal = (Journal) injector.getInstance("Journal");
        factory = (ObjectFactory) injector.getInstance(R.string.object_factory);
        this.frame = getFrame();
        BreakingNews news = (BreakingNews)factory.get(R.string.news).init();
        news.write("Image Frame", this.frame);
        journal.release("Image Frame", news);
    }

    private ImageFrame getFrame() {
        try {
            FileIO io = (FileIO) injector.getInstance("IO");
            InputStream in = io.readFile(src);
            ImageFrame frame = createFrame(in);
            return frame;
        } catch (IOException e) {
            ERRNO.write(ErrCodes.COULD_NOT_READ_FILE);
            return null;
        }
    }

    private ImageFrame createFrame(InputStream in) {
        List<List<Integer>> data = new LinkedList<List<Integer>>();

        try {
            List<Integer> row = new LinkedList<>();
            StringBuilder builder = new StringBuilder();

            while(in.available() >= 0) {
                char c = (char) in.read();
                if(c == '\n') {
                    String v = builder.toString();
                    builder = new StringBuilder();
                    int color = Integer.parseInt(v, 16);
                    row.add(color);
                    data.add(row);
                    if(data.size() >= 504) {
                        int a = 0;
                        a++;
                    }
                    row = new LinkedList<>();
                    continue;
                }
                if(c == ' ') {
                    String v = builder.toString();
                    builder = new StringBuilder();
                    int color = Integer.parseInt(v, 16);
                    row.add(color);
                    if(row.size() >= 313) {
                        int a = 0;
                        a++;
                    }
                    continue;
                }
                if(c == '\uFFFF') {
                    break;
                }
                builder.append(c);
            }
            String v = builder.toString();
            int color = Integer.parseInt(v, 16);
            row.add(color);
            data.add(row);

        } catch (Exception e) {
            assert false;
        }

        int height = data.size();
        int width = data.get(0).size();
        ImageFrame frame = (ImageFrame) factory.get("Image Frame").init(width, height);
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int pixel = data.get(y).get(x);
                int r = Color.red(pixel);
                int g = Color.green(pixel);
                int b = Color.blue(pixel);
                frame.put(x, y, r, g, b);
            }
        }
        return frame;
    }

    public void stop() {

    }
}
