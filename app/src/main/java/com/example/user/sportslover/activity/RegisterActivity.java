package com.example.user.sportslover.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.sportslover.R;
import com.example.user.sportslover.bean.User;
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

public class RegisterActivity extends Activity {


    @Bind(R.id.register_back)
    ImageView registerBack;
    @Bind(R.id.register_name)
    EditText registerName;
    @Bind(R.id.register_btn)
    Button registerBtn;
    @Bind(R.id.iv_upload_photo)
    ImageView upload_photo;

    User user = new User();


    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    private final int REQUEST_CODE = 0x01;

    private UserModelImpl mUserModelImpl = new UserModelImpl();

    private Dialog mLoadingDialog;
    private Dialog mLoadingFinishDialog;
    String phone ;
    String password;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        user = (User) getIntent().getSerializableExtra("User");
        phone = user.getNumber();
        password = user.getPassword();
        Log.d("TAAAAAAAAo","  " + phone + "   " + password);
        ButterKnife.bind(this);
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.add_photo)
                .showImageForEmptyUri(R.drawable.add_photo)
                .showImageOnFail(R.drawable.add_photo).cacheInMemory()
                .cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
                .displayer(new FadeInBitmapDisplayer(300)).build();

        mLoadingDialog = DialogBuilder.createLoadingDialog(this, "正在上传图片");
        mLoadingFinishDialog = DialogBuilder.createLoadingfinishDialog(this, "上传完成");

        //Bmob.initialize(this, "23fe35801c6ae4f698315d637955bb39");
        // mPhone = getIntent().getStringExtra("phone");
        // Log.d("TAAAAAAA", mPhone);

    }

    @OnClick({R.id.register_back, R.id.register_btn,R.id.iv_upload_photo})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.register_back:
                finish();
                break;
            case R.id.iv_upload_photo:
                final PhotoPickerIntent intent = new PhotoPickerIntent(RegisterActivity.this);
                intent.setPhotoCount(1);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.register_btn:
                String name = registerName.getText().toString();
                //if (!name.equals("")&& !password.equals("")&&!mPhone.equals("")) {
                if (!name.equals("") && !(upload_photo.getDrawable() == null)) {
                        User   usercurrent = new User();
                        usercurrent.setUsername(name);
                        usercurrent.setNumber(phone);
                        usercurrent.setPassword(password);
                        usercurrent.setPhoto(user.getPhoto());
                        usercurrent.setBirthday("");
                        usercurrent.setSex("");
                        usercurrent.setHeight("");
                        usercurrent.setWeight("");
                        usercurrent.setAvatar("");
                        Log.d("TAAAAAAAA1","  " + usercurrent.getNumber()+ "   " + password);
                                usercurrent.save(RegisterActivity.this, new SaveListener() {
                                    @Override
                                    public void onSuccess() {
                                        // TODO Auto-generated method stub
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                    }
                                    @Override
                                    public void onFailure(int code, String msg) {
                                        // TODO Auto-generated method stub
                                        Toast.makeText(RegisterActivity.this,"注册失败"+msg,Toast.LENGTH_SHORT).show();
                                    }
                                });
                } else {
                    ToastUtil.showLong(RegisterActivity.this, "请填写完整信息");
                }
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
            final BmobFile file_photo=new BmobFile(new File(pathList.get(0)));
            file_photo.upload(RegisterActivity.this, new UploadFileListener() {
                @Override
                public void onSuccess() {
                    user.setPhoto(file_photo);
                    imageLoader.displayImage(user.getPhoto().getUrl(),upload_photo, options);
                }
                @Override
                public void onFailure(int i, String s) {

                }
            });

       //     user.setPhoto(file_photo);
          //
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}