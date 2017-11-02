package com.example.user.sportslover.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.sportslover.R;
import com.example.user.sportslover.bean.DynamicItem;
import com.example.user.sportslover.bean.User;
import com.example.user.sportslover.bean.UserLocal;
import com.example.user.sportslover.model.DynamicModelImpr;
import com.example.user.sportslover.model.SportModelInter;
import com.example.user.sportslover.model.UserModelImpl;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DynamicDetailActivity extends AppCompatActivity {
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    @Bind(R.id.detail_back)
    ImageView detail_back;
    @Bind(R.id.write_photo2)
    ImageView write_photo ;
    @Bind(R.id.write_name2)
    TextView write_name ;
    @Bind(R.id.write_date2)
    TextView write_date;
    @Bind(R.id.dynamic_photo2)
    ImageView dynamic_photo;
    @Bind(R.id.show_activity_Name2)
    TextView show_dynamic_Name;
    @Bind(R.id.show_Type2)
    TextView show_dynamic_type;
    @Bind(R.id.dynamic_text2)
    TextView dynamic_text ;
    @Bind(R.id.show_startTime2)
    TextView show_startTime ;
    @Bind(R.id.show_endTime2)
    TextView show_endTime;
    @Bind(R.id.show_meil2)
    TextView show_mile ;
    @Bind(R.id.show_location2)
    TextView show_location;
    @Bind(R.id.show_participantCount2)
    TextView show_participantCount;
    //    @Bind(R.id.show_participant2)
//    TextView show_participant;
    @Bind(R.id.btn_enterfor)
    Button enter;
    UserLocal mUserLocal;
    UserModelImpl mUserModelImpl = new UserModelImpl();
    User userlocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_dynamic_detail);
        ButterKnife.bind(this);
        mUserLocal = mUserModelImpl.getUserLocal();
        userlocal = UserModelImpl.localUsertoUser(mUserLocal);
        imageLoader.init(ImageLoaderConfiguration.createDefault(DynamicDetailActivity.this));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.xiaolian)
                .showImageForEmptyUri(R.drawable.xiaolian)
                .showImageOnFail(R.drawable.xiaolian).cacheInMemory()
                .cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
                .displayer(new FadeInBitmapDisplayer(300)).build();
        final DynamicItem dynamicItem = (DynamicItem) getIntent().getSerializableExtra("DYNAMIC");
        Log.d("DETAILS",dynamicItem.Detail + " "+ dynamicItem.getTitle());

        new UserModelImpl().getUser(dynamicItem.getWriter().getObjectId(), new SportModelInter.BaseListener() {
            @Override
            public void getSuccess(Object o) {
                final User user = (User) o;
                imageLoader.displayImage(user.getPhoto().getUrl(), write_photo, options);
                write_name.setText(user.getUsername());
                write_date.setText(dynamicItem.getCreatedAt());
                imageLoader.displayImage(dynamicItem.getPhotoList().get(0).getUrl(), dynamic_photo, options);
                dynamic_text.setText(dynamicItem.getDetail());
                show_dynamic_Name.setText(dynamicItem.getTitle());
                show_dynamic_type.setText(dynamicItem.getSportsType());
                show_startTime.setText(getTime(dynamicItem.getStartTime()));
                show_endTime.setText(getTime(dynamicItem.getEndTime()));
                show_mile.setText(dynamicItem.getMeil());
                show_location.setText(dynamicItem.getArea());
                show_participantCount.setText(Integer.toString(dynamicItem.getParticipantName().size()));
                Log.d("DETAILS",write_date.getText().toString()+ "  "+ show_mile.getText().toString());

                if(user.getUsername() != mUserLocal.getName()){
                    enter.setVisibility(View.VISIBLE);
                    enter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new DynamicModelImpr().updateDynamicItem(userlocal,dynamicItem);
                        }
                    });
                }
            }

            @Override
            public void getFailure() {

            }
        });
        //        holder.show_participantCount.setText(dynamicItem.getParticipantName().size());
        //      String name = dynamicItem.getUserName();
        //      int nameCount = dynamicItem.getParticipantName().size();
        //      for(int i= 0;i<nameCount;i++) {
        //          holder.show_participant.setText(name + " ," + dynamicItem.getParticipantName().get(i));
        //         name = name + name + " ," + dynamicItem.getParticipantName().get(i);
        //     }
    }

    @OnClick({R.id.detail_back})
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.detail_back:
                finish();
                break;
            default:
                break;

        }

    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("YYYY.MM.dd  HH:SS");
        return format.format(date);
    }
}






