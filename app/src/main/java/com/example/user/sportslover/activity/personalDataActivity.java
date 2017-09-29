package com.example.user.sportslover.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.example.user.sportslover.R;
import com.example.user.sportslover.model.UserModel;
import com.example.user.sportslover.user.UserLocal;
import com.example.user.sportslover.view.MyPageFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class personalDataActivity extends AppCompatActivity {
    @Bind(R.id.iv_Changephoto)
    ImageView UserPhoto;
    @Bind(R.id.personal_back)
    ImageView back;
    @Bind(R.id.tv_name)
    TextView name;
    @Bind(R.id.tv_gender)
    TextView gender;
    @Bind(R.id.tv_height)
    TextView height;
    @Bind(R.id.tv_weight)
    TextView weight;
    @Bind(R.id.tv_birthday)
    TextView birthday;
//    @Bind(R.id.et_name)
//    EditText et_name;
    String Name;
    TimePickerView pvTime;
    private OptionsPickerView pvOptions;
    private OptionsPickerView heightPvOptions;
    private ArrayList<String> optionsItems = new ArrayList<>();
    private ArrayList<String> heightOptionsItems = new ArrayList<>();
    private UserLocal mUserLocal;
    private UserModel mUserModel = new UserModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        mUserLocal = mUserModel.getUserLocal();
        ButterKnife.bind(this);
        Name = getIntent().getStringExtra("name");
        name.setText(Name);
//        gender.setText(mUserLocal.getSex());
//        height.setText(mUserLocal.getHeight());
//        weight.setText(mUserLocal.getWeight());
//        birthday.setText(mUserLocal.getBirthday());
    }

    @OnClick({R.id.personal_back, R.id.iv_Changephoto, R.id.tv_name, R.id.tv_gender, R.id.tv_height, R.id.tv_weight, R.id.tv_birthday})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal_back:
                Intent intent = new Intent();
                intent.setClass(personalDataActivity.this, MyPageFragment.class);
                startActivity(intent);
                break;
            case R.id.iv_Changephoto:

                break;
            case R.id.tv_name:
               editeName();
                //mUserLocal.setName(name.getText().toString());
                break;
            case R.id.tv_gender:
                change_sex();
                break;
            case R.id.tv_height:
                initHeightOptionPicker();
                heightPvOptions.show();
              //  mUserLocal.setHeight(height.getText().toString());
                break;
            case R.id.tv_weight:
                initOptionPicker();
                pvOptions.show();
              //  mUserLocal.setWeight(weight.getText().toString());
                break;
            //运动记录
            case R.id.tv_birthday:
                initTimePicker();
                pvTime.show();
             //   mUserLocal.setBirthday(birthday.getText().toString());
                break;

        }
    }



    public void change_sex(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this); //定义一个AlertDialog
        String[] strarr = {"male","female"};
        builder.setItems(strarr, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface arg0, int arg1)
            {
                String sex = "2";
                // 自动生成的方法存根
                if (arg1 == 0) {//男
                    gender.setText("male");
                }else {//女
                    gender.setText("female");
                }
                mUserLocal.setSex(gender.getText().toString());
            }
        });
        builder.show();
    }





    private void editeName(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View myLoginView = layoutInflater.inflate(R.layout.edite_name, null);

        Dialog alertDialog = new AlertDialog.Builder(this).
                setTitle("edite name").
                setIcon(R.drawable.ic_launcher).
                setView(myLoginView).
                setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                      //  name.setText(et_name.getText());
                        mUserLocal.setName(name.getText().toString());
                    }
                }).
                setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                }).
                create();
        alertDialog.show();

}


    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2017, 11, 27);
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                birthday.setText(getTime(date));
                mUserLocal.setBirthday(birthday.getText().toString());

            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    private void initOptionPicker() {//条件选择器初始化

        for (int i = 10; i < 200; i++) {
            optionsItems.add(Integer.toString(i));
        }
        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = optionsItems.get(options1);
                weight.setText(tx+"Kg");
                mUserLocal.setWeight(weight.getText().toString());
            }

        })
                .setSubmitText("Ok")//确定按钮文字
                .setCancelText("Cancel")//取消按钮文字
                .setTitleText("Weight")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .setTitleBgColor(0xfffffbfa)//标题背景颜色 Night mode
                .setBgColor(0xfffffbfa)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                .setLinkage(false)//设置是否联动，默认true
                .setLabels("kg","Kg","kg")//设置选择的三级单位
                .setCyclic(false,false,false)//循环与否
                .setSelectOptions(1,1,1)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .build();

        pvOptions.setPicker(optionsItems);//添加数据源
    }

    private void initHeightOptionPicker() {//条件选择器初始化

        for (int i = 100; i < 230; i++) {
            heightOptionsItems.add(Integer.toString(i));
        }
        /**
         * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
         */

        heightPvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = heightOptionsItems.get(options1);
                height.setText(tx+"cm");
                mUserLocal.setHeight(height.getText().toString());
            }
        })
                .setSubmitText("Ok")//确定按钮文字
                .setCancelText("Cancel")//取消按钮文字
                .setTitleText("Height")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .setTitleBgColor(0xfffffbfa)//标题背景颜色 Night mode
                .setBgColor(0xfffffbfa)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                .setLinkage(false)//设置是否联动，默认true
                .setLabels("cm","cm","cm")//设置选择的三级单位
                .setCyclic(false,false,false)//循环与否
                .setSelectOptions(1,1,1)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .build();

        heightPvOptions.setPicker(heightOptionsItems);//添加数据源
    }

    @Override
    protected void onStop() {
        super.onStop();
        mUserLocal.save();
    }
}
