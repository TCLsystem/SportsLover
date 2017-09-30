package com.example.user.sportslover.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.sportslover.R;
import com.example.user.sportslover.activity.PersonalDataActivity;
import com.example.user.sportslover.activity.RouteActivity;
import com.example.user.sportslover.activity.MotionGoalActivity;
import com.example.user.sportslover.activity.MotionRecordActivity;
import com.example.user.sportslover.activity.MyDynamicActivity;
import com.example.user.sportslover.activity.SettingActivity;
import com.example.user.sportslover.model.UserModelImpl;
import com.example.user.sportslover.presenter.UserFragmentPresenter;
import com.example.user.sportslover.activity.LoginActivity;
import com.example.user.sportslover.bean.UserEventBus;
import com.example.user.sportslover.bean.UserLocal;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link MyPageFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the  factory method to
// * create an instance of this fragment.
// */


public class MyPageFragment extends Fragment {
    @Bind(R.id.UserPhoto)
    ImageView UserPhoto;
    @Bind(R.id.loginText)
    TextView loginText;
    @Bind(R.id.sportsClass)
    TextView sportsClass;
    @Bind(R.id.personalData)
    TextView personalData;
    @Bind(R.id.motionRecord)
    TextView motionRecord;
    @Bind(R.id.MyRecordRoute)
    TextView MyRecordRoute;
    @Bind(R.id.myMotion)
    TextView myMotion;
    @Bind(R.id.setting)
    TextView setting;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;

    private final String LOGINUSER = "loginuser";
    private UserLocal mUserLocal;

    private UserFragmentPresenter mUserFragmentPresenter;
    private final int REQUEST_CODE = 0x01;

    private UserModelImpl mUserModelImpl = new UserModelImpl();

    private Dialog mLoadingDialog;
    private Dialog mLoadingFinishDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mUserLocal = mUserModelImpl.getUserLocal();
        View v = inflater.inflate(R.layout.fragment_my_page, container, false);
        ButterKnife.bind(this, v);
        de.greenrobot.event.EventBus.getDefault().register(this);
        mUserFragmentPresenter = new UserFragmentPresenter();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_empty_dish)
                .showImageForEmptyUri(R.drawable.ic_empty_dish)
                .showImageOnFail(R.drawable.ic_empty_dish).cacheInMemory()
                .cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
                .displayer(new FadeInBitmapDisplayer(300)).build();
        if (mUserLocal != null) {
            imageLoader.displayImage(mUserLocal.getPhoto(), UserPhoto, options);
            loginText.setText(mUserLocal.getName());
        }
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.UserPhoto, R.id.loginText, R.id.sportsClass,R.id.personalData, R.id.motionGoal,R.id.motionRecord, R.id.MyRecordRoute, R.id.myMotion, R.id.setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.UserPhoto:
                if (mUserModelImpl.isLogin()) {
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.loginText:

                break;
            case R.id.sportsClass://运动等级

                break;
            case R.id.personalData://设置个人信息
                if (mUserLocal != null) {
                    Intent intent = new Intent(getActivity(), PersonalDataActivity.class);
                    intent.putExtra("name", mUserLocal.getName());
                    startActivity(intent);
                }
                break;
            case R.id.motionGoal://每周目标
                startActivity(new Intent(getActivity(), MotionGoalActivity.class));
                break;
            //运动记录
            case R.id.motionRecord:
                startActivity(new Intent(getActivity(), MotionRecordActivity.class));

                break;
            //我运动过的路线
            case R.id.MyRecordRoute:
                startActivity(new Intent(getActivity(), RouteActivity.class));
                break;
            //我的活动
            case R.id.myMotion:
                if (mUserModelImpl.isLogin()) {
                    startActivity(new Intent(getActivity(), MyDynamicActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            //设置
            case R.id.setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;

        }
    }

    /**
     * Eventbus的处理函数
     *
     * @param event
     */
    public void onEventMainThread(UserEventBus event) {
        mUserLocal = event.getmUser();
        if (mUserLocal != null) {
            if (event.getmUser().getPhoto() != null) {
                imageLoader.displayImage(event.getmUser().getPhoto(), UserPhoto, options);
            }
            loginText.setText(event.getmUser().getName());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}