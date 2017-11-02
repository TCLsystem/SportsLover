package com.example.user.sportslover.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.sportslover.R;
import com.example.user.sportslover.adapter.DynamicPhotoChooseAdapter;
import com.example.user.sportslover.bean.DynamicItem;
import com.example.user.sportslover.bean.DynamicPhotoItem;
import com.example.user.sportslover.bean.User;
import com.example.user.sportslover.bean.UserLocal;
import com.example.user.sportslover.model.DynamicModelImpr;
import com.example.user.sportslover.model.SportModelInter;
import com.example.user.sportslover.model.UserModelImpl;
import com.example.user.sportslover.widget.DialogBuilder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class SendDynamicActivity2  extends AppCompatActivity {
    private final int REQUEST_CODE = 0x01;
    @Bind(R.id.cancel)
    ImageView cancel;
    @Bind(R.id.send)
    TextView send;
    @Bind(R.id.edit_content)
    EditText editContent;
    @Bind(R.id.gridView)
    GridView gridView;
    @Bind(R.id.edit_activity_name)
    EditText editActivityName;

    private DynamicPhotoChooseAdapter mDynamicPhotoChooseAdapter;
    private final String LOGINUSER = "loginuser";
    // private User mUser;
    DynamicItem dynamicItem;
    private Dialog mLoadingDialog;
    private Dialog mLoadingFinishDialog;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    private List<User> list = new ArrayList<>();
    UserLocal mUserLocal;
    UserModelImpl mUserModelImpl = new UserModelImpl();
    User userlocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.publish_second);
        mUserLocal = mUserModelImpl.getUserLocal();
        userlocal = UserModelImpl.localUsertoUser(mUserLocal);

        ButterKnife.bind(this);
        dynamicItem = (DynamicItem) getIntent().getSerializableExtra("DynamicItem");
        //   mUser = dynamicItem.getWriter();
        Log.d("AAAAAA", dynamicItem.getSportsType() + "   " + dynamicItem.getStartTime() + "    " + dynamicItem.getArea());
        ButterKnife.bind(this);
        init();

    }

    public void init() {
        final PhotoPickerIntent intent = new PhotoPickerIntent(SendDynamicActivity2.this);
        intent.setPhotoCount(6);
        intent.setShowCamera(true);
        mDynamicPhotoChooseAdapter = new DynamicPhotoChooseAdapter(SendDynamicActivity2.this);
        gridView.setAdapter(mDynamicPhotoChooseAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mDynamicPhotoChooseAdapter.getCount() - 1) {
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });
        mLoadingDialog = DialogBuilder.createLoadingDialog(SendDynamicActivity2.this, "正在上传");
        mLoadingFinishDialog = DialogBuilder.createLoadingfinishDialog(SendDynamicActivity2.this, "上传完成");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 选择结果回调
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            List<DynamicPhotoItem> list = new ArrayList<>();
            if (pathList.size() != 0) {
                for (String path : pathList
                        ) {
                    list.add(new DynamicPhotoItem(path, false));
                }
            }
            mDynamicPhotoChooseAdapter.addData(list);
        }
    }

    @OnClick({R.id.cancel, R.id.send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.send:
                mLoadingDialog.show();
                List<BmobFile> fileList = new ArrayList<>();
                ArrayList<DynamicPhotoItem> photoItems = (ArrayList<DynamicPhotoItem>) mDynamicPhotoChooseAdapter.getData();
                for (int i = 0; i < photoItems.size() - 1; i++) {
                    fileList.add(new BmobFile(new File(photoItems.get(i).getFilePath())));
                }
                list.add(userlocal);
                dynamicItem.setTitle(editActivityName.getText().toString());
                dynamicItem.setDetail(editContent.getText().toString());
                dynamicItem.setParticipantName(list);
                dynamicItem.setPhotoList(fileList);
                new DynamicModelImpr().sendDynamicItem(dynamicItem, new SportModelInter.BaseListener() {
                    @Override
                    public void getSuccess(Object o) {
                        mLoadingDialog.dismiss();
                        mLoadingFinishDialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mLoadingFinishDialog.dismiss();
                                finish();
                            }
                        }, 500);
                    }
                    @Override
                    public void getFailure() {

                    }
                });

                break;
        }
    }
}

