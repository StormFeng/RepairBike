package com.lida.repairbike.widget;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by Administrator on 2017/3/27.
 */

public class MainHandler extends Handler {
    private static volatile MainHandler instance;

    public static MainHandler getInstance() {
        if (null == instance) {
            synchronized (MainHandler.class) {
                if (null == instance) {
                    instance = new MainHandler();
                }
            }
        }
        return instance;
    }
    private MainHandler() {
        super(Looper.getMainLooper());
    }
}
