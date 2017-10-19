package com.example.user.sportslover.db;

import com.example.user.sportslover.application.BaseApplication;
import com.example.user.sportslover.application.Config;
import com.example.user.sportslover.bean.GroupInfo;
import com.example.user.sportslover.bean.User;
import com.example.user.sportslover.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


public class GroupManager {

    private static GroupManager instance;

    private static final Object LOCK = new Object();

    private GroupManager() {

    }

    public static GroupManager getInstance() {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new GroupManager();
                }
            }
        }
        return instance;
    }

    public void sendCreateGroupMessage(String groupName, final String groupDescription, final
    SaveListener listener) {
        GroupInfo message = createGroupInfo(groupName, groupDescription);
        message.setSendStatus(Config.SEND_STATUS_SUCCESS);
        message.setReadStatus(Config.STATUS_VERIFY_READED);
        final String time = String.valueOf(System.currentTimeMillis());
        message.setCreatedTime(time);
        message.save(BaseApplication.INSTANCE(), new SaveListener() {
            @Override
            public void onSuccess() {
                BmobQuery<GroupInfo> bmobQuery = new BmobQuery<>();
                bmobQuery.addWhereEqualTo("toId", BmobUser.getCurrentUser(BaseApplication
                        .INSTANCE(), User.class).getObjectId());
                bmobQuery.addWhereEqualTo("groupDescription", groupDescription);
                bmobQuery.findObjects(BaseApplication.INSTANCE(), new FindListener<GroupInfo>() {
                    @Override
                    public void onSuccess(List<GroupInfo> list) {

                        if (list != null && list.size() > 0) {
                            final GroupInfo GroupInfo = list.get(0);
                            GroupInfo.setGroupId(GroupInfo.getObjectId());
//                                                                设置未读取状态，方便定时拉取到
                            GroupInfo.setReadStatus(Config.STATUS_VERIFY_NONE);
                            GroupInfo.update(BaseApplication.INSTANCE(), new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    GroupChatDB.create().saveGroupInfo(GroupInfo);
                                    LogUtil.e("success");
                                    /*uploadChatTableMessage(GroupInfo, new SaveListener() {
                                        @Override
                                        public void onSuccess() {
                                            sendGroupChatMessage("大家好", GroupInfo.getGroupId(),
                                                    Constant.TAG_MSG_TYPE_TEXT, new
                                                            OnSendMessageListener() {
                                                @Override
                                                public void onSending() {

                                                }

                                                @Override
                                                public void onSuccess() {
                                                    LogUtil.e("发送群欢迎消息成功");
                                                    listener.onSuccess();
                                                }

                                                @Override
                                                public void onFailed(BmobException e) {
                                                    LogUtil.e("发送群欢迎消息失败");
                                                    listener.onFailure(e.getErrorCode(), e
                                                            .getMessage());

                                                }
                                            });
                                        }

                                        @Override
                                        public void onFailure(int i, String s) {
                                            LogUtil.e("error" + s + i);
                                            listener.onFailure(i, s);

                                        }
                                    });*/
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    LogUtil.e("error" + s + i);
                                    listener.onFailure(i, s);
                                }
                            });
                        } else {
                            listener.onFailure(0, "error");
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        LogUtil.e("error:" + s + i);
                        listener.onFailure(i, s);
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                LogUtil.e("保存群主所建的群结构消息到服务器上失败" + s + i);
                listener.onFailure(i, s);
            }
        });
    }

    private GroupInfo createGroupInfo(String groupName, String groupDescription) {
        User currentUser = BmobUser.getCurrentUser(BaseApplication.INSTANCE(), User.class);
        GroupInfo message = new GroupInfo();
        message.setGroupName(groupName);
        message.setGroupDescription(groupDescription);
        message.setGroupAvatar(currentUser.getAvatar() == null ? "" : currentUser.getAvatar());
        message.setCreatorId(currentUser.getObjectId());
        message.setReadStatus(Config.STATUS_VERIFY_NONE);
        message.setSendStatus(Config.SEND_STATUS_SUCCESS);
        message.setToId(currentUser.getObjectId());
        message.setGroupNick("");
        message.setNotification("");
        return message;
    }

    private void uploadChatTableMessage(final GroupInfo groupInfo, SaveListener listener) {
        List<String> groupMember = groupInfo.getGroupMember();
        GroupInfo message;
        List<String> copy = new ArrayList<>(groupMember);
        if (copy.contains(BmobUser.getCurrentUser(BaseApplication.INSTANCE(), User.class)
                .getObjectId())) {
            copy.remove(BmobUser.getCurrentUser(BaseApplication.INSTANCE(), User.class)
                    .getObjectId());
        }
        List<BmobObject> groupInfoList = new ArrayList<>();
        for (int i = 0; i < copy.size(); i++) {
            message = new GroupInfo();
            message.setSendStatus(Config.SEND_STATUS_SUCCESS);
            message.setReadStatus(Config.STATUS_VERIFY_NONE);
            message.setGroupDescription(groupInfo.getGroupDescription());
            message.setGroupId(groupInfo.getGroupId());
            message.setCreatedTime(groupInfo.getCreatedTime());
            message.setToId(copy.get(i));
            message.setgroupMember(groupMember);
            message.setGroupAvatar(groupInfo.getGroupAvatar());
            message.setGroupName(groupInfo.getGroupName());
            message.setGroupNick(groupInfo.getGroupNick());
            message.setNotification(groupInfo.getNotification());
            message.setCreatorId(groupInfo.getCreatorId());
            groupInfoList.add(message);
        }
        new BmobObject().insertBatch(BaseApplication.INSTANCE(), groupInfoList, listener);
    }

   /* private void sendGroupChatMessage(String content, String groupId, int msgType, final
   OnSendMessageListener listener) {
      sendTextMessage(false, createGroupChatMessage(content, groupId, msgType), new
      OnSendMessageListener() {
            @Override
            public void onSending() {
                LogUtil.e("发送建群欢迎消息中.............");

            }

            @Override
            public void onSuccess() {
                LogUtil.e("发送建群欢迎消息成功:这里是推送和上传成功后的回调");
                listener.onSuccess();
            }

            @Override
            public void onFailed(BmobException e) {
                LogUtil.e("发送建群欢迎消息失败" + e.getMessage() + e.getErrorCode());
                listener.onFailed(e);
            }
        });
    }*/

}
