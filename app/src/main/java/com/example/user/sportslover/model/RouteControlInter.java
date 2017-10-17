package com.example.user.sportslover.model;

import android.content.Context;

import com.example.user.sportslover.bean.RouteItem;

import java.util.List;

/**
 * Created by user on 17-10-11.
 */

public interface RouteControlInter {

    RouteItem findRouteById(int id);

    void uploadRoute(Context context, RouteItem routeItem);

    void findRouteByUsername(Context context, String username, RouteControlImpr.OnRouteFindListener listener);

    void findRouteById(Context context, String id, RouteControlImpr.OnRouteFindListener listener);
}
