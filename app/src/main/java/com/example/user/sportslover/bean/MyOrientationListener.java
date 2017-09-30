package com.example.user.sportslover.bean;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by user on 17-9-20.
 */

public class MyOrientationListener implements SensorEventListener {

    private Context context;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetic;

    private float[] accelerometerValues = new float[3];
    private float[] magneticFieldValues = new float[3];

    //private float lastX ;

    private OnOrientationListener onOrientationListener ;

    public MyOrientationListener(Context context)
    {
        this.context = context;
    }

    // 开始
    public void start()
    {
        // 获得传感器管理器
        sensorManager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null)
        {
            // 获得方向传感器
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        }
        // 注册
        if (accelerometer != null && magnetic != null)
        {//SensorManager.SENSOR_DELAY_UI
            sensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_UI);
            sensorManager.registerListener(this, magnetic,
                    SensorManager.SENSOR_DELAY_UI);
        }

    }

    // 停止检测
    public void stop()
    {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        float[] values = new float[3];
        float[] R = new float[9];

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerValues = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magneticFieldValues = event.values;
        }
        SensorManager.getRotationMatrix(R, null, accelerometerValues,
                magneticFieldValues);
        SensorManager.getOrientation(R, values);
        values[0] = (float) Math.toDegrees(values[0]);
        onOrientationListener.onOrientationChanged(values[0]);
    }

    public void setOnOrientationListener(OnOrientationListener onOrientationListener)
    {
        this.onOrientationListener = onOrientationListener ;
    }


    public interface OnOrientationListener
    {
        void onOrientationChanged(float x);
    }

}
