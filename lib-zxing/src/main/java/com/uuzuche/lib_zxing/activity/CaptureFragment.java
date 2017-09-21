package com.uuzuche.lib_zxing.activity;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.uuzuche.lib_zxing.PasswordInputView;
import com.uuzuche.lib_zxing.R;
import com.uuzuche.lib_zxing.camera.CameraManager;
import com.uuzuche.lib_zxing.decoding.CaptureActivityHandler;
import com.uuzuche.lib_zxing.decoding.InactivityTimer;
import com.uuzuche.lib_zxing.keyboard.VirtualKeyboardView;
import com.uuzuche.lib_zxing.view.ViewfinderView;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

/**
 * 自定义实现的扫描Fragment
 */
public class CaptureFragment extends Fragment implements SurfaceHolder.Callback {

    private CaptureActivityHandler handler;
    public ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private CodeUtils.AnalyzeCallback analyzeCallback;
    private Camera camera;

    public Button btnOk;
    public PasswordInputView etNumber;
    public RelativeLayout rlNumber;
    public TextView tvNotice;
    VirtualKeyboardView virtualKeyboardView;
    GridView gridView;
    ArrayList<Map<String, String>> valueList;
    String amount = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        CameraManager.init(getActivity().getApplication());

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this.getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.from(getActivity()).inflate(R.layout.fragment_capture,null);
        btnOk = (Button) view.findViewById(R.id.btn_ok);
        etNumber = (PasswordInputView) view.findViewById(R.id.et_numver);
        rlNumber = (RelativeLayout) view.findViewById(R.id.rl_number);
        tvNotice = (TextView) view.findViewById(R.id.tv_notice);
        virtualKeyboardView = (VirtualKeyboardView) view.findViewById(R.id.virtualKeyboardView);

        viewfinderView = (ViewfinderView) view.findViewById(R.id.viewfinder_view);
        surfaceView = (SurfaceView) view.findViewById(R.id.preview_view);
        surfaceHolder = surfaceView.getHolder();

        initKeyBoard();

        return view;
    }

    private void initKeyBoard() {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            etNumber.setInputType(InputType.TYPE_NULL);
        } else {
            getActivity().getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(etNumber, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        gridView = virtualKeyboardView.getGridView();
        gridView.setOnItemClickListener(onItemClickListener);
        valueList = virtualKeyboardView.getValueList();
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            if(amount.length()<10){
                if (position < 11 && position != 9) {    //点击0~9按钮

                    amount = etNumber.getText().toString().trim();
                    amount = amount + valueList.get(position).get("name");

                    etNumber.setText(amount);

                    Editable ea = etNumber.getText();
                    etNumber.setSelection(ea.length());
                } else {

//                    if (position == 9) {      //点击退格键
//                        amount = etNumber.getText().toString().trim();
//                        if (!amount.contains(".")) {
//                            amount = amount + valueList.get(position).get("name");
//                            etNumber.setText(amount);
//
//                            Editable ea = etNumber.getText();
//                            etNumber.setSelection(ea.length());
//                        }
//                    }
                }
            }
            if (position == 11) {      //点击退格键
                amount = etNumber.getText().toString().trim();
                if (amount.length() > 0) {
                    amount = amount.substring(0, amount.length() - 1);
                    etNumber.setText(amount);

                    Editable ea = etNumber.getText();
                    etNumber.setSelection(ea.length());
                }
            }
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getActivity().getSystemService(getActivity().AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        inactivityTimer.shutdown();
        super.onDestroy();
    }


    /**
     * Handler scan result
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();

        if (result == null || TextUtils.isEmpty(result.getText())) {
            if (analyzeCallback != null) {
                analyzeCallback.onAnalyzeFailed();
            }
        } else {
            if (analyzeCallback != null) {
                analyzeCallback.onAnalyzeSuccess(barcode, result.getText());
            }
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
            camera = CameraManager.get().getCamera();
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet, viewfinderView);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
        if (camera != null) {
            if (camera != null && CameraManager.get().isPreviewing()) {
                if (!CameraManager.get().isUseOneShotPreviewCallback()) {
                    camera.setPreviewCallback(null);
                }
                camera.stopPreview();
                CameraManager.get().getPreviewCallback().setHandler(null, 0);
                CameraManager.get().getAutoFocusCallback().setHandler(null, 0);
                CameraManager.get().setPreviewing(false);
            }
        }
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    public CodeUtils.AnalyzeCallback getAnalyzeCallback() {
        return analyzeCallback;
    }

    public void setAnalyzeCallback(CodeUtils.AnalyzeCallback analyzeCallback) {
        this.analyzeCallback = analyzeCallback;
    }

}
