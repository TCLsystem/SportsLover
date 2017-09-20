package com.example.user.sportslover.model.Impl;

/**
 * Created by user on 17-9-16.
 */
public interface UserModelImpl {
    void getUser(String name,String passoword,SportModelImpl.BaseListener listener);
    void getUser(String objectId, final SportModelImpl.BaseListener listener);
}
