package com.example.user.sportslover.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.sportslover.R;
import com.example.user.sportslover.activity.RouteActivity;
import com.example.user.sportslover.activity.motionGoalActivity;
import com.example.user.sportslover.activity.motionRecordActivity;
import com.example.user.sportslover.activity.myDynamicActivity;
import com.example.user.sportslover.activity.settingActivity;
import com.example.user.sportslover.dto.User;
import com.example.user.sportslover.model.Impl.SportModelImpl;
import com.example.user.sportslover.model.UserModel;
import com.example.user.sportslover.presenter.UserFragmentPresenter;
import com.example.user.sportslover.user.UserEventBus;
import com.example.user.sportslover.user.UserLocal;
import com.example.user.sportslover.util.ToastUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import me.iwf.photopicker.PhotoPickerActivity;

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

    private UserModel mUserModel;

    private Dialog mLoadingDialog;
    private Dialog mLoadingFinishDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        mUserModel = new UserModel();
//        Log.d("rhLog"," mUserModel.getUserLocal()--"+ mUserModel.getUserLocal());
//        mUserLocal = mUserModel.getUserLocal();
        View v = inflater.inflate(R.layout.fragment_my_page, container, false);
        ButterKnife.bind(this, v);
//        de.greenrobot.event.EventBus.getDefault().register(this);
//        mUserFragmentPresenter = new UserFragmentPresenter();
//        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
//        options = new DisplayImageOptions.Builder()
//                .showStubImage(R.drawable.ic_empty_dish)
//                .showImageForEmptyUri(R.drawable.ic_empty_dish)
//                .showImageOnFail(R.drawable.ic_empty_dish).cacheInMemory()
//                .cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
//                .displayer(new FadeInBitmapDisplayer(300)).build();
//        if (mUserLocal != null) {
//            imageLoader.displayImage(mUserLocal.getPhoto(), UserPhoto, options);
//            loginText.setText(mUserLocal.getName());
//        }
//        mLoadingDialog = DialogBuilder.createLoadingDialog(getActivity(), "正在上传图片");
//        mLoadingFinishDialog = DialogBuilder.createLoadingfinishDialog(getActivity(), "上传完成");
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
//                if (mUserModel.isLogin()) {
//                    final PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
//                    intent.setPhotoCount(1);
//                    intent.setShowCamera(true);
//                    startActivityForResult(intent, REQUEST_CODE);
//                } else {
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                }
                ToastUtil.showShort(getActivity(),"设置个人信息");
                break;
            case R.id.loginText:
             //   mUserFragmentPresenter.onLogin(getActivity());
                break;
            case R.id.sportsClass://运动等级

                break;
            case R.id.personalData://运动等级

                break;
            case R.id.motionGoal://每周目标
                startActivity(new Intent(getActivity(), motionGoalActivity.class));
                break;
            //运动记录
            case R.id.motionRecord:
                startActivity(new Intent(getActivity(), motionRecordActivity.class));


//                if (mUserModel.isLogin()) {
//                    mUserModel.getUser(mUserLocal.getObjectId(), new SportModelImpl.BaseListener() {
//                        @Override
//                        public void getSuccess(Object o) {
//                            mUserFragmentPresenter.onSendDynamic(getActivity(), (User) o);
//                        }
//
//                        @Override
//                        public void getFailure() {
//
//                        }
//                    });
//                } else {
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                }
                break;
            //我运动过的路线
            case R.id.MyRecordRoute:
                startActivity(new Intent(getActivity(), RouteActivity.class));
                break;
            //我的活动
            case R.id.myMotion:
  //              if (mUserModel.isLogin()) {
                    startActivity(new Intent(getActivity(), myDynamicActivity.class));
//                } else {
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                }
                break;
            //设置
            case R.id.setting:
                startActivity(new Intent(getActivity(), settingActivity.class));
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 选择结果回调
        if (requestCode == REQUEST_CODE && data != null) {
            mLoadingDialog.show();
            List<String> pathList = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            mUserModel.updateUserPhoto(pathList.get(0), mUserLocal.getObjectId(), new SportModelImpl.BaseListener() {
                @Override
                public void getSuccess(Object o) {
                    mLoadingDialog.dismiss();
                    mLoadingFinishDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mLoadingFinishDialog.dismiss();
                        }
                    }, 500);
                    ToastUtil.showLong(getActivity(), "头像修改成功");
                    User user = (User) o;
                    imageLoader.displayImage(user.getPhoto().getUrl(), UserPhoto, options);
                    UserLocal userLocal = new UserLocal();
                    userLocal.setName(user.getName());
                    userLocal.setObjectId(user.getObjectId());
                    userLocal.setNumber(user.getNumber());
                    if (user.getPhoto() != null) {
                        userLocal.setPhoto(user.getPhoto().getUrl());
                    }
                    mUserModel.putUserLocal(userLocal);
                }

                @Override
                public void getFailure() {

                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}