package com.example.user.sportslover.model;

/**
 * Created by user on 17-9-16.
 */
public interface UserModelInter {
    void getUser(String name,String passoword,SportModelInter.BaseListener listener);
    void getUser(String objectId, final SportModelInter.BaseListener listener);
}
