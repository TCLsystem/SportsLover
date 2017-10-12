package com.example.user.sportslover.model;

import android.graphics.BitmapFactory;

import com.baidu.mapapi.model.LatLng;
import com.example.user.sportslover.bean.RouteItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 17-10-11.
 */

public class RouteFindImpr implements RouteFindInter {
    @Override
    public RouteItem findRouteById(int id) {
        RouteItem routeItem = new RouteItem();
        List<LatLng> points = new ArrayList<>();
        switch (id){
            case 0:
                points.add(new LatLng(0,0));
                points.add(new LatLng(1,1));
                points.add(new LatLng(2,2));
                points.add(new LatLng(3,3));
                points.add(new LatLng(4,4));
                routeItem.setId(0);
                routeItem.setSportsPath(points);
                routeItem.setBitmap(BitmapFactory.decodeFile("/data/data/com.example.user.sportslover/files/test.png"));
                routeItem.setDistance(20000);
                routeItem.setPlace("Huizhou");
                break;
            case 1:
                points.add(new LatLng(100,30));
                points.add(new LatLng(1,1));
                points.add(new LatLng(2,2));
                points.add(new LatLng(3,3));
                points.add(new LatLng(4,4));
                routeItem.setId(1);
                routeItem.setSportsPath(points);
                routeItem.setBitmap(BitmapFactory.decodeFile("/data/data/com.example.user.sportslover/files/test.png"));
                routeItem.setDistance(5000);
                routeItem.setPlace("Huizhou");
                break;
            default:
                points.add(new LatLng(70,60));
                points.add(new LatLng(1,1));
                points.add(new LatLng(2,2));
                points.add(new LatLng(3,3));
                points.add(new LatLng(4,4));
                routeItem.setId(2);
                routeItem.setSportsPath(points);
                routeItem.setBitmap(BitmapFactory.decodeFile("/data/data/com.example.user.sportslover/files/test.png"));
                routeItem.setDistance(60000);
                routeItem.setPlace("Huizhou");
                break;
        }
        return routeItem;
    }
}
