package com.midian.base.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.bishilai.thirdpackage.R;

public class LoadingDialog extends Dialog {
	private static int default_width = 160; // 默认宽度
	private static int default_height = 120;// 默认高度
	private TextView message;
	private View progressView;

	public LoadingDialog(Context context, int layout, int style) {
		this(context, default_width, default_height, layout, style);
	}

	public LoadingDialog(Context context, int width, int height, int layout,
			int style) {
		super(context, style);
		setContentView(layout);
		Window window = getWindow();
		LayoutParams params = window.getAttributes();
		params.gravity = Gravity.CENTER;
		params.width = LayoutParams.WRAP_CONTENT;
		params.height = LayoutParams.WRAP_CONTENT;
		params.dimAmount = 0.0f;
		window.setAttributes(params);
		message = (TextView) findViewById(R.id.message);
		progressView = findViewById(R.id.progress);
		this.setCancelable(true);
		this.setCanceledOnTouchOutside(true);
	}

	public void setMessage(String msg) {
		message.setText(msg);
		if (TextUtils.isEmpty(msg)) {
			message.setVisibility(View.GONE);
		} else {
			message.setVisibility(View.VISIBLE);
		}
	}

	public void showMessage(String msg) {
		setMessage(msg);
		progressView.setVisibility(View.VISIBLE);
		show();
	}

	public void showMessageAndHideProgress(String msg) {
		setMessage(msg);
		progressView.setVisibility(View.GONE);
		show();
	}
}
