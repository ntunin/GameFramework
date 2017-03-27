package com.ntunin.cybervision.journal.cameracapturing;

import android.graphics.Color;

import com.ntunin.cybervision.ERRNO;
import com.ntunin.cybervision.ErrCodes;
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

    public FileCapturing init(String src) {
        this.src = src;
        return this;
    }

    public void start() {
        injector = InternalInjector.main();
        newsFactory = (NewsFactory) injector.getInstance("News Factory");
        journal = (Journal) injector.getInstance("Journal");
        this.frame = getFrame();
        NewsFactory newsFactory = (NewsFactory) injector.getInstance("News Factory");
        BreakingNews news = newsFactory.create();
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
        for(int y = 0; true; y++) {
            try {
                if(in.available() == 0) break;
                List<Integer> row = new LinkedList<>();
                for(int x = 0; true; x++) {
                    if(in.available() == 0) break;
                    char c = (char) in.read();
                    if(c == '\n') break;
                    String v = new StringBuilder().append(c).append(c).append(c).append(c).append(c).append(c).toString();
                    int color = Integer.parseInt(v, 16);
                    row.add(color);
                }
                data.add(row);

            } catch (IOException e) {
                assert false;
            }
        }

        int height = data.size();
        int width = data.get(0).size();
        ImageFrame frame = new YCbCrFrame(width, height);
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
