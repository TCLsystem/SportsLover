package com.example.user.sportslover.user;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.sportslover.R;
import com.example.user.sportslover.dto.User;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class RegisterActivity extends Activity {

    private EditText getName,getPassword,getNumber;
    private Button btnSign;
    private CompositeSubscription mCompositeSubscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getName=(EditText)findViewById(R.id.getName);
        getPassword=(EditText)findViewById(R.id.getPassword);
        getNumber=(EditText)findViewById(R.id.getNumber);
        btnSign=(Button)findViewById(R.id.btnSign);
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }


    //返回到登录页面
    public void ret(){

        Intent intent1 = new Intent();
        intent1.setClass(RegisterActivity.this, LoginActivity.class);
        RegisterActivity.this.startActivity(intent1);


    }
    @SuppressLint("UseValueOf")
    //点击注册
    public void Register(){

        String name=getName.getText().toString();
        final String password=getPassword.getText().toString();
        final String number=getNumber.getText().toString();
        if(name.equals("")||password.equals(""))
        {
            Toast.makeText(this, "帐号或密码不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if(name.length()<6)
        {
            Toast.makeText(this, "帐号小于6位", Toast.LENGTH_LONG).show();
            return;
        }
        if(number.length()==0)
        {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if(number.length()!=11)
        {
            Toast.makeText(this, "请输入11位有效号码", Toast.LENGTH_LONG).show();
            return;
        }
        final User myuser = new User();
        myuser.setUsername(name);
        myuser.setPassword(password);
        myuser.setNumber(number);
        addSubscription(myuser.signUp(new SaveListener<User>() {
            @Override
            public void done(User s, BmobException e) {
                if (e == null) {
                    ret();
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG).show();
                }
            }
        }));

    }


    private void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

}
