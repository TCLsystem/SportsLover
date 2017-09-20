package com.example.user.sportslover.model.Impl;

/**
 * Created by user on 17-9-16.
 */

public interface SportModelImpl {
    /**
     * 获取美食的大概信息
     * @param sortby
     * @param type
     * @param page
     * @param listener
     */
    void getGeneralFoodsItem(String sortby,int type,int page,BaseListener listener);

    /**
     * 获取美食的详细做法
     * @param foodlink
     * @param listener
     */
    void getFoodDetailTeachItem(String foodlink,BaseListener listener);


    /**
     * 获取滚动展示的数据集合
     * @param listener
     */
    void  getSliderShowFood(BaseListener listener);

    interface BaseListener<T>{
        void getSuccess(T t);
        void getFailure();
    }
}
