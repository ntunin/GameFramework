package com.ntunin.cybervision.timeout;

/**
 * Created by mikhaildomrachev on 18.04.17.
 */

public class Timeout {
    public static void set(final Task task, int delay) {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        task.execute();
                    }
                },
                delay
        );
    }
}
