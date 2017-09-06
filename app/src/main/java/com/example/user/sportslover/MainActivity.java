package com.example.user.sportslover;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.sportslover.view.ContactsFragment;
import com.example.user.sportslover.view.HomeFragment;
import com.example.user.sportslover.view.MainView;
import com.example.user.sportslover.view.MyPageFragment;
import com.example.user.sportslover.view.SportsEventFragment;

public class MainActivity extends AppCompatActivity implements MainView,View.OnClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
        initFragment(0);

    }

    private void initEvent() {
        ll_home.setOnClickListener(this);
        ll_contacts.setOnClickListener(this);
        ll_sportsEvent.setOnClickListener(this);
        ll_myPage.setOnClickListener(this);

    }

    private void initView() {

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
                    contactsFragment = new ContactsFragment();
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
        iv_home.setImageResource(R.drawable.ic_launcher_down);
        iv_contacts.setImageResource(R.drawable.ic_launcher_down);
        iv_sportsEvent.setImageResource(R.drawable.ic_launcher_down);
        iv_myPage.setImageResource(R.drawable.ic_launcher_down);
        tv_home.setTextColor(0xffffffff);
        tv_contacts.setTextColor(0xffffffff);
        tv_sportsEvent.setTextColor(0xffffffff);
        tv_myPage.setTextColor(0xffffffff);
    }

    @Override
    public void switch2Home() {
        iv_home.setImageResource(R.drawable.ic_launcher);
        tv_home.setTextColor(0xff1B940A);
        initFragment(0);
    }

    @Override
    public void switch2Contacts() {
        iv_contacts.setImageResource(R.drawable.ic_launcher);
        tv_contacts.setTextColor(0xff1B940A);
        initFragment(1);
    }

    @Override
    public void switch2SportsEvent() {
        iv_sportsEvent.setImageResource(R.drawable.ic_launcher);
        tv_sportsEvent.setTextColor(0xff1B940A);
        initFragment(2);
    }

    @Override
    public void switch2MyPage() {
        iv_myPage.setImageResource(R.drawable.ic_launcher);
        tv_myPage.setTextColor(0xff1B940A);
        initFragment(3);
    }
}
