package com.example.user.sportslover.dto;

import cn.bmob.v3.BmobUser;

/**
 * Created by user on 17-9-11.
 */

public class User extends BmobUser {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String password1;

    public String getPassword() {
        return password1;
    }

    private String number;
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number= number;
    }


    @Override
    public String toString() {
        return getUsername()+"\n"+getObjectId()+"\n"+"\n"+number+"\n"+getSessionToken()+"\n"+getEmailVerified();
    }
}


