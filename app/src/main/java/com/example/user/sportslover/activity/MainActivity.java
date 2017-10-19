package com.example.user.sportslover.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.sportslover.R;
import com.example.user.sportslover.base.BaseActivity;
import com.example.user.sportslover.bean.UserLocal;
import com.example.user.sportslover.db.NewFriendManager;
import com.example.user.sportslover.event.RefreshEvent;
import com.example.user.sportslover.fragment.ContactFragment;
import com.example.user.sportslover.fragment.HomeFragment;
import com.example.user.sportslover.fragment.MyPageFragment;
import com.example.user.sportslover.fragment.SportsEventFragment;
import com.example.user.sportslover.model.UserModelImpl;
import com.example.user.sportslover.util.IMMLeaks;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.exception.BmobException;

public class MainActivity extends BaseActivity implements MainView,View.OnClickListener {

    private LinearLayout ll_home;
    private LinearLayout ll_contacts;
    private LinearLayout ll_sportsEvent;
    private LinearLayout ll_myPage;

    private ImageView iv_home;
    private ImageView iv_contacts;
    private ImageView iv_sportsEvent;
    private ImageView iv_myPage;

    private TextView tv_home;
    private TextView tv_contacts;
    private TextView tv_sportsEvent;
    private TextView tv_myPage;

    private Fragment homeFragment;
    private Fragment contactsFragment;
    private Fragment sportsEventFragment;
    private Fragment myPageFragment;
    private UserLocal mUserLocal;
    private UserModelImpl mUserModelImpl = new UserModelImpl();

    @Bind(R.id.iv_contact_tips)
    ImageView iv_contact_tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Bmob.initialize(this,"23fe35801c6ae4f698315d637955bb39");
        initView();
        initEvent();
        initFragment(0);

        mUserLocal = mUserModelImpl.getUserLocal();

