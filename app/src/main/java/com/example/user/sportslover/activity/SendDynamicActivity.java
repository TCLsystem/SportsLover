package com.example.user.sportslover.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.example.user.sportslover.R;
import com.example.user.sportslover.bean.DynamicItem;
import com.example.user.sportslover.bean.User;
import com.lljjcoder.city_20170724.CityPickerView;
import com.lljjcoder.city_20170724.bean.CityBean;
import com.lljjcoder.city_20170724.bean.DistrictBean;
import com.lljjcoder.city_20170724.bean.ProvinceBean;

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
    @Bind(R.id.LMile)
    LinearLayout lMile;
    @Bind(R.id.LStartTime)
    LinearLayout lStartTime;
    @Bind(R.id.LEndTime)
    LinearLayout lEndTime;
    @Bind(R.id.LApplicationDeadline)
    LinearLayout lDeadline;
    @Bind(R.id.LPlace)
    LinearLayout lPlace;
    @Bind(R.id.LArea)
    LinearLayout lArea;
    @Bind(R.id.tv_startTime)
    TextView tv_startTime;
    @Bind(R.id.tv_endTime)
    TextView tv_endTime;
    @Bind(R.id.tv_applicationDeadline)
    TextView tv_applicationDeadline;
    @Bind(R.id.tv_mile)
    TextView tv_mile;
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
    private ArrayList<String> mile = new ArrayList<String>();
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
        dynamicItem.setUserName(mUser.getUsername());
        ButterKnife.bind(this);
    }

    @OnClick({R.id.publish_next, R.id.personal_back,R.id.LType,R.id.LMile,R.id.LStartTime,R.id.LEndTime,R.id.LApplicationDeadline,R.id.LPlace,R.id.LArea})
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
                break;

            case R.id.LMile:
               for(int i=1;i<50;i++)
               {mile.add(String.valueOf(i));}
                initOptionPicker2(mile,tv_mile,"Activity Mile");
                pvOptions.show();
                break;
            case R.id.LStartTime:
                initTimePicker(tv_startTime,"Activity Start Time");
                pvTime.show();
                break;
            case R.id.LEndTime:
                initTimePicker1(tv_endTime,"Activity End Time");
                pvTime.show();
                break;
            case R.id.LApplicationDeadline:
                initTimePicker2(tv_applicationDeadline,"Application Deadline");
                pvTime.show();
                break;
            case R.id.LPlace:

                break;
            case R.id.LArea:
                showpick();
                break;
            default:
                break;


        }
    }


    private void initTimePicker(final TextView textView, String title) {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2017, 0, 01, 00, 00);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2017, 11, 31,23,59);
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                tv_startTime.setText(getTime(date));
                dynamicItem.setStartTime(date);
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



    private void initTimePicker1(final TextView textView, String title) {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2017, 0, 01, 00, 00);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2017, 11, 31,23,59);
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                tv_endTime.setText(getTime(date));
                dynamicItem.setEndtTime(date);
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



    private void initTimePicker2(final TextView textView, String title) {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2017, 0, 01, 00, 00);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2017, 11, 31,23,59);
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                tv_applicationDeadline.setText(getTime(date));
                dynamicItem.setApplicationDeadline(date);
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
        SimpleDateFormat format = new SimpleDateFormat("YYYY.MM.dd  HH:SS");
        return format.format(date);
    }


    private void initOptionPicker(final ArrayList<String> optionsItems , final TextView textView ,String title) {//条件选择器初始化


        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = optionsItems.get(options1);
                tv_type.setText(tx);
                dynamicItem.setSportsType(tv_type.getText().toString());
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

    private void initOptionPicker2(final ArrayList<String> optionsItems , final TextView textView ,String title) {//条件选择器初始化


        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = optionsItems.get(options1);
                tv_mile.setText(tx+"km");
                dynamicItem.setMeil(tv_mile.getText().toString());
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


    private  void showpick()
    {
        CityPickerView cityPicker = new CityPickerView.Builder(SendDynamicActivity.this)
                .textSize(20)
                .title("地址选择")
                .backgroundPop(0xa0000000)
                .titleBackgroundColor("#234Dfa")
                .titleTextColor("#000000")
                .backgroundPop(0xa0000000)
                .confirTextColor("#000000")
                .cancelTextColor("#000000")
                .province("江苏省")
                .city("常州市")
                .district("天宁区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();

        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                //返回结果
                //ProvinceBean 省份信息
                //CityBean     城市信息
                //DistrictBean 区县信息
                tv_area.setText(district.getPinYin()+ ","+ city.getPinYin() +","+ province.getPinYin());
                dynamicItem.setArea(district.getPinYin()+ ","+ city.getPinYin() +","+ province.getPinYin());
                tv_place.setText(district.getPinYin());
                dynamicItem.setPlace(district.getPinYin());
            }

            @Override
            public void onCancel() {

            }
        });

    }






    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}




