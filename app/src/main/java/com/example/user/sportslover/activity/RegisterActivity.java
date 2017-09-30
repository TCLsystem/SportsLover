package com.example.user.sportslover.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.sportslover.R;
import com.example.user.sportslover.bean.User;
import com.example.user.sportslover.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends Activity {


    @Bind(R.id.register_back)
    ImageView registerBack;
    @Bind(R.id.register_name)
    EditText registerName;
    @Bind(R.id.register_password)
    EditText registerPassword;
    @Bind(R.id.register_btn)
    Button registerBtn;

    private String mPhone;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        Bmob.initialize(this,"23fe35801c6ae4f698315d637955bb39");
        mPhone = getIntent().getStringExtra("phone");
        Log.d("TAAAAAAA",mPhone);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.register_back, R.id.register_btn})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.register_back:
                finish();
                break;
            case R.id.register_btn:
                String name = registerName.getText().toString();
                String password =registerPassword.getText().toString();
                if (!name.equals("")&& !password.equals("")&&!mPhone.equals("")) {
                    User user = new User();
                    user.setName(name);
                    user.setPassword(password);
                    user.setNumber(mPhone);
                    Log.d("TAAAAAAAA",name+"  "+password+"   "+mPhone);
                    user.save(this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(int code, String msg) {
                            // TODO Auto-generated method stub
                            Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    ToastUtil.showLong(RegisterActivity.this, "请填写完整信息");
                }
                break;
        }
    }
}