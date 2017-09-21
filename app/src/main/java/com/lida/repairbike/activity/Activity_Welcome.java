package com.lida.repairbike.activity;

import android.database.Observable;
import android.os.Bundle;
import android.view.KeyEvent;

import com.apkfuns.logutils.LogUtils;
import com.lida.repairbike.R;
import com.midian.base.base.BaseActivity;
import com.midian.base.util.UIHelper;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class Activity_Welcome extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                UIHelper.jump(_activity,Activity_Login.class);
                onDestroy();
            }
        },2000);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
            LogUtils.e("dispatchKeyEvent");
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
