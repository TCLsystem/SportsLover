package com.example.user.sportslover.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.sportslover.R;
import com.example.user.sportslover.base.BaseActivity;
import com.example.user.sportslover.bean.User;
import com.example.user.sportslover.bean.UserEventBus;
import com.example.user.sportslover.bean.UserLocal;
import com.example.user.sportslover.model.SportModelInter;
import com.example.user.sportslover.model.UserModel;
import com.example.user.sportslover.model.UserModelImpl;
import com.example.user.sportslover.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;
import de.greenrobot.event.EventBus;

public class LoginActivity extends BaseActivity {
    @Bind(R.id.login_back)
    ImageView loginBack;
    @Bind(R.id.login_btn)
    Button loginBtn;
    @Bind(R.id.login_uname)
    EditText loginUname;
    @Bind(R.id.login_pass)
    EditText loginPass;
    @Bind(R.id.select_password)
    TextView select_name;
    @Bind(R.id.select_number)
    TextView select_number;
    @Bind(R.id.select_login_name)
    TextView select_login_stytle;
    @Bind(R.id.btn_login_getverify_code)
    Button verifycode;
    int flag = 0;

    private UserModelImpl mUserModelImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, "23fe35801c6ae4f698315d637955bb39");
        ButterKnife.bind(this);
        verifycode.setVisibility(View.GONE);
        mUserModelImpl = new UserModelImpl();
    }

    @OnClick({R.id.login_back,R.id.login_btn,R.id.select_number,R.id.select_password,R.id.btn_login_getverify_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_back:
                finish();
                break;
            case R.id.select_number:
                verifycode.setVisibility(View.GONE);
                verifycode.setVisibility(View.VISIBLE);
                select_login_stytle.setText("Phone Number");
                select_number.setTextColor(0xff000000);
                select_name.setTextColor(0xffb4b3b3);
                flag = 1;
                break;
            case R.id.select_password:
                verifycode.setVisibility(View.VISIBLE);
                select_name.setTextColor(0xff000000);
                verifycode.setVisibility(View.INVISIBLE);
                select_number.setTextColor(0xffb4b3b3);
                flag = 0;
                break;
//            case R.id.login_register:
//                Intent intent = new Intent(LoginActivity.this, PhoneValidateActivity.class);
//                startActivity(intent);
//                break;
            case R.id.btn_login_getverify_code:
                flag = 1;
                BmobSMS.requestSMSCode(LoginActivity.this, loginUname.getText().toString().trim(), "KeepFit", new RequestSMSCodeListener() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                        if (e == null) {//验证码发送成功
                            Log.i("smile", "短信id：");//用于后续的查询本次短信发送状态
                            Toast.makeText(LoginActivity.this, "Verification code have sent to your phone", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Verification code send failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                });


                break;
            case R.id.login_btn:
//                UserModel.getInstance().login(loginUname.getText().toString().trim(), loginPass.getText
//                        ().toString(), new LogInListener() {
//
//                    @Override
//                    public void done(Object o, BmobException e) {
//                        if (e == null) {
//                            User user = (User) o;
//                            ToastUtil.showLong(LoginActivity.this, "登录成功");
//                            UserLocal userLocal = new UserLocal();
//                            userLocal.setName(user.getUserName());
//                            userLocal.setObjectId(user.getObjectId());
//                            userLocal.setNumber(user.getNumber());
//                            userLocal.setWeight(user.getWeight());
//                            userLocal.setBirthday(user.getBirthday());
//                            userLocal.setHeight(user.getHeight());
//                            userLocal.setSex(user.getSex());
//                            if (user.getPhoto() != null) {
//                                userLocal.setPhoto(user.getPhoto().getUrl());
//                            }
//                            mUserModelImpl.putUserLocal(userLocal);
//                            BmobIM.getInstance().updateUserInfo(new BmobIMUserInfo(user
//                                    .getObjectId(), user.getUserName(), user.getAvatar()));
//                            startActivity(MainActivity.class, null, true);
//                        } else {
//                            toast(e.getMessage() + "(" + e.getErrorCode() + ")");
//                        }
//                    }
//                });


                if (!TextUtils.isEmpty(loginUname.getText().toString().trim()) && !TextUtils.isEmpty(loginPass.getText().toString())) {
                    if (flag == 0) {
                /*        mUserModelImpl.getUser(loginUname.getText().toString().trim(), loginPass.getText().toString().trim(), new SportModelInter.BaseListener() {
                            @Override
                            public void getSuccess(Object o) {
                                ToastUtil.showLong(LoginActivity.this, "登录成功");
                                User user = (User) o;
                                UserLocal userLocal = new UserLocal();
                                userLocal.setName(user.getUsername());
                                userLocal.setObjectId(user.getObjectId());
                                userLocal.setNumber(user.getNumber());
                                userLocal.setWeight(user.getWeight());
                                userLocal.setBirthday(user.getBirthday());
                                userLocal.setHeight(user.getHeight());
                                userLocal.setSex(user.getSex());
                                userLocal.setAvatar(user.getAvatar());
                                if (user.getPhoto() != null) {
                                    userLocal.setPhoto(user.getPhoto().getUrl());
                                }
                                mUserModelImpl.putUserLocal(userLocal);
                                BmobIM.getInstance().updateUserInfo(new BmobIMUserInfo(user
                                        .getObjectId(), user.getUsername(), user.getAvatar()));
                                startActivity(MainActivity.class, null, true);
                                EventBus.getDefault().post(new UserEventBus(userLocal));
                                finish();
                            }

                            @Override
                            public void getFailure() {
                                ToastUtil.showLong(LoginActivity.this, "登录失败");
                            }
                        });*/
                        UserModel.getInstance().login(loginUname.getText().toString().trim(), loginPass
                                .getText().toString(), new LogInListener() {
                            @Override
                            public void done(Object o, BmobException e) {
                                if (e == null) {
                                    ToastUtil.showLong(LoginActivity.this, "登录成功");
                                    User user = (User) o;
                                    UserLocal userLocal = new UserLocal();
                                    userLocal.setName(user.getUsername());
                                    userLocal.setObjectId(user.getObjectId());
                                    userLocal.setNumber(user.getNumber());
                                    userLocal.setWeight(user.getWeight());
                                    userLocal.setBirthday(user.getBirthday());
                                    userLocal.setHeight(user.getHeight());
                                    userLocal.setSex(user.getSex());
                                    userLocal.setAvatar(user.getAvatar());
                                    if (user.getPhoto() != null) {
                                        userLocal.setPhoto(user.getPhoto().getUrl());
                                    }
                                    mUserModelImpl.putUserLocal(userLocal);
                                    BmobIM.getInstance().updateUserInfo(new BmobIMUserInfo(user
                                            .getObjectId(), user.getUsername(), user.getAvatar()));
                                    startActivity(MainActivity.class, null, true);
                                    EventBus.getDefault().post(new UserEventBus(userLocal));
                                    finish();
                                } else {
                                    //toast(e.getMessage() + "(" + e.getErrorCode() + ")");
                                    ToastUtil.showLong(LoginActivity.this, "login fail");
                                }
                            }
                        });
                    } else if (flag == 1) {

                        BmobSMS.verifySmsCode(LoginActivity.this, loginUname.getText().toString().trim(), loginPass.getText().toString().trim(), new VerifySMSCodeListener() {
                            @Override
                            public void done(BmobException e) {

                                mUserModelImpl.getUserbyPhone(loginUname.getText().toString().trim(), new SportModelInter.BaseListener() {
                                    @Override
                                    public void getSuccess(Object o) {
                                        ToastUtil.showLong(LoginActivity.this, "login success");
                                        User user = (User) o;
                                        UserLocal userLocal = new UserLocal();
                                        userLocal.setName(user.getUsername());
                                        userLocal.setObjectId(user.getObjectId());
                                        userLocal.setNumber(user.getNumber());
                                        userLocal.setWeight(user.getWeight());
                                        userLocal.setBirthday(user.getBirthday());
                                        userLocal.setHeight(user.getHeight());
                                        userLocal.setSex(user.getSex());
                                        userLocal.setAvatar(user.getAvatar());
                                        if (user.getPhoto() != null) {
                                            userLocal.setPhoto(user.getPhoto().getUrl());
                                        }
                                        mUserModelImpl.putUserLocal(userLocal);
                                        BmobIM.getInstance().updateUserInfo(new BmobIMUserInfo(user
                                                .getObjectId(), user.getUsername(), user.getAvatar()));
                                        startActivity(MainActivity.class, null, true);
                                        EventBus.getDefault().post(new UserEventBus(userLocal));
                                        finish();
                                    }

                                    @Override
                                    public void getFailure() {
                                        ToastUtil.showLong(LoginActivity.this, "login fail");
                                    }
                                });
                            }
                        });
                    }
                    break;
                }
        }
    }
}
