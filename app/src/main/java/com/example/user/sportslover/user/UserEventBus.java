package com.example.user.sportslover.user;

/**
 * Created by user on 17-9-16.
 */

public class UserEventBus {
    public UserLocal getmUser() {
        return mUser;
    }

    public void setmUser(UserLocal mUser) {
        this.mUser = mUser;
    }

    private UserLocal mUser;

    public UserEventBus(UserLocal mUser) {
        this.mUser = mUser;
    }
}

