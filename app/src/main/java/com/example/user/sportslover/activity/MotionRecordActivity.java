package com.example.user.sportslover.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.user.sportslover.R;
import com.example.user.sportslover.application.BaseApplication;
import com.example.user.sportslover.bean.RecordItem;
import com.example.user.sportslover.bean.User;
import com.example.user.sportslover.bean.UserLocal;
import com.example.user.sportslover.model.SportModelInter;
import com.example.user.sportslover.model.UserModelImpl;
import com.example.user.sportslover.util.ToastUtil;
import com.example.user.sportslover.widget.DialogBuilder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class MotionRecordActivity extends AppCompatActivity {
    @Bind(R.id.iv_sport_record)
    ImageView iv_sport_record;


    private final int REQUEST_CODE = 0x01;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    private Dialog mLoadingDialog;
    private Dialog mLoadingFinishDialog;
    private RecordItem recordItem = new RecordItem();

    private UserLocal mUserLocal;
    User user;
    private UserModelImpl mUserModelImpl = new UserModelImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_record);
        ButterKnife.bind(this);
        mUserLocal = mUserModelImpl.getUserLocal();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_empty_dish)
                .showImageForEmptyUri(R.drawable.ic_empty_dish)
                .showImageOnFail(R.drawable.ic_empty_dish).cacheInMemory()
                .cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
                .displayer(new FadeInBitmapDisplayer(300)).build();
        mLoadingDialog = DialogBuilder.createLoadingDialog(this, "正在上传图片");
        mLoadingFinishDialog = DialogBuilder.createLoadingfinishDialog(this, "上传完成");
        mUserModelImpl.getUser(mUserLocal.getObjectId(), new SportModelInter.BaseListener() {
            @Override
            public void getSuccess(Object o) {
              user = (User)  o;
            }

            @Override
            public void getFailure() {

            }
        });
        recordItem.setSportsman(user);

    }
    @OnClick({R.id.iv_sport_record})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_sport_event:
                final PhotoPickerIntent intent = new PhotoPickerIntent(MotionRecordActivity.this);
                intent.setPhotoCount(1);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);

                break;
            default:
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 选择结果回调
        if (requestCode == REQUEST_CODE && data != null) {
            mLoadingDialog.show();
            List<String> pathList = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
         //   mUserModelImpl.updateUserPhoto(pathList.get(0), mUserLocal.getObjectId(), new SportModelInter.BaseListener() {
            final BmobFile bmobFile = new BmobFile(new File(pathList.get(0)));
            bmobFile.upload(BaseApplication.getmContext(), new UploadFileListener() {
                @Override
                public void onSuccess() {
                   recordItem.setSportPicture(bmobFile);
                    recordItem.save(MotionRecordActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            mLoadingDialog.dismiss();
                            mLoadingFinishDialog.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mLoadingFinishDialog.dismiss();
                                }
                            }, 500);
                            ToastUtil.showLong(MotionRecordActivity.this, "成功");
                            imageLoader.displayImage(recordItem.getSportPicture().getUrl(), iv_sport_record, options);
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });

                }

                @Override
                public void onFailure(int i, String s) {
                    ToastUtil.showShort(MotionRecordActivity.this,i+"  "+s);
                }
            });
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
     //   ButterKnife.unbind(this);
    }
}