        BmobIM.connect(mUserLocal.getObjectId(), new ConnectListener() {
            @Override
            public void done(String uid, BmobException e) {
                if (e == null) {
                    Logger.i("connect success");
                    //服务器连接成功就发送一个更新事件，同步更新会话及主页的小红点
                    EventBus.getDefault().post(new RefreshEvent());
                } else {
                    Logger.e(e.getErrorCode() + "/" + e.getMessage());
                }
            }
        });
        //监听连接状态，也可通过BmobIM.getInstance().getCurrentStatus()来获取当前的长连接状态
        BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
            @Override
            public void onChange(ConnectionStatus status) {
                toast("" + status.getMsg());
            }
        });
        //解决leancanary提示InputMethodManager内存泄露的问题
        IMMLeaks.fixFocusedViewLeak(getApplication());
    }

    private void initEvent() {
        ll_home.setOnClickListener(this);
        ll_contacts.setOnClickListener(this);
        ll_sportsEvent.setOnClickListener(this);
        ll_myPage.setOnClickListener(this);
    }

    public void initView() {

        this.ll_home = (LinearLayout) findViewById(R.id.ll_home);
        this.ll_contacts = (LinearLayout) findViewById(R.id.ll_contacts);
        this.ll_sportsEvent = (LinearLayout) findViewById(R.id.ll_sport_event);
        this.ll_myPage = (LinearLayout) findViewById(R.id.ll_my_page);

        this.iv_home = (ImageView) findViewById(R.id.iv_home);
        this.iv_contacts = (ImageView) findViewById(R.id.iv_contacts);
        this.iv_sportsEvent = (ImageView) findViewById(R.id.iv_sport_event);
        this.iv_myPage = (ImageView) findViewById(R.id.iv_my_page);

        this.tv_home = (TextView) findViewById(R.id.tv_home);
        this.tv_contacts = (TextView) findViewById(R.id.tv_contacts);
        this.tv_sportsEvent = (TextView) findViewById(R.id.tv_sport_event);
        this.tv_myPage = (TextView) findViewById(R.id.tv_my_page);

    }

    @Override
    public void onClick(View v) {
        restartBottom();
        switch (v.getId()) {
            case R.id.ll_home:
                switch2Home();
                break;
            case R.id.ll_contacts:
                switch2Contacts();
                break;
            case R.id.ll_sport_event:
                switch2SportsEvent();
                break;
            case R.id.ll_my_page:
                switch2MyPage();
                break;
            default:
                break;
        }

    }

    @Override
    public void initFragment(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (index) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.fl_content, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                break;
            case 1:
                if (contactsFragment == null) {
                    contactsFragment = new ContactFragment();
                    transaction.add(R.id.fl_content, contactsFragment);
                } else {
                    transaction.show(contactsFragment);
                }
                break;
            case 2:
                if (sportsEventFragment == null) {
                    sportsEventFragment = new SportsEventFragment();
                    transaction.add(R.id.fl_content, sportsEventFragment);
                } else {
                    transaction.show(sportsEventFragment);
                }

                break;
            case 3:
                if (myPageFragment == null) {
                    myPageFragment = new MyPageFragment();
                    transaction.add(R.id.fl_content, myPageFragment);
                } else {
                    transaction.show(myPageFragment);
                }

                break;

            default:
                break;
        }
        transaction.commit();
    }

    @Override
    public void hideFragment(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (contactsFragment != null) {
            transaction.hide(contactsFragment);
        }
        if (sportsEventFragment != null) {
            transaction.hide(sportsEventFragment);
        }
        if (myPageFragment != null) {
            transaction.hide(myPageFragment);
        }
    }


    @Override
    public void restartBottom() {
        iv_home.setImageResource(R.drawable.home_down);
        iv_contacts.setImageResource(R.drawable.contact_down);
        iv_sportsEvent.setImageResource(R.drawable.share_down);
        iv_myPage.setImageResource(R.drawable.mirror_down);
        tv_home.setTextColor(0xff848484);
        tv_contacts.setTextColor(0xff848484);
        tv_sportsEvent.setTextColor(0xff848484);
        tv_myPage.setTextColor(0xff848484);
    }

    @Override
    public void switch2Home() {
        iv_home.setImageResource(R.drawable.home_up);
        tv_home.setTextColor(0xff6ee4bc);
        initFragment(0);
    }

    @Override
    public void switch2Contacts() {
        iv_contacts.setImageResource(R.drawable.contact_up);
        tv_contacts.setTextColor(0xff6ee4bc);
        initFragment(1);
    }

    @Override
    public void switch2SportsEvent() {
        iv_sportsEvent.setImageResource(R.drawable.share_up);
        tv_sportsEvent.setTextColor(0xff6ee4bc);
        initFragment(2);
    }

    @Override
    public void switch2MyPage() {
        iv_myPage.setImageResource(R.drawable.mirror_up);
        tv_myPage.setTextColor(0xff6ee4bc);
        initFragment(3);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //显示小红点
        checkRedPoint();
        //进入应用后，通知栏应取消
        BmobNotificationManager.getInstance(this).cancelNotification();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清理导致内存泄露的资源
        BmobIM.getInstance().clear();
    }

    /**注册消息接收事件
     * @param event
     */
    @Subscribe
    public void onEventMainThread(MessageEvent event){
        checkRedPoint();
    }

    /**注册离线消息接收事件
     * @param event
     */
    @Subscribe
    public void onEventMainThread(OfflineMessageEvent event){
        checkRedPoint();
    }

    /**注册自定义消息接收事件
     * @param event
     */
    @Subscribe
    public void onEventMainThread(RefreshEvent event){
        log("---主页接收到自定义消息---");
        checkRedPoint();
    }

    private void checkRedPoint(){
        //是否有好友添加的请求
        if(NewFriendManager.getInstance(this).hasNewFriendInvitation()){
            iv_contact_tips.setVisibility(View.VISIBLE);
        }else{
            iv_contact_tips.setVisibility(View.GONE);
        }
    }

}
