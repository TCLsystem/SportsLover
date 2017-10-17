package com.example.user.sportslover.model;

import android.content.Context;

import com.example.user.sportslover.bean.RecordItem;
import com.example.user.sportslover.bean.SportHistoryDataBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 17-10-10.
 */

public class SportHistoryModelImpr implements SportHistoryModelInter {

    RecordItemControlInter recordItemControlInter = new RecordItemControlImpr();
    final static long ONEDAYMILIS = 24*60*60*1000;
    final static long EIGHTZONE = 8* 60 * 60 *1000;
    final static String MONTH[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"};

    @Override
    public void loadHistoryData(Context context, final int days, String type, final OnSportHistoryListener listener) {
        final Date date = new Date();
        final long now = date.getTime() + EIGHTZONE;
        recordItemControlInter.findRecordItemByTime(context, type, now - (days - 1) * ONEDAYMILIS - days % ONEDAYMILIS, now, new RecordItemControlImpr.OnRecordItemListener() {
            @Override
            public void onSuccess(List<RecordItem> recordItemList) {
                List<SportHistoryDataBean> sportHistoryDataBeanList = new ArrayList<>();
                for (int i = 0; i < days; i++)
                    sportHistoryDataBeanList.add(new SportHistoryDataBean("", "", 0, 0, 0));
                for (int i = 0; i < days; i++){
                    Date date1 = new Date(now - i * ONEDAYMILIS);
                    SportHistoryDataBean sportHistoryDataBean = new SportHistoryDataBean(MONTH[date1.getMonth()], Integer.toString(date1.getDate()), 0, 0, 0);
                    if (recordItemList.size() > 0){
                        for (int j = 0; j < recordItemList.size(); j++){
                            if (recordItemList.get(j).getSportsTime() > (i==0?(now - days % ONEDAYMILIS):(now - days % ONEDAYMILIS - i * ONEDAYMILIS))){
                                sportHistoryDataBean.setCalories(sportHistoryDataBean.getCalories() + recordItemList.get(j).getCalories());
                                sportHistoryDataBean.setCumulativeTime(sportHistoryDataBean.getCumulativeTime() + recordItemList.get(j).getDuration());
                                sportHistoryDataBean.setDistance(sportHistoryDataBean.getDistance() + recordItemList.get(j).getDistance());
                                recordItemList.remove(j);
                            }
                        }
                    }
                    sportHistoryDataBeanList.set(days - i - 1, sportHistoryDataBean);
                }
                listener.onSuccess(sportHistoryDataBeanList);
                sportHistoryDataBeanList.clear();
            }
        });
    }

    public interface OnSportHistoryListener{
        void onSuccess(List<SportHistoryDataBean> sportHistoryDataBeanList);
    }

    /*@Override
    public List<SportHistoryDataBean> loadHistoryData(int seconds, String type) {
        List<SportHistoryDataBean> sportHistoryDataBeanList1 = new ArrayList<>();
        init(type);
        for (int i = 0; i < seconds; i++){
            if (i < 30)
                sportHistoryDataBeanList1.add(sportHistoryDataBeanList.get(i));
        }
        return sportHistoryDataBeanList1;
    }

    private void init(String type){
        if ("running".equals(type)) {
            for (int i = 0; i < 5; i++) {
                SportHistoryDataBean sportHistoryDataBean1 = new SportHistoryDataBean("Aug", "1", 1, 1, 1);
                sportHistoryDataBeanList.add(sportHistoryDataBean1);
                SportHistoryDataBean sportHistoryDataBean2 = new SportHistoryDataBean("Aug", "2", 2, 2, 2);
                sportHistoryDataBeanList.add(sportHistoryDataBean2);
                SportHistoryDataBean sportHistoryDataBean3 = new SportHistoryDataBean("Aug", "3", 3, 3, 3);
                sportHistoryDataBeanList.add(sportHistoryDataBean3);
                SportHistoryDataBean sportHistoryDataBean4 = new SportHistoryDataBean("Aug", "4", 4, 4, 4);
                sportHistoryDataBeanList.add(sportHistoryDataBean4);
                SportHistoryDataBean sportHistoryDataBean5 = new SportHistoryDataBean("Aug", "5", 5, 5, 5);
                sportHistoryDataBeanList.add(sportHistoryDataBean5);
                SportHistoryDataBean sportHistoryDataBean6 = new SportHistoryDataBean("Aug", "6", 6, 6, 6);
                sportHistoryDataBeanList.add(sportHistoryDataBean6);
            }
        }
        else if ("walking".equals(type)) {
            for (int i = 0; i < 5; i++) {
                SportHistoryDataBean sportHistoryDataBean1 = new SportHistoryDataBean("Jun", "1", 1, 1, 1);
                sportHistoryDataBeanList.add(sportHistoryDataBean1);
                SportHistoryDataBean sportHistoryDataBean2 = new SportHistoryDataBean("Jun", "2", 2, 2, 2);
                sportHistoryDataBeanList.add(sportHistoryDataBean2);
                SportHistoryDataBean sportHistoryDataBean3 = new SportHistoryDataBean("Jun", "3", 3, 3, 3);
                sportHistoryDataBeanList.add(sportHistoryDataBean3);
                SportHistoryDataBean sportHistoryDataBean4 = new SportHistoryDataBean("Jun", "4", 4, 4, 4);
                sportHistoryDataBeanList.add(sportHistoryDataBean4);
                SportHistoryDataBean sportHistoryDataBean5 = new SportHistoryDataBean("Jun", "5", 5, 5, 5);
                sportHistoryDataBeanList.add(sportHistoryDataBean5);
                SportHistoryDataBean sportHistoryDataBean6 = new SportHistoryDataBean("Jun", "6", 6, 6, 6);
                sportHistoryDataBeanList.add(sportHistoryDataBean6);
            }
        }
        else if ("riding".equals(type)) {
            for (int i = 0; i < 5; i++) {
                SportHistoryDataBean sportHistoryDataBean1 = new SportHistoryDataBean("Mar", "1", 1, 1, 1);
                sportHistoryDataBeanList.add(sportHistoryDataBean1);
                SportHistoryDataBean sportHistoryDataBean2 = new SportHistoryDataBean("Mar", "2", 2, 2, 2);
                sportHistoryDataBeanList.add(sportHistoryDataBean2);
                SportHistoryDataBean sportHistoryDataBean3 = new SportHistoryDataBean("Mar", "3", 3, 3, 3);
                sportHistoryDataBeanList.add(sportHistoryDataBean3);
                SportHistoryDataBean sportHistoryDataBean4 = new SportHistoryDataBean("Mar", "4", 4, 4, 4);
                sportHistoryDataBeanList.add(sportHistoryDataBean4);
                SportHistoryDataBean sportHistoryDataBean5 = new SportHistoryDataBean("Mar", "5", 5, 5, 5);
                sportHistoryDataBeanList.add(sportHistoryDataBean5);
                SportHistoryDataBean sportHistoryDataBean6 = new SportHistoryDataBean("Mar", "6", 6, 6, 6);
                sportHistoryDataBeanList.add(sportHistoryDataBean6);
            }
        }
        else {
            for (int i = 0; i < 5; i++) {
                SportHistoryDataBean sportHistoryDataBean1 = new SportHistoryDataBean("None", "1", 1, 1, 1);
                sportHistoryDataBeanList.add(sportHistoryDataBean1);
                SportHistoryDataBean sportHistoryDataBean2 = new SportHistoryDataBean("None", "2", 2, 2, 2);
                sportHistoryDataBeanList.add(sportHistoryDataBean2);
                SportHistoryDataBean sportHistoryDataBean3 = new SportHistoryDataBean("None", "3", 3, 3, 3);
                sportHistoryDataBeanList.add(sportHistoryDataBean3);
                SportHistoryDataBean sportHistoryDataBean4 = new SportHistoryDataBean("None", "4", 4, 4, 4);
                sportHistoryDataBeanList.add(sportHistoryDataBean4);
                SportHistoryDataBean sportHistoryDataBean5 = new SportHistoryDataBean("None", "5", 5, 5, 5);
                sportHistoryDataBeanList.add(sportHistoryDataBean5);
                SportHistoryDataBean sportHistoryDataBean6 = new SportHistoryDataBean("None", "6", 6, 6, 6);
                sportHistoryDataBeanList.add(sportHistoryDataBean6);
            }
        }
    }*/
}
