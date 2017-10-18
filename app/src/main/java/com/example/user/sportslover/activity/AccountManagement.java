package com.example.user.sportslover.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.user.sportslover.R;
import com.example.user.sportslover.bean.UserLocal;
import com.example.user.sportslover.model.UserModelImpl;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountManagement extends AppCompatActivity {
   @Bind(R.id.logout)
    Button logout;
    @Bind(R.id.account_settings_back)
    ImageView back;

    UserLocal mUserLocal;
    UserModelImpl mUserModelImpl = new UserModelImpl();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.logout,R.id.account_settings_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.account_settings_back:
                finish();
                break;
            case R.id.logout:
                mUserLocal = mUserModelImpl.getUserLocal();
                UserLocal item = UserLocal.load(UserLocal.class, mUserLocal.getId());
                item.delete();
             startActivity(new Intent().setClass(AccountManagement.this,SplashActivity.class));
                finish();
                break;

        }
        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
