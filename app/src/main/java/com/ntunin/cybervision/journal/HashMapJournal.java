package com.ntunin.cybervision.journal;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ntunin.cybervision.journal.breakingnews.BreakingNews;


/**
 * Created by nikolay on 26.01.17.
 */

public class HashMapJournal extends Journal{

    private Map<String, List<JournalSubscriber>> subscribers;

    public HashMapJournal() {
        subscribers = new HashMap<>();
    }


    @Override
    public void release(String title, BreakingNews news) {
        List<JournalSubscriber> subscriberList = subscribers.get(title);
        if(subscriberList == null) return;
        for(JournalSubscriber subscriber: subscriberList) {
            subscriber.breakingNews(news);
        }
    }

    @Override
    public void subscribe(String title, JournalSubscriber subscriber) {
        synchronized (this) {
            List<JournalSubscriber> subscriberList = subscribers.get(title);
            if(subscriberList == null) subscriberList = new LinkedList<>();
            subscriberList.add(subscriber);
            subscribers.put(title, subscriberList);
        }
    }

}
