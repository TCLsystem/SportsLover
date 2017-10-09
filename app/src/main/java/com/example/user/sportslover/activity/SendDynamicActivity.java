package com.example.user.sportslover.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.example.user.sportslover.R;
import com.example.user.sportslover.bean.CityJsonBean;
import com.example.user.sportslover.bean.DynamicItem;
import com.example.user.sportslover.bean.User;
import com.example.user.sportslover.util.GetJsonDataUtil;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendDynamicActivity extends Activity {
    private final int REQUEST_CODE = 0x01;
    @Bind(R.id.personal_back)
    ImageView back;
    @Bind(R.id.publish_next)
    TextView next;
    @Bind(R.id.LType)
    LinearLayout lType;
    @Bind(R.id.LStartTime)
    LinearLayout lStartTime;
    @Bind(R.id.LEndTime)
    LinearLayout lEndTime;
    @Bind(R.id.LPlace)
    LinearLayout lPlace;
    @Bind(R.id.LArea)
    LinearLayout lArea;
//    @Bind(R.id.send)
//    TextView send;
//    @Bind(R.id.edit_activity_name)
//    EditText et_activity_name;
//    @Bind(R.id.edit_content)
//    EditText et_content;
//    @Bind(R.id.gridView)
//    GridView gridView;
    @Bind(R.id.tv_startTime)
    TextView tv_startTime;
    @Bind(R.id.tv_endTime)
    TextView tv_endTime;
    @Bind(R.id.tv_type)
    TextView tv_type;
    @Bind(R.id.tv_place)
    TextView tv_place;
    @Bind(R.id.tv_area)
    TextView tv_area;





    private final String LOGINUSER = "loginuser";
    private User mUser;
    private Dialog mLoadingDialog;
    private Dialog mLoadingFinishDialog;

    TimePickerView pvTime;
    DynamicItem dynamicItem = new DynamicItem();
    private OptionsPickerView pvOptions;
    private OptionsPickerView pvOptionsCity;
    private ArrayList<String> type = new ArrayList<String>();
    private ArrayList<CityJsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    private boolean isLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_send_dynamic);
        mUser = (User) getIntent().getSerializableExtra("User");
        dynamicItem.setWriter(mUser);
        dynamicItem.setUserName(mUser.getName());
        ButterKnife.bind(this);
    }

    @OnClick({R.id.publish_next, R.id.personal_back,R.id.LType,R.id.LStartTime,R.id.LEndTime,R.id.LPlace,R.id.LArea})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal_back:
                finish();
                break;
            case R.id.publish_next:
                Intent intent = new Intent(SendDynamicActivity.this, SendDynamicActivity2.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("DynamicItem", dynamicItem);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.LType:
                type.add("Running");
                type.add("Walking");
                type.add("Riding");
                initOptionPicker(type,tv_type,"Activity Type");
                pvOptions.show();
                dynamicItem.setSportsType(tv_type.getText().toString());
                break;
            case R.id.LStartTime:
                initTimePicker(tv_startTime,"Activity Time");
                pvTime.show();
                dynamicItem.setStartTime(tv_startTime.getText().toString());
                break;
            case R.id.LEndTime:
                initTimePicker(tv_endTime,"Application Deadline");
                pvTime.show();
                dynamicItem.setStartTime(tv_endTime.getText().toString());
                break;
            case R.id.LPlace:
                if (isLoaded){
                    pvOptionsCity.show();
                    dynamicItem.setPlace(tv_place.getText().toString());
                }else {
                  //  Toast.makeText(JsonDataActivity.this,"Please waiting until the data is parsed",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.LArea:
                dynamicItem.setArea(tv_area.getText().toString());
                break;


        }
    }






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
                .setType(new boolean[]{false, true, true, true, true, false})
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
        SimpleDateFormat format = new SimpleDateFormat("YYYY.MM.dd   HH:SS");
        return format.format(date);
    }


    private void initOptionPicker(final ArrayList<String> optionsItems , final TextView textView ,String title) {//条件选择器初始化


        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = optionsItems.get(options1);
                textView.setText(tx);
            }

        })
                .setSubmitText("Ok")//确定按钮文字
                .setCancelText("Cancel")//取消按钮文字
                .setTitleText(title)//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .setTitleBgColor(0xfffffbfa)//标题背景颜色 Night mode
                .setBgColor(0xfffffbfa)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                .setLinkage(false)//设置是否联动，默认true
//                .setLabels("kg","Kg","kg")//设置选择的三级单位
                .setCyclic(false,false,false)//循环与否
                .setSelectOptions(1,1,1)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .build();

        pvOptions.setPicker(optionsItems);//添加数据源
    }




    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread==null){//如果已创建就不再重新创建子线程了

   //                     Toast.makeText(JsonDataActivity.this,"Begin Parse Data",Toast.LENGTH_SHORT).show();
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
   //                 Toast.makeText(JsonDataActivity.this,"Parse Succeed",Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
    //                Toast.makeText(JsonDataActivity.this,"Parse Failed",Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };


    private void ShowPickerView() {// 弹出选择器

        pvOptionsCity = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()+
                        options2Items.get(options1).get(options2)+
                        options3Items.get(options1).get(options2).get(options3);
                        tv_place.setText(tx);
                        tv_area.setText(options2Items.get(options1).get(options2));
               // Toast.makeText(JsonDataActivity.this,tx,Toast.LENGTH_SHORT).show();
            }
        })

                .setTitleText("Activity Location")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptionsCity.setPicker(options1Items, options2Items,options3Items);//三级选择器
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this,"province.json");//获取assets目录下的json文件数据

        ArrayList<CityJsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i=0;i<jsonBean.size();i++){//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c=0; c<jsonBean.get(i).getCityList().size(); c++){//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        ||jsonBean.get(i).getCityList().get(c).getArea().size()==0) {
                    City_AreaList.add("");
                }else {

                    for (int d=0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    public ArrayList<CityJsonBean> parseData(String result) {//Gson 解析
        ArrayList<CityJsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                CityJsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), CityJsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}




