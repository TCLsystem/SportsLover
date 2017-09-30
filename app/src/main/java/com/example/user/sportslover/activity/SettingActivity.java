package com.example.user.sportslover.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.sportslover.R;
import com.example.user.sportslover.widget.DialogHelper;
import com.example.user.sportslover.util.DataCleanManager;
import com.example.user.sportslover.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

public class SettingActivity extends AppCompatActivity {

    @Bind(R.id.personal)
    TextView personal;
    @Bind(R.id.cache)
    LinearLayout cache;
    @Bind(R.id.about)
    TextView about;
    @Bind(R.id.update)
    TextView update;
    @Bind(R.id.cacheSize)
    TextView txtCacheSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        try {

            txtCacheSize.setText(DataCleanManager.getTotalCacheSize(this));

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    @OnClick({R.id.personal, R.id.cache, R.id.about, R.id.update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal://隐私设置
//
                ToastUtil.showShort(SettingActivity.this, "设置个人信息");
                break;
            case R.id.cache://清除缓存
                //   mUserFragmentPresenter.onLogin(getActivity());
                new Thread(new clearCache()).start();
                break;
            case R.id.about://关于我们
                Log.d("TAAAAAAA","关于我们对话框");
                new DialogHelper(this).show();
                break;
            case R.id.update://检查更新

                BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
                    @Override
                    public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                        if (updateStatus == UpdateStatus.No) {
                            Log.d("TAAAAAA","检查更新");
                            Toast.makeText(SettingActivity.this, "版本无更新", Toast.LENGTH_SHORT).show();
                        } else if (updateStatus == UpdateStatus.Yes) {
                            BmobUpdateAgent.forceUpdate(SettingActivity.this);
                        }
                    }
                });
                BmobUpdateAgent.update(SettingActivity.this);
                ToastUtil.showLong(SettingActivity.this,"正在检查更新");
                break;
            default:
                break;
        }
    }


    class clearCache implements Runnable {

        @Override

        public void run() {

            try {

                DataCleanManager.clearAllCache(SettingActivity.this);

                Thread.sleep(3000);

                if (DataCleanManager.getTotalCacheSize(SettingActivity.this).startsWith("0")) {

                    handler.sendEmptyMessage(0);

                }

            } catch (Exception e) {

                return;

            }

        }

    }

    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {

                case 0:

                    Toast.makeText(SettingActivity.this,"清理完成",Toast.LENGTH_SHORT).show();

                    try {

                        txtCacheSize.setText(DataCleanManager.getTotalCacheSize(SettingActivity.this));

                    } catch (Exception e) {

                        e.printStackTrace();

                    }

            }

        };

    };






}
