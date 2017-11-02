package com.example.user.sportslover.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.example.user.sportslover.R;

import butterknife.Bind;
import butterknife.OnClick;

public class PrivacyActivity extends AppCompatActivity {
    @Bind(R.id.privacy_back)
    ImageView back;
    @Bind(R.id.mTogBtn2)
    ToggleButton mTogBtn2;
    @Bind(R.id.mTogBtn3)
    ToggleButton mTogBtn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_privacy);
    }
    @OnClick({R.id.privacy_back,R.id.mTogBtn2,R.id.mTogBtn3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.privacy_back:
                finish();
                break;

            case R.id.mTogBtn2:

                break;

            case R.id.mTogBtn3://隐私设置

                break;
            default:
                break;
        }
    }
}
