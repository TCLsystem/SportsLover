package com.example.user.sportslover.view;

import android.support.v4.app.FragmentTransaction;

/**
 * Created by user on 17-9-6.
 */

public interface MainView {
    //Initial Fragment
    void initFragment(int index);
    //Hide Fragment
    void hideFragment(FragmentTransaction transaction);
    //Reset the bottom status to unclicked status
    void restartBottom();
    //Switch to home fragment
    void switch2Home();
    //Switch to contacts fragment
    void switch2Contacts();
    //Switch to sports event fragment
    void switch2SportsEvent();
    //Switch to my page fragment
    void switch2MyPage();
}
