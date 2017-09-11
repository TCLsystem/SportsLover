package com.example.user.sportslover.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.sportslover.MainActivity;
import com.example.user.sportslover.R;
import com.example.user.sportslover.dto.User;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import rx.Subscriber;

public class LoginActivity extends BaseActivity{


    private EditText etusername;
    private EditText etpassword;
    private Button login;
    private Button sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
        initView();
    }

    private void initView() {

    }

    private void initialize() {

        etusername = (EditText) findViewById(R.id.et_username);
        etpassword = (EditText) findViewById(R.id.et_password);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlogin();
            }
        });
        sign = (Button) findViewById(R.id.sign);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    //登录点击
    public void dlogin() {
        String name = etusername.getText().toString();
        String password = etpassword.getText().toString();


        final User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        //login回调
        /*user.login(new SaveListener<BmobUser>() {

			@Override
			public void done(BmobUser bmobUser, BmobException e) {
				if(e==null){
					toast(user.getUsername() + "登陆成功");
					testGetCurrentUser();
				}else{
					loge(e);
				}
			}
		});*/
        //v3.5.0开始新增加的rx风格的Api
        user.loginObservable(BmobUser.class).subscribe(new Subscriber<BmobUser>() {
            @Override
            public void onCompleted() {
                log("----onCompleted----");
            }

            @Override
            public void onError(Throwable e) {
                loge(new BmobException(e));
                Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(BmobUser bmobUser) {
                Intent intent1 = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent1);
                toast(bmobUser.getUsername() + "登陆成功");
                testGetCurrentUser();
            }
        });

    }


    private void testGetCurrentUser() {
//		MyUser myUser = BmobUser.getCurrentUser(this, MyUser.class);
//		if (myUser != null) {
//			log("本地用户信息:objectId = " + myUser.getObjectId() + ",name = " + myUser.getUsername()
//					+ ",age = "+ myUser.getAge());
//		} else {
//			toast("本地用户为null,请登录。");
//		}
        //V3.4.5版本新增加getObjectByKey方法获取本地用户对象中某一列的值
        String username = (String) BmobUser.getObjectByKey("username");
        Integer age = (Integer) BmobUser.getObjectByKey("age");
        Boolean sex = (Boolean) BmobUser.getObjectByKey("sex");
        JSONArray hobby = (JSONArray) BmobUser.getObjectByKey("hobby");
        JSONArray cards = (JSONArray) BmobUser.getObjectByKey("cards");
        JSONObject banker = (JSONObject) BmobUser.getObjectByKey("banker");
        JSONObject mainCard = (JSONObject) BmobUser.getObjectByKey("mainCard");
        log("username：" + username + ",\nage：" + age + ",\nsex：" + sex);
        log("hobby:" + (hobby != null ? hobby.toString() : "为null") + "\ncards:" + (cards != null ? cards.toString() : "为null"));
        log("banker:" + (banker != null ? banker.toString() : "为null") + "\nmainCard:" + (mainCard != null ? mainCard.toString() : "为null"));
    }

}
