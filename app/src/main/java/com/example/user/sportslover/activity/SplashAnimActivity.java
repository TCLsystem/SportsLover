package com.example.user.sportslover.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import com.example.user.sportslover.R;
import com.example.user.sportslover.bean.UserLocal;
import com.example.user.sportslover.model.UserModelImpl;
import com.example.user.sportslover.util.PrefUtils;

public class SplashAnimActivity extends AppCompatActivity {

    private LinearLayout mLinAnimation;
    UserLocal mUserLocal;
    UserModelImpl mUserModelImpl = new UserModelImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_splash_anim);

        mLinAnimation = (LinearLayout) findViewById(R.id.linAnimation);
        startAnimation();
    }

    private void startAnimation() {

        AlphaAnimation aniAlyha = new AlphaAnimation(0.0f, 1.0f);
        aniAlyha.setDuration(1000);
        aniAlyha.setRepeatCount(1);
        aniAlyha.setFillAfter(true);
        aniAlyha.setRepeatMode(Animation.REVERSE);

        RotateAnimation aniRotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
        aniRotate.setDuration(1000);
        aniRotate.setRepeatCount(1);
        aniRotate.setFillAfter(true);
        aniRotate.setRepeatMode(Animation.REVERSE);

        ScaleAnimation aniScalse = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        aniScalse.setDuration(1000);
        aniScalse.setRepeatCount(1);
        aniScalse.setFillAfter(true);
        aniScalse.setRepeatMode(Animation.REVERSE);

        AnimationSet setAll = new AnimationSet(true);
        setAll.addAnimation(aniAlyha);
        setAll.addAnimation(aniRotate);
        setAll.addAnimation(aniScalse);
        mLinAnimation.startAnimation(setAll);

        setAll.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boolean isGuideShow = PrefUtils.getBoolean("isGuideShow", false, getApplicationContext());
                // if (isGuideShow){
//                    startActivity(new Intent(SplashAnimActivity.this,
//                            MainActivity.class));
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mUserLocal = mUserModelImpl.getUserLocal();
                        if (mUserLocal == null) {
                            startActivity(new Intent(SplashAnimActivity.this,
                                    SplashActivity.class));
                        } else {
                            startActivity(new Intent(SplashAnimActivity.this,
                                    MainActivity.class));
                        }
                    }
                }, 1000);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
