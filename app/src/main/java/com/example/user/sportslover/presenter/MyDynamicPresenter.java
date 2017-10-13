package com.example.user.sportslover.presenter;

import com.example.user.sportslover.bean.DynamicItem;
import com.example.user.sportslover.model.DynamicModelImpr;
import com.example.user.sportslover.model.SportModelInter;

import java.util.List;

/**
 * Created by user on 17-10-12.
 */

public class MyDynamicPresenter {
        private DynamicModelImpr mDynamicModelImpr = new DynamicModelImpr();
        private MyDynamic mView;

        public MyDynamicPresenter(MyDynamic mView) {
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


