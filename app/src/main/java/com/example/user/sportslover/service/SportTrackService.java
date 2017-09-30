package com.example.user.sportslover.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.baidu.trace.api.entity.AddEntityResponse;
import com.baidu.trace.api.entity.LocRequest;
import com.baidu.trace.api.entity.OnEntityListener;
import com.baidu.trace.api.track.DistanceRequest;
import com.baidu.trace.api.track.DistanceResponse;
import com.baidu.trace.api.track.HistoryTrackRequest;
import com.baidu.trace.api.track.HistoryTrackResponse;
import com.baidu.trace.api.track.LatestPoint;
import com.baidu.trace.api.track.LatestPointRequest;
import com.baidu.trace.api.track.LatestPointResponse;
import com.baidu.trace.api.track.OnTrackListener;
import com.baidu.trace.api.track.SupplementMode;
import com.baidu.trace.api.track.TrackPoint;
import com.baidu.trace.model.BaseRequest;
import com.baidu.trace.model.CoordType;
import com.baidu.trace.model.LocationMode;
import com.baidu.trace.model.OnTraceListener;
import com.baidu.trace.model.ProcessOption;
import com.baidu.trace.model.PushMessage;
import com.baidu.trace.model.SortType;
import com.baidu.trace.model.TraceLocation;
import com.baidu.trace.model.TransportMode;
import com.example.user.sportslover.util.MapUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class SportTrackService extends Service {

    private long serviceId = 150504;
    private String entityName = "17895462181357";
    private int gatherInterval = 1000;
    private int packInterval = 1000;
    private long startTime;
    private long endTime;
    private Timer timer = new Timer();
    private TimerTask task;
    private boolean timerValidFlag = false;
    LBSTraceClient mClient;
    Trace mTrace;
    private AtomicInteger mSequenceGenerator = new AtomicInteger();
    private OnEntityListener entityListener = new OnEntityListener() {
        @Override
        public void onReceiveLocation(TraceLocation traceLocation) {
            super.onReceiveLocation(traceLocation);
        }

        @Override
        public void onAddEntityCallback(AddEntityResponse addEntityResponse) {
            super.onAddEntityCallback(addEntityResponse);
            Log.d("TAG", addEntityResponse.toString());
        }
    };
    private OnTrackListener trackListener = new OnTrackListener() {
        @Override
        public void onLatestPointCallback(LatestPointResponse response) {
            LatLng currentLatLng;
            if (response.status == 0){
                LatestPoint point = response.getLatestPoint();
                currentLatLng = MapUtil.convertTrace2Map(point.getLocation());
                if (timerValidFlag) {
                    if (points.size() < 2){
                        points.add(currentLatLng);
                    }
                    else {
                        double tmpDistance = MapUtil.gps2m(currentPoint.latitude, currentPoint.longitude,
                                currentLatLng.latitude, currentLatLng.longitude);
                        distance += tmpDistance;
                        double tmpDistance2 = MapUtil.gps2m(points.get(points.size() - 1).latitude, points.get(points.size() - 1).longitude,
                                currentLatLng.latitude, currentLatLng.longitude);
                        if (tmpDistance2 > 5){
                            points.add(currentLatLng);
                        }
                    }
                }
                currentPoint = currentLatLng;
            }
        }

        @Override
        public void onHistoryTrackCallback(HistoryTrackResponse historyTrackResponse) {
            List<TrackPoint> trackPoints = historyTrackResponse.getTrackPoints();
            for (TrackPoint trackPoint : trackPoints) {
                points.add(MapUtil.convertTrace2Map(trackPoint.getLocation()));
            }
        }

        @Override
        public void onDistanceCallback(DistanceResponse distanceResponse) {
            distance = distanceResponse.getDistance();
        }
    };
    private OnTraceListener traceListener = new OnTraceListener() {
        @Override
        public void onBindServiceCallback(int i, String s) {

        }

        @Override
        public void onStartTraceCallback(int i, String s) {

        }

        @Override
        public void onStopTraceCallback(int i, String s) {

        }

        @Override
        public void onStartGatherCallback(int i, String s) {

        }

        @Override
        public void onStopGatherCallback(int i, String s) {

        }

        @Override
        public void onPushCallback(byte b, PushMessage pushMessage) {

        }
    };
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            // 调用相机回调接口由于MainActivity已经实现了回调接口，所以MainActivity.this即可

            if (msg.what == 1) {
                LatestPointRequest request = new LatestPointRequest(getTag(), serviceId, entityName);
                ProcessOption processOptionLoc = new ProcessOption();//纠偏选项
                processOptionLoc.setRadiusThreshold(50);//设置精度过滤，0为不需要；精度大于50米的位置点过滤掉
                processOptionLoc.setTransportMode(TransportMode.walking);
                processOptionLoc.setNeedDenoise(true);//去噪处理
                processOptionLoc.setNeedMapMatch(false);//绑路处理
                request.setProcessOption(processOptionLoc);//设置参数
                mClient.queryLatestPoint(request, trackListener);//请求纠偏后的最新点
            }
        }

    };

    private long seconds = 0;
    private double distance = 0;
    private List<LatLng> points = new ArrayList<LatLng>();
    private LatLng currentPoint = null;
    private SportTrackServiceControlBinder mBinder = new SportTrackServiceControlBinder();

    public SportTrackService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("", "Service Begin");
        mTrace = new Trace(serviceId, entityName, true);
        mClient = new LBSTraceClient(getApplicationContext());
        mClient.setLocationMode(LocationMode.High_Accuracy);
        LocRequest locRequest = new LocRequest(serviceId);
        mClient.queryRealTimeLoc(locRequest, entityListener);
        mClient.setInterval(gatherInterval, packInterval);
        mClient.startTrace(mTrace, traceListener);
        mClient.startGather(traceListener);
        startTime = System.currentTimeMillis()/1000;
        startTimer();
        /*HistoryTrackRequest historyTrackRequest = new HistoryTrackRequest();
        ProcessOption processOptionHis = new ProcessOption();//纠偏选项
        processOptionHis.setRadiusThreshold(50);//精度过滤
        processOptionHis.setTransportMode(TransportMode.walking);//交通方式，默认为驾车
        processOptionHis.setNeedDenoise(true);//去噪处理，默认为false，不处理
        processOptionHis.setNeedVacuate(true);//设置抽稀，仅在查询历史轨迹时有效，默认需要false
        processOptionHis.setNeedMapMatch(true);//绑路处理，将点移到路径上，默认不需要false
        historyTrackRequest.setProcessOption(processOptionHis);
        historyTrackRequest.setSupplementMode(SupplementMode.no_supplement);
        historyTrackRequest.setSortType(SortType.asc);//设置返回结果的排序规则，默认升序排序；升序：集合中index=0代表起始点；降序：结合中index=0代表终点。
        historyTrackRequest.setCoordTypeOutput(CoordType.bd09ll);//设置返回结果的坐标类型，默认为百度经纬度
        historyTrackRequest.setProcessed(true);
        historyTrackRequest.setTag(getTag());//设置请求标识，用于唯一标记本次请求，在响应结果中会返回该标识
        historyTrackRequest.setServiceId(serviceId);//设置轨迹服务id，Trace中的id
        historyTrackRequest.setEntityName(entityName);//Trace中的entityName
        historyTrackRequest.setStartTime(startTime);
        historyTrackRequest.setEndTime(endTime);
        mClient.queryHistoryTrack(historyTrackRequest, trackListener);
        DistanceRequest distanceRequest = new DistanceRequest(tag, serviceId, entityName);
        distanceRequest.setStartTime(startTime);// 设置开始时间
        distanceRequest.setEndTime(endTime);// 设置结束时间
        distanceRequest.setProcessed(true);// 纠偏
        ProcessOption processOptionDis = new ProcessOption();// 创建纠偏选项实例
        processOptionDis.setNeedDenoise(true);// 去噪
        processOptionDis.setNeedMapMatch(true);// 绑路
        processOptionDis.setTransportMode(TransportMode.walking);// 交通方式为步行
        distanceRequest.setProcessOption(processOptionDis);// 设置纠偏选项
        distanceRequest.setSupplementMode(SupplementMode.no_supplement);// 里程填充方式为无
        mClient.queryDistance(distanceRequest, trackListener);// 查询里程*/
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("", "get Binder " + mBinder);
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pauseTimer();
        mClient.stopGather(traceListener);
        endTime = System.currentTimeMillis()/1000;
        mClient.stopTrace(mTrace, traceListener);
    }

    public int getTag() {
        return mSequenceGenerator.incrementAndGet();
    }


    public void startTimer() {
        if (timer == null) {
            timer = new Timer();
        }

        if (task == null) {
            task = new TimerTask() {
                @Override
                public void run() {
                    if (timerValidFlag)
                        seconds++;
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            };
        }

        if (timer != null && task != null) {
            try {
                //task.cancel();
                timer.schedule(task, 1000, 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void pauseTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    public class SportTrackServiceControlBinder extends Binder{
        public void startService(){
            timerValidFlag = true;
        }

        public void pauseService(){
            timerValidFlag = false;
        }

        public long getSeconds(){
            return seconds;
        }

        public double getDistance(){
            return distance;
        }

        public List<LatLng> getPoints(){
            return points;
        }

        public LatLng getCurrentPoint() { return currentPoint; }
    }
}
