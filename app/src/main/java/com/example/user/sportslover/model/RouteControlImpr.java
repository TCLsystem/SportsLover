package com.example.user.sportslover.model;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.example.user.sportslover.bean.RouteItem;
import com.example.user.sportslover.bean.TotalRecord;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by user on 17-10-11.
 */

public class RouteControlImpr implements RouteControlInter {
    @Override
    public RouteItem findRouteById(int id) {
        RouteItem routeItem = new RouteItem();
        List<LatLng> points = new ArrayList<>();
        switch (id){
            case 0:
                points.add(new LatLng(23.035529,114.357632));
                points.add(new LatLng(23.135529,114.357632));
                points.add(new LatLng(23.235529,114.457632));
                points.add(new LatLng(23.335529,114.357632));
                points.add(new LatLng(23.035529,114.357632));
                routeItem.setId(0);
                routeItem.setSportsPath(points);
                routeItem.setBitmap(BitmapFactory.decodeFile("/data/data/com.example.user.sportslover/files/test0.png"));
                routeItem.setDistance(103174);
                routeItem.setPlace("Huizhou");
                break;
            case 1:
                points.add(new LatLng(23.455529,114.357632));
                points.add(new LatLng(23.135529,114.857632));
                points.add(new LatLng(23.535529,114.567632));
                points.add(new LatLng(23.335529,114.257632));
                points.add(new LatLng(23.455529,114.385632));
                routeItem.setId(1);
                routeItem.setSportsPath(points);
                routeItem.setBitmap(BitmapFactory.decodeFile("/data/data/com.example.user.sportslover/files/test1.png"));
                routeItem.setDistance(239017);
                routeItem.setPlace("Huizhou");
                break;
            default:
                points.add(new LatLng(23.035529,114.237632));
                points.add(new LatLng(23.135529,114.457632));
                points.add(new LatLng(23.455529,114.457632));
                points.add(new LatLng(23.345529,114.457632));
                points.add(new LatLng(23.565529,114.567632));
                routeItem.setId(2);
                routeItem.setSportsPath(points);
                routeItem.setBitmap(BitmapFactory.decodeFile("/data/data/com.example.user.sportslover/files/test2.png"));
                routeItem.setDistance(137917.67222);
                routeItem.setPlace("Huizhou");
                break;
        }
        return routeItem;
    }

    @Override
    public void uploadRoute(final Context context, final RouteItem routeItem) {
        final BmobFile bmobFile = new BmobFile(new File(routeItem.getFilePath()));
        bmobFile.upload(context, new UploadFileListener() {
            @Override
            public void onSuccess() {
                routeItem.setPic(bmobFile);
                routeItem.save(context, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(context, "Successfully collect route!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(context, "Fail to collect route!\n" +s, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context, "Fail to collect route!\n" +s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void findRouteByUsername(final Context context, String username, final OnRouteFindListener listener) {
        BmobQuery<RouteItem> query = new BmobQuery<>();
        if (!"".equals(username)){
            query.addWhereEqualTo("UserName", username);
        }
        query.findObjects(context, new FindListener<RouteItem>() {
            @Override
            public void onSuccess(List<RouteItem> list) {
                listener.onSuccess(list);
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(context, "Database Error.\n" + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void findRouteById(final Context context, String id, final OnRouteFindListener listener) {
        BmobQuery<RouteItem> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", id);
        query.findObjects(context, new FindListener<RouteItem>() {
            @Override
            public void onSuccess(List<RouteItem> list) {
                listener.onSuccess(list);
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(context, "Database Error.\n" + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface OnRouteFindListener{
        void onSuccess(List<RouteItem> routeItemList);
    }
}
