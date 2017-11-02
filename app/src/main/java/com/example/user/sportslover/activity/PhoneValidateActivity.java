package com.example.user.sportslover.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.sportslover.R;
import com.example.user.sportslover.bean.User;
import com.example.user.sportslover.model.UserModelImpl;
import com.example.user.sportslover.util.ToastUtil;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class PhoneValidateActivity extends AppCompatActivity {
    @Bind(R.id.iv_register_back)
    ImageView registerBack;
    @Bind(R.id.register_get_check_pass)
    Button registerGetCheckPass;
    @Bind(R.id.register_checknum)
    EditText register_checknum;



    @Bind(R.id.phone_tips)
    TextView phone_tips;
    EventHandler eh;
    @Bind(R.id.tv_password)
    EditText Password;
    @Bind(R.id.tv_check_password)
    EditText check_password;
    private UserModelImpl mUserModelImpl = new UserModelImpl();
    String Phone ;

    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_phone_validate);
        SMSSDK.initSDK(this, "2128cfa210e50", "28520707ebc842ff5b741911627bf55a");
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Phone = intent.getStringExtra("phoneNumber");
        phone_tips.setText("The verification code will be send to +86 " + Phone);
        //注册回调
        SMSSDK.registerEventHandler(eventHandler);
    }

    @OnClick({R.id.iv_register_back, R.id.register_get_check_pass, R.id.btn_verify_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_register_back:
                finish();
                break;
            case R.id.register_get_check_pass:
                SMSSDK.getVerificationCode("86", Phone);
                phone_tips.setText("The verification code have been sent to +86 " + Phone);
                break;

            case R.id.btn_verify_phone:

                String password = Password.getText().toString();
                String passwordAgain = check_password.getText().toString();
                //if (!name.equals("")&& !password.equals("")&&!mPhone.equals("")) {
                if (!password.equals("") && !passwordAgain.equals("")) {
                    if (!passwordAgain.equals(password)) {
                        ToastUtil.showLong(PhoneValidateActivity.this, "密码不一致");
                    } else {
                        if (!TextUtils.isEmpty(register_checknum.getText().toString())) {
                            SMSSDK.submitVerificationCode("86", Phone, register_checknum.getText().toString());
                        }
                        else{
                            ToastUtil.showShort(PhoneValidateActivity.this,"Please input verification code");
                        }
                    }
                }
                else{
                    ToastUtil.showShort(PhoneValidateActivity.this,"Please input password");
                }
                break;
        }
    }


    //防止内存泄漏
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    private EventHandler eventHandler = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            super.afterEvent(event, result, data);
            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    @SuppressWarnings("unchecked") HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");
                    Log.d("TAAAAAAA", "提交验证码成功--country=" + country + "--phone" + phone);

                    user.setNumber(Phone);
                    user.setPassword(Password.getText().toString());
                    Intent intent = new Intent(PhoneValidateActivity.this, RegisterActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("User", user);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    Log.d("TAAAAAAA", "获取验证码成功");
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                }
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
    };

}