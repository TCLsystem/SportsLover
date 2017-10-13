package com.example.user.sportslover.presenter;

import com.example.user.sportslover.bean.SportHistoryDataBean;

import java.util.List;

/**
 * Created by user on 17-10-10.
 */

public interface SportHistoryPresenter {
    List<SportHistoryDataBean> loadHistoryData();
}
