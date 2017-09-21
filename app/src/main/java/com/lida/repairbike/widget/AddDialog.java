package com.lida.repairbike.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import com.lida.repairbike.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/28 0028.
 */

public class AddDialog extends Dialog {

    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.et_Content)
    EditText etContent;

    private Context context;

    public AddDialog(Context context) {
        super(context, R.style.diy_dialog);
        init(context);
    }

    public AddDialog(Context context, int themeResId) {
        super(context, R.style.diy_dialog);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        Window w = this.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        w.setAttributes(lp);
        this.setCanceledOnTouchOutside(true);
        View v = View.inflate(context, R.layout.dialog_notice, null);
        this.setContentView(v);
        ButterKnife.bind(this, v);
    }

    public String getContent(){
        return etContent.getText().toString();
    }

    public EditText getEtContent(){
        return etContent;
    }

    public View getButton(){
        return btnCommit;
    }
}
