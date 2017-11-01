package com.example.user.sportslover.model;

/**
 * Created by user on 17-9-19.
 */
public interface DynamicModelInter {
    void getDynamicItem(SportModelInter.BaseListener listener);
    void getDynamicItemByPhone(String username, SportModelInter.BaseListener listener);
}
