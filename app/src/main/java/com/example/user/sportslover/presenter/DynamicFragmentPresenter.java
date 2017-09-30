package com.example.user.sportslover.presenter;

import com.example.user.sportslover.bean.DynamicItem;
import com.example.user.sportslover.model.DynamicModelImpr;
import com.example.user.sportslover.model.SportModelInter;
import com.example.user.sportslover.fragment.IDynamicFragment;

import java.util.List;

/**
 * Created by user on 17-9-19.
 */
public class DynamicFragmentPresenter {
    private DynamicModelImpr mDynamicModelImpr = new DynamicModelImpr();
    private IDynamicFragment mView;

    public DynamicFragmentPresenter(IDynamicFragment mView) {
        this.mView = mView;
    }

    public void onRefresh(){
        mDynamicModelImpr.getDynamicItem(new SportModelInter.BaseListener() {
            @Override
            public void getSuccess(Object o) {
                List<DynamicItem> list= (List<DynamicItem>) o;
                mView.onRefresh(list);
            }

            @Override
            public void getFailure() {
            }
        });
    }

    public void onLoadMore(){

    }

}

