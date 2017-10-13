package com.example.user.sportslover.presenter;

import com.example.user.sportslover.bean.DynamicItem;

import java.util.List;

/**
 * Created by user on 17-10-12.
 */

public interface MyDynamic {
    //加载更多
    void onLoadMore(List<DynamicItem> list);

    //下拉刷新
    void onRefresh(List<DynamicItem> list);
}

