package com.example.user.sportslover.model;

import android.widget.Toast;

import com.activeandroid.util.Log;
import com.example.user.sportslover.application.BaseApplication;
import com.example.user.sportslover.bean.DynamicItem;
import com.example.user.sportslover.bean.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by user on 17-9-19.
 */
public class DynamicModelImpr implements DynamicModelInter {


    /**
     * 获取所有的朋友圈消息
     *
     * @param listener
     */
    @Override
    public void getDynamicItem(final SportModelInter.BaseListener listener) {
        BmobQuery<DynamicItem> query = new BmobQuery<DynamicItem>();
        query.order("-createdAt");
        query.findObjects(BaseApplication.getmContext(), new FindListener<DynamicItem>() {
            @Override
            public void onSuccess(List<DynamicItem> object) {
                if (object != null && object.size() != 0) {
                    listener.getSuccess(object);
                }
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }

    /**
     * 获取当前用户的所有动态
     *
     * @param user
     * @param listener
     */
    @Override
    public void getDynamicItemByPhone(User user, final SportModelInter.BaseListener listener) {
        BmobQuery<DynamicItem> query = new BmobQuery<DynamicItem>();
        query.addWhereEqualTo("Writer", user);
        query.findObjects(BaseApplication.getmContext(), new FindListener<DynamicItem>() {
            @Override
            public void onSuccess(List<DynamicItem> object) {
                if (object != null && object.size() != 0) {
                    listener.getSuccess(object);
                }
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }

    /**
     * 上传动态
     *
     * @param dynamicitem
     */
    public void sendDynamicItem(final DynamicItem dynamicitem, final SportModelInter.BaseListener listener) {
        if (dynamicitem.getPhotoList().size() != 0) {
            final String[] array = new String[dynamicitem.getPhotoList().size()];
            for (int i = 0; i < dynamicitem.getPhotoList().size(); i++) {
                array[i] = dynamicitem.getPhotoList().get(i).getLocalFile().getAbsolutePath();
                Log.i("path", "sendDynamicItem: " + array[i] + " " + dynamicitem.getPhotoList().size());
            }
            BmobFile.uploadBatch(BaseApplication.getmContext(), array, new UploadBatchListener() {

                @Override
                public void onSuccess(List<BmobFile> files, List<String> urls) {
                    if (urls.size() == array.length) {
                        dynamicitem.setPhotoList(files);
                        dynamicitem.save(BaseApplication.getmContext(), new SaveListener() {
                            @Override
                            public void onSuccess() {
                                listener.getSuccess(null);
                                Toast.makeText(BaseApplication.getmContext(), "上传成功", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(BaseApplication.getmContext(), "上传失败", Toast.LENGTH_LONG).show();
                                listener.getFailure();
                            }
                        });
                    }
                }

                @Override
                public void onError(int statuscode, String errormsg) {
                    Log.i("TAG", "onError: " + errormsg + statuscode);
                }

                @Override
                public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                    Log.i("TAG", "onProgress: " + curIndex + " " + curPercent + " " + total);
                }
            });
        } else {
            dynamicitem.save(BaseApplication.getmContext(), new SaveListener() {
                @Override
                public void onSuccess() {
                    listener.getSuccess(null);
                    Toast.makeText(BaseApplication.getmContext(), "上传成功", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(BaseApplication.getmContext(), "上传失败", Toast.LENGTH_LONG).show();
                    listener.getFailure();
                }
            });
        }

    }


}
