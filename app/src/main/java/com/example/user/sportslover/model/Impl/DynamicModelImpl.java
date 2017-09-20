package com.example.user.sportslover.model.Impl;

import com.example.user.sportslover.dto.User;

/**
 * Created by user on 17-9-19.
 */
public interface DynamicModelImpl {
    void getDynamicItem(SportModelImpl.BaseListener listener);
    void getDynamicItemByPhone(User user, SportModelImpl.BaseListener listener);
}
