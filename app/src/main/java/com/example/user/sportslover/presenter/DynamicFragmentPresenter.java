package com.example.user.sportslover.presenter;

import com.example.user.sportslover.bean.DynamicItem;
import com.example.user.sportslover.model.DynamicModel;
import com.example.user.sportslover.model.Impl.SportModelImpl;
import com.example.user.sportslover.view.IDynamicFragment;

import java.util.List;

/**
 * Created by user on 17-9-19.
 */
public class DynamicFragmentPresenter {
    private DynamicModel mDynamicModel = new DynamicModel();
    private IDynamicFragment mView;

    public DynamicFragmentPresenter(IDynamicFragment mView) {
        this.mView = mView;
    }

    public void onRefresh(){
        mDynamicModel.getDynamicItem(new SportModelImpl.BaseListener() {
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

