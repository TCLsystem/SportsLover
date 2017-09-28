package com.example.user.sportslover.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.sportslover.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class personalDataActivity extends AppCompatActivity {
    @Bind(R.id.iv_Changephoto)
    ImageView UserPhoto;
    @Bind(R.id.tv_name)
    TextView name;
    @Bind(R.id.tv_gender)
    TextView gender;
    @Bind(R.id.tv_height)
    TextView height;
    @Bind(R.id.tv_weight)
    TextView weight;
    @Bind(R.id.tv_birthday)
    TextView birthday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_Changephoto, R.id.tv_name, R.id.tv_gender,R.id.tv_height, R.id.tv_weight,R.id.tv_birthday})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_Changephoto:

                break;
            case R.id.tv_name:

                break;
            case R.id.tv_gender:

                break;
            case R.id.tv_height:

                break;
            case R.id.tv_weight:

                break;
            //运动记录
            case R.id.tv_birthday:


                break;
            //我运动过的路线
            case R.id.MyRecordRoute:

                break;

        }
    }



}
