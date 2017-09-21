package com.lida.repairbike.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.jaeger.library.StatusBarUtil;
import com.lida.repairbike.R;
import com.lida.repairbike.bean.LoginBean;
import com.lida.repairbike.util.AppUtil;
import com.midian.base.base.BaseActivity;
import com.midian.base.bean.NetResult;
import com.midian.base.util.AnimatorUtils;
import com.midian.base.util.UIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登陆页
 */
public class Activity_Login extends BaseActivity {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ac.isUserIdExsit()){
            UIHelper.jump(_activity,Activity_Main.class);
            onDestroy();
        }else{
            setContentView(R.layout.activity_login);
            ButterKnife.bind(this);
        }
    }

    @OnClick(R.id.btn_login)
    public void onClick() {
        String name=etName.getText().toString();
        String pass=etPass.getText().toString();
        if("".equals(name)){
            AnimatorUtils.onVibrationView(etName);
            UIHelper.t(_activity,"请输入用户名");
            return;
        }
        if("".equals(pass)){
            AnimatorUtils.onVibrationView(etPass);
            UIHelper.t(_activity,"请输入密码");
            return;
        }
        AppUtil.getBikeApiClient(ac).login(name,pass,this);
//        UIHelper.jump(_activity,Activity_Home.class);
    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        if(res.isOK()){
            LoginBean bean = (LoginBean) res;
            ac.saveUserInfo(bean.getData().getId(),bean.getData().getName(),bean.getData().getHead_img(),bean.getData().getPhone(),
                    bean.getData().getAddress(),bean.getData().getLogintime(),bean.getData().getAccount(),bean.getData().getPasswd(),
                    bean.getData().getBirth(),bean.getData().getRepair_life());
            UIHelper.t(_activity,"登录成功！");
            UIHelper.jump(_activity,Activity_Main.class);
            onDestroy();
        }else{
            AnimatorUtils.onVibrationView(btnLogin);
            UIHelper.t(_activity,"用户名或密码错误！");
        }
    }
}
