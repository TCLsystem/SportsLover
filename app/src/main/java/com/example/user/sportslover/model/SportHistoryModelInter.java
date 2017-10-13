package com.example.user.sportslover.model;

import com.example.user.sportslover.bean.SportHistoryDataBean;

import java.util.List;

/**
 * Created by user on 17-10-10.
 */

public interface SportHistoryModelInter {

    public List<SportHistoryDataBean> loadHistoryData(int seconds, String type);

}
