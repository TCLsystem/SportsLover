package com.example.user.sportslover.view;

import com.example.user.sportslover.dto.DynamicItem;

import java.util.List;

/**
 * Created by user on 17-9-19.
 */
public interface IDynamicFragment {
        //加载更多
        void onLoadMore(List<DynamicItem> list);

        //下拉刷新
        void onRefresh(List<DynamicItem> list);
    }

