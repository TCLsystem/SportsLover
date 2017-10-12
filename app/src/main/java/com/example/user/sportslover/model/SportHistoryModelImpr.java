package com.example.user.sportslover.model;

import com.example.user.sportslover.bean.SportHistoryDataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 17-10-10.
 */

public class SportHistoryModelImpr implements SportHistoryModelInter {

    List<SportHistoryDataBean> sportHistoryDataBeanList = new ArrayList<>();

    @Override
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
    }
}
