package com.example.user.sportslover.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.user.sportslover.R;
import com.example.user.sportslover.model.UserModelImpl;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class PhoneValidateActivity extends AppCompatActivity {
    @Bind(R.id.register_back)
    ImageView registerBack;
    @Bind(R.id.register_get_check_pass)
    Button registerGetCheckPass;
    @Bind(R.id.register_checknum)
    EditText register_checknum;
    @Bind(R.id.register_layout)
    LinearLayout registerLayout;
    @Bind(R.id.register_btn)
    Button registerBtn;
    EventHandler eh;
    @Bind(R.id.register_phone)
    EditText registerPhone;

    private UserModelImpl mUserModelImpl = new UserModelImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_phone_validate);
        SMSSDK.initSDK(this, "2128cfa210e50", "28520707ebc842ff5b741911627bf55a");
        ButterKnife.bind(this);

//        eh = new EventHandler() {
//            @Override
//            public void afterEvent(int event, int result, Object data) {
//
//                if (result == SMSSDK.RESULT_COMPLETE) {
//                    //回调完成
//                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
//                        Intent intent = new Intent(PhonevalidActivity.this, RegisterActivity.class);
//                        intent.putExtra("phone", registerPhone.getText().toString());
//                        startActivity(intent);
//                        finish();
//                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
//                    } else if (event == SMSSDK.RESULT_ERROR) {
//                    }
//                } else {
//                    ((Throwable) data).printStackTrace();
//                }
//            }
//        };
        //      SMSSDK.registerEventHandler(eh); //注册短信回调
        //注册回调
        SMSSDK.registerEventHandler(eventHandler);
    }

    @OnClick({R.id.register_back, R.id.register_get_check_pass, R.id.register_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_back:
                finish();
                break;
            case R.id.register_get_check_pass:
//                if (StringUtil.isMobileNO(registerPhone.getText().toString())) {
//                    mUserModel.isPhoneRegister(registerPhone.getText().toString(), new SportModelImpl.BaseListener() {
//                        @Override
//                        public void getSuccess(Object o) {
//                            ToastUtil.showLong(PhonevalidActivity.this, "当前手机号码已注册，请直接登录");
//                            startActivity(new Intent(PhonevalidActivity.this, LoginActivity.class));
//                        }
//
//                        @Override
//                        public void getFailure() {
//                            SMSSDK.getVerificationCode("86", registerPhone.getText().toString());
//                            CountDownButtonHelper countDownButtonHelper = new CountDownButtonHelper(registerGetCheckPass, "获取验证码", 60, 1);
//                            countDownButtonHelper.start();
//                            countDownButtonHelper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {
//                                @Override
//                                public void finish() {
//                                    registerGetCheckPass.setEnabled(true);
//                                }
//                            });
//                        }
//                    });
//                } else {
//                    ToastUtil.showLong(PhonevalidActivity.this, "手机号码格式不正确");
//                }

                String phone = registerPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(PhoneValidateActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    SMSSDK.getVerificationCode("86", phone);
                    Toast.makeText(PhoneValidateActivity.this, "手机号是：" + phone, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.register_btn:
                if (!TextUtils.isEmpty(register_checknum.getText().toString()) && !TextUtils.isEmpty(registerPhone.getText().toString())) {
                    SMSSDK.submitVerificationCode("86", registerPhone.getText().toString(), register_checknum.getText().toString());
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
                    Intent intent = new Intent(PhoneValidateActivity.this, RegisterActivity.class);
                    intent.putExtra("phone",phone);
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