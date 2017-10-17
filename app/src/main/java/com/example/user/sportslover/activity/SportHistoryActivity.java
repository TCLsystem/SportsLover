package com.example.user.sportslover.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.user.sportslover.R;
import com.example.user.sportslover.adapter.SportHistoryDataAdapter;
import com.example.user.sportslover.bean.ScrollBarBean;
import com.example.user.sportslover.bean.ScrollPerBarBean;
import com.example.user.sportslover.bean.SportHistoryDataBean;
import com.example.user.sportslover.customview.ScrollBarPicMonth;
import com.example.user.sportslover.customview.ScrollBarPicWeek;
import com.example.user.sportslover.customview.ScrollViewDragHelperMonth;
import com.example.user.sportslover.customview.ScrollViewDragHelperWeek;
import com.example.user.sportslover.model.SportHistoryModelImpr;
import com.example.user.sportslover.presenter.SportHistoryPresenter;
import com.example.user.sportslover.presenter.SportHistoryPresenterRidingMonthImpr;
import com.example.user.sportslover.presenter.SportHistoryPresenterRidingWeekImpr;
import com.example.user.sportslover.presenter.SportHistoryPresenterRunningMonthImpr;
import com.example.user.sportslover.presenter.SportHistoryPresenterRunningWeekImpr;
import com.example.user.sportslover.presenter.SportHistoryPresenterWalkingMonthImpr;
import com.example.user.sportslover.presenter.SportHistoryPresenterWalkingWeekImpr;
import com.example.user.sportslover.widget.MyVerticalViewPager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SportHistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private MyVerticalViewPager myVerticalViewPager;
    private ArrayList<View> pageview;
    private View view0;
    private View view1;
    private ScrollViewDragHelperWeek scrollViewDragHelperWeek;
    private ScrollViewDragHelperMonth scrollViewDragHelperMonth;
    private ScrollBarPicWeek scrollBarPicWeek;
    private ScrollBarPicMonth scrollBarPicMonth;
    private ScrollBarBean scrollBarBean = new ScrollBarBean();
    private ScrollBarBean scrollBarBeanWeek = new ScrollBarBean();
    private ScrollBarBean scrollBarBeanMonth = new ScrollBarBean();
    private ArrayList<ScrollPerBarBean> lists = new ArrayList<>();
    private List<SportHistoryDataBean> sportHistoryDataBeanList = new ArrayList<>();
    private SportHistoryDataAdapter listviewAdapter;
    private SportHistoryPresenter sportHistoryPresenter;
    private TextView tvTotalDistance;
    private TextView tvTotalCalories;
    private TextView tvTotalCumulativeTime;
    private Button buttonRunning;
    private Button buttonWalking;
    private Button buttonRiding;
    private ImageView ivSetTargetBack;
    private ImageView ivScrollUp;
    private ImageView ivScrollDown;
    private Drawable drawableClicked;
    private Drawable drawableUnclicked;
    private ListView selectButtonItem;
    private TextView tvselectButton;
    private List<String> stringList;
    private ArrayAdapter<String> stringArrayAdapter;
    private PopupWindow popupWindow;
    private int currSport = 0;
    private int currTimeScale = 1;


    private DecimalFormat textFormat = new DecimalFormat("#0.0");
    private DecimalFormat caloriesFormat = new DecimalFormat("#0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_history);
        view0 = getLayoutInflater().inflate(R.layout.item_chart_viewpager, null);
        view1 = getLayoutInflater().inflate(R.layout.item_listview_viewpager, null);
        pageview =new ArrayList<View>();
        pageview.add(view0);
        pageview.add(view1);
        tvTotalDistance = (TextView) view1.findViewById(R.id.tv_history_total_distance);
        tvTotalCalories = (TextView) view1.findViewById(R.id.tv_history_total_calories);
        tvTotalCumulativeTime = (TextView) view1.findViewById(R.id.tv_history_total_cumulative_time);
        ivScrollUp = (ImageView) view1.findViewById(R.id.history_scroll_up);
        ivScrollDown = (ImageView) view1.findViewById(R.id.history_scroll_down);
        scrollViewDragHelperWeek = (ScrollViewDragHelperWeek) view0.findViewById(R.id.scroll_view_drag_helper_week);
        scrollViewDragHelperMonth = (ScrollViewDragHelperMonth) view0.findViewById(R.id.scroll_view_drag_helper_month);
        scrollBarPicWeek = (ScrollBarPicWeek) view0.findViewById(R.id.scroll_bar_pic_week);
        scrollBarPicWeek.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (scrollBarBeanWeek.getLists() != null)
                    scrollBarPicWeek.setDatas(scrollBarBeanWeek);
            }
        });
        scrollBarPicMonth = (ScrollBarPicMonth) view0.findViewById(R.id.scroll_bar_pic_month);
        scrollBarPicMonth.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (scrollBarBeanMonth.getLists() != null)
                    scrollBarPicMonth.setDatas(scrollBarBeanMonth);
            }
        });
        myVerticalViewPager = (MyVerticalViewPager) findViewById(R.id.history_vertical_viewpager);
        PagerAdapter mPageAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return pageview.size();
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0==arg1;
            }

            public void destroyItem(View arg0, int arg1, Object arg2) {
                ((MyVerticalViewPager) arg0).removeView(pageview.get(arg1));
            }

            public Object instantiateItem(View arg0, int arg1){
                ((MyVerticalViewPager)arg0).addView(pageview.get(arg1));
                return pageview.get(arg1);
            }

            @Override
            public float getPageWidth(int position) {
                if (position == 0)
                    return 0.77f;
                else
                    return super.getPageWidth(position);
            }
        };
        myVerticalViewPager.setAdapter(mPageAdapter);
        myVerticalViewPager.setCurrentItem(0);
        myVerticalViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        listviewAdapter = new SportHistoryDataAdapter(SportHistoryActivity.this, R.layout.item_history_data, sportHistoryDataBeanList);
        ListView listView = (ListView) view1.findViewById(R.id.lv_history_data);
        listView.setAdapter(listviewAdapter);
        listView.setDividerHeight(0);
        tvselectButton = (TextView) findViewById(R.id.tv_history_select_button);
        tvselectButton.setOnClickListener(this);
        buttonRunning = (Button) view0.findViewById(R.id.set_target_type_running);
        buttonRunning.setOnClickListener(this);
        buttonWalking = (Button) view0.findViewById(R.id.set_target_type_walking);
        buttonWalking.setOnClickListener(this);
        buttonRiding = (Button) view0.findViewById(R.id.set_target_type_riding);
        buttonRiding.setOnClickListener(this);
        ivSetTargetBack = (ImageView) findViewById(R.id.history_back);
        ivSetTargetBack.setOnClickListener(this);
        drawableClicked = buttonRunning.getBackground();
        drawableUnclicked = buttonWalking.getBackground();
        refleshCharts();
    }

    private void refleshCharts(){
        sportHistoryPresenter = selectPresenter(currSport, currTimeScale);
        sportHistoryPresenter.loadHistoryData(getApplicationContext(), new SportHistoryModelImpr.OnSportHistoryListener() {
            @Override
            public void onSuccess(List<SportHistoryDataBean> sportHistoryDataBeanList1) {
                String html;
                float tempDistance = 0, tempCalories = 0, tempCumulativeTime = 0;
                sportHistoryDataBeanList.clear();
                sportHistoryDataBeanList.addAll(sportHistoryDataBeanList1);
                switch (currTimeScale){
                    case 0:
                        scrollBarBeanWeek.setTotal(100);
                        lists.clear();
                        for (int i = 0; i< sportHistoryDataBeanList.size(); i++){
                            lists.add(new ScrollPerBarBean(sportHistoryDataBeanList.get(i).getMonth() + sportHistoryDataBeanList.get(i).getDate(),
                                    (int)(sportHistoryDataBeanList.get(i).getDistance()/20*100), Float.toString(sportHistoryDataBeanList.get(i).getDistance()) + "km"));
                        }
                        scrollBarBeanWeek.setLists(lists);
                        scrollBarPicWeek.invalidate();
                        break;
                    case 1:
                        scrollBarBeanMonth.setTotal(100);
                        lists.clear();
                        for (int i = 0; i< sportHistoryDataBeanList.size(); i++){
                            lists.add(new ScrollPerBarBean(sportHistoryDataBeanList.get(i).getMonth() + sportHistoryDataBeanList.get(i).getDate(),
                                    (int)(sportHistoryDataBeanList.get(i).getDistance()/20*100), Float.toString(sportHistoryDataBeanList.get(i).getDistance()) + "km"));
                        }
                        scrollBarBeanMonth.setLists(lists);
                        scrollBarPicMonth.invalidate();
                        break;
                    default:
                        break;
                }
                for (int i = 0; i < sportHistoryDataBeanList.size(); i++){
                    tempDistance += sportHistoryDataBeanList.get(i).getDistance();
                    tempCalories += sportHistoryDataBeanList.get(i).getCalories();
                    tempCumulativeTime += sportHistoryDataBeanList.get(i).getCumulativeTime();
                }
                html = "<big><big><big>" + textFormat.format(tempDistance) + "</big></big> km</big><br>Distance";
                tvTotalDistance.setText(Html.fromHtml(html));
                html = "<big><big><big>" + caloriesFormat.format(tempCalories) + "</big></big> kcal</big><br>Calories";
                tvTotalCalories.setText(Html.fromHtml(html));
                html = "<big><big><big>" + textFormat.format(tempCumulativeTime)  + "</big></big> h</big><br>Cumulative<br>time";
                tvTotalCumulativeTime.setText(Html.fromHtml(html));
                listviewAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set_target_type_running:
                initButtons();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    buttonRunning.setBackground(drawableClicked);
                }
                buttonRunning.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                buttonRunning.setTextColor(Color.BLACK);
                currSport = 0;
                refleshCharts();
                break;
            case R.id.set_target_type_walking:
                initButtons();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    buttonWalking.setBackground(drawableClicked);
                }
                buttonWalking.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                buttonWalking.setTextColor(Color.BLACK);
                currSport = 1;
                refleshCharts();
                break;
            case R.id.set_target_type_riding:
                initButtons();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    buttonRiding.setBackground(drawableClicked);
                }
                buttonRiding.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                buttonRiding.setTextColor(Color.BLACK);
                currSport = 2;
                refleshCharts();
                break;
            case R.id.tv_history_select_button:
                // 点击控件后显示popup窗口
                initSelectPopup();
                // 使用isShowing()检查popup窗口是否在显示状态
                if (popupWindow != null && !popupWindow.isShowing()) {
                    popupWindow.showAsDropDown(tvselectButton, 0, 10);
                }
                break;
            case R.id.set_target_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void initButtons(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            buttonRunning.setBackground(drawableUnclicked);
            buttonWalking.setBackground(drawableUnclicked);
            buttonRiding.setBackground(drawableUnclicked);
        }
        buttonRunning.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        buttonWalking.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        buttonRiding.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        buttonRunning.setTextColor(Color.WHITE);
        buttonWalking.setTextColor(Color.WHITE);
        buttonRiding.setTextColor(Color.WHITE);
    }

    private void initSelectPopup() {
        selectButtonItem = new ListView(this);
        stringList = new ArrayList<>();
        stringList.add("WEEK");
        stringList.add("MONTH");
        // 设置适配器
        stringArrayAdapter = new ArrayAdapter<String>(this, R.layout.item_popup_text, stringList);
        selectButtonItem.setDividerHeight(0);
        selectButtonItem.setAdapter(stringArrayAdapter);

        // 设置ListView点击事件监听
        selectButtonItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 在这里获取item数据
                String value = stringList.get(position);
                // 把选择的数据展示对应的TextView上
                tvselectButton.setText(value);
                currTimeScale = position;
                refleshCharts();
                // 选择完后关闭popup窗口
                popupWindow.dismiss();
            }
        });
        popupWindow = new PopupWindow(selectButtonItem, tvselectButton.getWidth(), ActionBar.LayoutParams.WRAP_CONTENT, true);
        // 取得popup窗口的背景图片
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.bg_history_corner);
        popupWindow.setBackgroundDrawable(drawable);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // 关闭popup窗口
                popupWindow.dismiss();
            }
        });
    }

    private SportHistoryPresenter selectPresenter(int currSport, int currTimeScale){
        switch (currTimeScale){
            case 0:
                scrollViewDragHelperWeek.setVisibility(View.VISIBLE);
                scrollViewDragHelperMonth.setVisibility(View.GONE);
                switch (currSport){
                    case 0:
                        return new SportHistoryPresenterRunningWeekImpr();
                    case 1:
                        return new SportHistoryPresenterWalkingWeekImpr();
                    case 2:
                        return new SportHistoryPresenterRidingWeekImpr();
                }
                break;
            case 1:
                scrollViewDragHelperWeek.setVisibility(View.GONE);
                scrollViewDragHelperMonth.setVisibility(View.VISIBLE);
                switch (currSport){
                    case 0:
                        return new SportHistoryPresenterRunningMonthImpr();
                    case 1:
                        return new SportHistoryPresenterWalkingMonthImpr();
                    case 2:
                        return new SportHistoryPresenterRidingMonthImpr();
                }
                break;
            default:
                break;
        }
        return new SportHistoryPresenterRunningWeekImpr();
    }


    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    ivScrollUp.setVisibility(View.VISIBLE);
                    ivScrollDown.setVisibility(View.GONE);
                    break;
                case 1:
                    ivScrollUp.setVisibility(View.GONE);
                    ivScrollDown.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
