package com.example.user.sportslover.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bigkoo.pickerview.TimePickerView;
import com.example.user.sportslover.R;
import com.example.user.sportslover.util.DataCleanManager;
import com.example.user.sportslover.util.ToastUtil;
import com.example.user.sportslover.widget.DialogHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

public class SettingActivity extends AppCompatActivity {


    @Bind(R.id.setting_back)
    ImageView settings_back;
    @Bind(R.id.accountManagement)
    TextView accountManagement;
    @Bind(R.id.personal)
    TextView personal;
    @Bind(R.id.mTogBtn)
    ToggleButton mTogBtn;
    @Bind(R.id.Lnotification)
    LinearLayout Lnotification;
    @Bind(R.id.tv_notifyTime)
    TextView tv_notifyTime;

    @Bind(R.id.cache)
    LinearLayout cache;
    @Bind(R.id.about)
    TextView about;
    @Bind(R.id.update)
    TextView update;
    @Bind(R.id.cacheSize)
    TextView txtCacheSize;
    TimePickerView pvTime;

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

    @OnClick({R.id.setting_back,R.id.accountManagement,R.id.personal,R.id.mTogBtn, R.id.cache, R.id.about, R.id.update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_back:
                finish();
                break;

            case R.id.accountManagement:
                Intent intent1 = new Intent();
                intent1.setClass(this,AccountManagement.class);
                startActivity(intent1);
                break;

            case R.id.personal://隐私设置
                Intent intent = new Intent();
                intent.setClass(this,PrivacyActivity.class);
                startActivity(intent);
                ToastUtil.showShort(SettingActivity.this, "设置个人隐私");
                break;
            case R.id.sportsSettings:
                Intent intent2 = new Intent();
                intent2.setClass(this,SportsSettings.class);
                startActivity(intent2);
                break;


            case R.id.mTogBtn:
                mTogBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                        // TODO Auto-generated method stub
                        if(isChecked){
                            //选中的情况下，将下一个布局显示为可改变
                        }else{
                            //未选中
                            Lnotification.setAlpha((float) 0.3);
                            Lnotification.setOnClickListener(null);  //只需如此设置，即可达到效果
                        }
                    }
                });// 添加监听事件

            case R.id.Lnotification:
                 initTimePicker(tv_notifyTime,"Set Time");
                 pvTime.show();

            case R.id.cache://清除缓存;
                new AlertDialog.Builder(this)
                        .setMessage("Wipe the cache")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {// 积极

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub
                                new Thread(new clearCache()).start();
                            }
                        }).setNegativeButton("no", new DialogInterface.OnClickListener() {// 消极

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        // TODO Auto-generated method stub

                    }
                })
                        .show();

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


    private void initTimePicker(final TextView textView, String title) {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2017, 0, 23, 00, 00);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2017, 11, 31,23,59);
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                textView.setText(getTime(date));
            }
        })
                .setSubmitText("Ok")//确定按钮文字
                .setCancelText("Cancel")//取消按钮文字
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{false, false, false, true, true, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setTitleText(title)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("HH:SS");
        return format.format(date);
    }



}
