package com.example.user.sportslover.user;

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

import com.example.user.sportslover.MainActivity;
import com.example.user.sportslover.R;
import com.example.user.sportslover.dto.User;
import com.example.user.sportslover.model.Impl.SportModelImpl;
import com.example.user.sportslover.model.UserModel;
import com.example.user.sportslover.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    private UserModel mUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mUserModel = new UserModel();
    }

    @OnClick({R.id.login_back, R.id.login_register, R.id.login_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_back:
                finish();
                break;
            case R.id.login_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
                break;
            case R.id.login_btn:
                if (!TextUtils.isEmpty(loginUname.getText().toString()) && !TextUtils.isEmpty(loginPass.getText().toString())) {
                    mUserModel.getUser(loginUname.getText().toString(), loginPass.getText().toString(), new SportModelImpl.BaseListener() {
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
                            mUserModel.putUserLocal(userLocal);
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
