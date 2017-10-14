package com.example.user.sportslover.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.sportslover.R;
import com.example.user.sportslover.base.BaseActivity;
import com.example.user.sportslover.bean.User;
import com.example.user.sportslover.model.UserModel;
import com.example.user.sportslover.model.UserModelImpl;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class LoginActivity extends BaseActivity {
    @Bind(R.id.login_back)
    ImageView loginBack;
    @Bind(R.id.login_register)
    TextView loginRegister;
    @Bind(R.id.login_btn)
    Button loginBtn;
    @Bind(R.id.login_uname)
    EditText loginUname;
    @Bind(R.id.login_pass)
    EditText loginPass;

    private UserModelImpl mUserModelImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, "23fe35801c6ae4f698315d637955bb39");
        ButterKnife.bind(this);
        mUserModelImpl = new UserModelImpl();
    }

    @OnClick({R.id.login_back, R.id.login_register, R.id.login_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_back:
                finish();
                break;
            case R.id.login_register:
                Intent intent = new Intent(LoginActivity.this, PhoneValidateActivity.class);
                startActivity(intent);
                break;
            case R.id.login_btn:
                UserModel.getInstance().login(loginUname.getText().toString(), loginPass.getText
                        ().toString(), new LogInListener() {

                    @Override
                    public void done(Object o, BmobException e) {
                        if (e == null) {
                            User user = (User) o;
                            BmobIM.getInstance().updateUserInfo(new BmobIMUserInfo(user
                                    .getObjectId(), user.getUsername(), user.getAvatar()));
                            startActivity(MainActivity.class, null, true);
                        } else {
                            toast(e.getMessage() + "(" + e.getErrorCode() + ")");
                        }
                    }
                });
                break;
        }
    }
}
