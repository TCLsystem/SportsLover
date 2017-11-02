package com.example.user.sportslover.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.user.sportslover.R;
import com.example.user.sportslover.adapter.VPAdapter;
import com.example.user.sportslover.adapter.ViewPagerIndicator;
import com.example.user.sportslover.login.GetPhoneNumber;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.start_btn_login)
    Button login;
    @Bind(R.id.start_btn_join)
    Button Join;

    private ViewPager vp;
    private VPAdapter vpAdapter;
    private LinearLayout ll;
    private static final int MY_PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_splash2);

        boolean isAllGranted = checkPermissionAllGranted(new String[]{Manifest.permission
                .READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest
                .permission.RECORD_AUDIO, Manifest.permission.ACCESS_FINE_LOCATION, Manifest
                .permission.ACCESS_COARSE_LOCATION});

        // 如果这3个权限全都拥有, 则直接执行
        if (isAllGranted) {
            ButterKnife.bind(this);
            vp = (ViewPager) findViewById(R.id.vp);
            vpAdapter = new VPAdapter(this);
            vp.setAdapter(vpAdapter);
            ll = (LinearLayout) findViewById(R.id.ll);
            vp.setOnPageChangeListener(new ViewPagerIndicator(this, vp, ll, 3));
            return;
        }

        /**
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                .READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest
                .permission.RECORD_AUDIO, Manifest.permission.ACCESS_FINE_LOCATION, Manifest
                .permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_CODE);

    }


    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager
                    .PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }


    /**
     * 第 3 步: 申请权限结果返回处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                // 如果所有的权限都授予了, 则执行
                ButterKnife.bind(this);
                vp = (ViewPager) findViewById(R.id.vp);
                vpAdapter = new VPAdapter(this);
                vp.setAdapter(vpAdapter);
                ll = (LinearLayout) findViewById(R.id.ll);
                vp.setOnPageChangeListener(new ViewPagerIndicator(this, vp, ll, 3));

            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                openAppDetails();
            }
        }
    }


    /**
     * 打开 APP 的详情设置
     */
    private void openAppDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("该应用需要读写内存 ，请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }


    @OnClick({R.id.start_btn_login,R.id.start_btn_join})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_btn_login:
            startActivity(new Intent().setClass(SplashActivity.this,LoginActivity.class));
                break;
            case R.id.start_btn_join:
                startActivity(new Intent().setClass(SplashActivity.this,GetPhoneNumber.class));
              //  startActivity(new Intent().setClass(SplashActivity.this,RegisterActivity.class));
                break;
        }
    }

}
