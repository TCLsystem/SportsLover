package com.example.user.sportslover.model;

import com.example.user.sportslover.bean.User;

/**
 * Created by user on 17-9-19.
 */
public interface DynamicModelInter {
    void getDynamicItem(SportModelInter.BaseListener listener);
    void getDynamicItemByPhone(User user, SportModelInter.BaseListener listener);
}
