package com.example.user.sportslover.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.sportslover.R;
import com.example.user.sportslover.bean.User;
import com.example.user.sportslover.bean.UserEventBus;
import com.example.user.sportslover.bean.UserLocal;
import com.example.user.sportslover.model.SportModelInter;
import com.example.user.sportslover.model.UserModelImpl;
import com.example.user.sportslover.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import de.greenrobot.event.EventBus;

public class LoginActivity extends Activity {
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
        Bmob.initialize(this,"23fe35801c6ae4f698315d637955bb39");
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
                Intent intent = new Intent(LoginActivity.this,PhoneValidateActivity.class);
                startActivity(intent);
                break;
            case R.id.login_btn:
                if (!TextUtils.isEmpty(loginUname.getText().toString()) && !TextUtils.isEmpty(loginPass.getText().toString())) {
                    mUserModelImpl.getUser(loginUname.getText().toString(), loginPass.getText().toString(), new SportModelInter.BaseListener() {
                        @Override
                        public void getSuccess(Object o) {
                            ToastUtil.showLong(LoginActivity.this, "登录成功");
                            User user = (User) o;
                            UserLocal userLocal = new UserLocal();
                            userLocal.setName(user.getName());
                            userLocal.setObjectId(user.getObjectId());
                            userLocal.setNumber(user.getNumber());
                            if (user.getPhoto() != null) {
                                userLocal.setPhoto(user.getPhoto().getUrl());
                            }
                            mUserModelImpl.putUserLocal(userLocal);
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            EventBus.getDefault().post(new UserEventBus(userLocal));
                            finish();
                        }

                        @Override
                        public void getFailure() {
                            ToastUtil.showLong(LoginActivity.this, "登录失败");
                        }
                    });
                }
                break;
        }
    }
}
