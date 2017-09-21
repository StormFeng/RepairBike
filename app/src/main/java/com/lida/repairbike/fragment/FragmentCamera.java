package com.lida.repairbike.fragment;

import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.lida.repairbike.R;
import com.lida.repairbike.widget.PasswordInputView;
import com.midian.base.base.BaseActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/8.
 */

public class FragmentCamera extends BaseActivity {

    @BindView(R.id.videoView)
    VideoView videoView;
    @BindView(R.id.ll_iv)
    LinearLayout llIv;
    @BindView(R.id.password_view)
    PasswordInputView passwordView;
    @BindView(R.id.btn_ok)
    Button btnOk;

    private Camera camera;
    private SurfaceHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_camera);
        ButterKnife.bind(this);
        holder = videoView.getHolder();
        camera = Camera.open();
        if (camera != null) {
            try {
                camera.setPreviewDisplay(holder);
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.btn_ok)
    public void onClick() {
    }
}
