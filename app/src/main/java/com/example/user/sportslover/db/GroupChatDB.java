package com.example.user.sportslover.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user.sportslover.application.BaseApplication;
import com.example.user.sportslover.application.Config;
import com.example.user.sportslover.bean.GroupChatMessage;
import com.example.user.sportslover.bean.GroupInfo;
import com.example.user.sportslover.util.CommonUtil;
import com.example.user.sportslover.util.LogUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.newim.BmobIM;

public class GroupChatDB {

    //        ************************群消息表的各项列名//
    private static final String _ID = "_id";
    private static final String GROUP_MESSAGE_TABLE = "group_message_table";
    private static final String GROUP_ID = "groupId";
    private static final String GROUP_FROM_ID = "uid";
    private static final String GROUP_CREATE_TIME = "time";
    private static final String GROUP_SEND_STATUS = "sendStatus";
    private static final String GROUP_READ_STATUS = "readStatus";
    private static final String GROUP_CONTENT = "content";
    private static final String GROUP_MSG_TYPE = "type";
    private static final String GROUP_FROM_AVATAR = "avatar";
    private static final String GROUP_FROM_NAME = "name";
    private static final String GROUP_FROM_NICK = "nick";
    //        ************************群消息表的各项列名//

    /**
     * 创建群消息表的SQL语句
     */
    private static final String SQL_CREATE_GROUP_MESSAGE_TABLE = "CREATE TABLE " +
            GROUP_MESSAGE_TABLE + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + GROUP_ID
            + " TEXT, " + GROUP_FROM_ID + " TEXT, " + GROUP_FROM_NAME + " TEXT, " +
            GROUP_FROM_AVATAR + " TEXT, " + GROUP_CREATE_TIME + " TEXT, " + GROUP_SEND_STATUS + "" +
            " INTEGER, " + GROUP_READ_STATUS + " INTEGER, " + GROUP_CONTENT + " TEXT, " +
            GROUP_FROM_NICK + " TEXT, " + GROUP_MSG_TYPE + " INTEGER);";

    //        ************************群结构表的各项列名//
    private static final String GROUP_TABLE = "group_table";
    private static final String GROUP_AVATAR = "group_avatar";
    private static final String GROUP_NAME = "group_name";
    private static final String GROUP_NICK = "group_nick";
    private static final String GROUP_DESCRIPTION = "group_description";
    private static final String GROUP_CREATOR_ID = "creatorId";
    private static final String GROUP_TO_ID = "group_toId";
    private static final String GROUP_MEMBER = "group_member";
    private static final String GROUP_OBJECT_ID = "objectId";
    private static final String GROUP_NOTIFICATION = "notification";
    //        ************************群结构表的各项列名//
    private static final String SQL_CREATE_GROUP_TABLE = "CREATE TABLE " + GROUP_TABLE + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + GROUP_ID + " TEXT, " + GROUP_NAME + " " +
            "TEXT, " + GROUP_AVATAR + " TEXT, " + GROUP_NICK + " TEXT, " + GROUP_OBJECT_ID + " " +
            "TEXT, " + GROUP_CREATE_TIME + " TEXT, " + GROUP_CREATOR_ID + " TEXT, " +
            GROUP_MEMBER + " TEXT, " + GROUP_TO_ID + " TEXT, " + GROUP_NOTIFICATION + " TEXT, " +
            GROUP_SEND_STATUS + " INTEGER, " + GROUP_READ_STATUS + " INTEGER, " +
            GROUP_DESCRIPTION + " TEXT); ";


    /**
     * 储存键值为UID,值为DB的map
     */
    private static Map<String, GroupChatDB> sMap = new HashMap<>();

    private SQLiteDatabase mDB;

    private GroupChatDB(String userId) {
        GroupDBConfig config = new GroupDBConfig();
        config.setName(userId);
        config.setContext(BaseApplication.INSTANCE());
        mDB = new ChatSQLiteDBHelper(config.getContext(), config.getName(), null, config
                .getDbVersion(), config.getListener()).getWritableDatabase();
    }

    public static GroupChatDB create(String userId) {
        return getInstance(userId);
    }

    public static GroupChatDB create() {
        return getInstance(BmobIM.getInstance().getCurrentUid());
    }

    private synchronized static GroupChatDB getInstance(String userId) {
        if (userId == null) {
            throw new RuntimeException("userId is NULL!!!");
        }
        GroupChatDB groupChatDB = sMap.get(userId);
        if (groupChatDB == null) {
            LogUtil.e("新建一个数据库存储新的用户数据");
            groupChatDB = new GroupChatDB(userId);
            sMap.put(userId, groupChatDB);
        }
        return groupChatDB;
    }

    public List<GroupChatMessage> queryGroupChatMessageFromDB(String groupId, int page, long
            createTime) {
        List<GroupChatMessage> list = new ArrayList<>();
        if (mDB.isOpen()) {
            String sql;
            if (createTime > 0) {
                sql = "SELECT * from " + GROUP_MESSAGE_TABLE + " WHERE " + GROUP_ID + " = ?" + " " +
                        "AND " + GROUP_CREATE_TIME + " < " + createTime + " ORDER BY " + _ID
                        + " DESC LIMIT " + page * 10;
            } else {
                sql = "SELECT * from " + GROUP_MESSAGE_TABLE + " WHERE " + GROUP_ID + " = ?" + " " +
                        "ORDER BY " + _ID + " DESC LIMIT " + page * 10;
            }
            Cursor cursor = mDB.rawQuery(sql, new String[]{groupId});
            GroupChatMessage message;
            while (cursor != null && cursor.moveToNext()) {
                message = new GroupChatMessage();
                message.setBelongId(cursor.getString(cursor.getColumnIndexOrThrow(GROUP_FROM_ID)));
                message.setBelongAvatar(cursor.getString(cursor.getColumnIndexOrThrow
                        (GROUP_FROM_AVATAR)));
                message.setBelongNick(cursor.getString(cursor.getColumnIndexOrThrow
                        (GROUP_FROM_NICK)));
                message.setBelongUserName(cursor.getString(cursor.getColumnIndexOrThrow
                        (GROUP_FROM_NAME)));
                message.setMsgType(cursor.getInt(cursor.getColumnIndexOrThrow(GROUP_MSG_TYPE)));
                message.setCreateTime(cursor.getString(cursor.getColumnIndexOrThrow
                        (GROUP_CREATE_TIME)));
                message.setSendStatus(cursor.getInt(cursor.getColumnIndexOrThrow
                        (GROUP_SEND_STATUS)));
                message.setContent(cursor.getString(cursor.getColumnIndexOrThrow(GROUP_CONTENT)));
                message.setReadStatus(cursor.getInt(cursor.getColumnIndexOrThrow
                        (GROUP_READ_STATUS)));
                list.add(message);
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
//                        反转消息,使list底部为最新的消息
            Collections.reverse(list);
        }
        return list;
    }

    /**
     * 保存群消息到数据库
     *
     * @param chatMessage 群消息实体
     * @return 结果
     */
    public long saveGroupChatMessage(GroupChatMessage chatMessage) {
        long result = -1;
        if (mDB.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(GROUP_FROM_AVATAR, chatMessage.getBelongAvatar());
            values.put(GROUP_FROM_NICK, chatMessage.getBelongNick());
            if (!isExistGroupChatMessage(chatMessage.getGroupId(), chatMessage.getCreateTime())) {
                values.put(GROUP_ID, chatMessage.getGroupId());
                values.put(GROUP_FROM_ID, chatMessage.getBelongId());
                values.put(GROUP_FROM_NAME, chatMessage.getBelongUserName());
                values.put(GROUP_CONTENT, chatMessage.getContent());
                values.put(GROUP_MSG_TYPE, chatMessage.getMsgType());
                values.put(GROUP_CREATE_TIME, chatMessage.getCreateTime());
                values.put(GROUP_SEND_STATUS, chatMessage.getSendStatus());
                values.put(GROUP_READ_STATUS, chatMessage.getReadStatus());
                result = mDB.insert(GROUP_MESSAGE_TABLE, null, values);
            } else {
//                               否则更新数据
                LogUtil.e("更新群数据");
                result = mDB.update(GROUP_MESSAGE_TABLE, values, GROUP_ID + " =? AND " +
                        GROUP_CREATE_TIME + " =?", new String[]{chatMessage.getGroupId(),
                        chatMessage.getCreateTime()});
            }
        }
        if (result > 0) {
            LogUtil.e("保存或更新群消息到数据库中成功");
        } else {
            LogUtil.e("保存或更新群消息到数据库中失败");
        }
        return result;
    }

    /**
     * 判断是否存在群消息
     *
     * @param groupId     群ID
     * @param createdTime 消创建时间
     * @return 结果
     */
    public boolean isExistGroupChatMessage(String groupId, String createdTime) {
        boolean result = false;
        if (mDB.isOpen()) {
            Cursor cursor = mDB.query(GROUP_MESSAGE_TABLE, null, GROUP_ID + " =? AND " +
                    GROUP_CREATE_TIME + " =?", new String[]{groupId, createdTime}, null, null,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                result = true;
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }

    /**
     * 获取所有的群结构表
     *
     * @return 结果集
     */
    public List<GroupInfo> getAllGroupMessage() {
        List<GroupInfo> list = null;
        if (mDB.isOpen()) {
            Cursor cursor = mDB.rawQuery("select * from " + GROUP_TABLE, null);
            if (cursor != null) {
                list = new ArrayList<>();
                GroupInfo groupInfo;
                while (cursor.moveToNext()) {
                    groupInfo = new GroupInfo();
                    groupInfo.setGroupId(cursor.getString(cursor.getColumnIndexOrThrow(GROUP_ID)));
                    groupInfo.setObjectId(cursor.getString(cursor.getColumnIndexOrThrow
                            (GROUP_OBJECT_ID)));
                    groupInfo.setCreatorId(cursor.getString(cursor.getColumnIndexOrThrow
                            (GROUP_CREATOR_ID)));
                    groupInfo.setCreatedTime(cursor.getString(cursor.getColumnIndexOrThrow
                            (GROUP_CREATE_TIME)));
                    groupInfo.setGroupDescription(cursor.getString(cursor.getColumnIndexOrThrow
                            (GROUP_DESCRIPTION)));
                    groupInfo.setGroupAvatar(cursor.getString(cursor.getColumnIndexOrThrow
                            (GROUP_AVATAR)));
                    groupInfo.setGroupName(cursor.getString(cursor.getColumnIndexOrThrow
                            (GROUP_NAME)));
                    groupInfo.setGroupNick(cursor.getString(cursor.getColumnIndexOrThrow
                            (GROUP_NICK)));
                    groupInfo.setToId(cursor.getString(cursor.getColumnIndexOrThrow(GROUP_TO_ID)));
                    groupInfo.setSendStatus(cursor.getInt(cursor.getColumnIndexOrThrow
                            (GROUP_SEND_STATUS)));
                    groupInfo.setReadStatus(cursor.getInt(cursor.getColumnIndexOrThrow
                            (GROUP_READ_STATUS)));
                    groupInfo.setgroupMember(CommonUtil.string2list(cursor.getString(cursor
                            .getColumnIndexOrThrow(GROUP_MEMBER))));
                    groupInfo.setNotification(cursor.getString(cursor.getColumnIndexOrThrow
                            (GROUP_NOTIFICATION)));
                    list.add(groupInfo);
                }
                if (!cursor.isClosed()) {
                    cursor.close();
                }
            }
        }
        return list;
    }


    public boolean saveGroupInfo(List<GroupInfo> list) {
        boolean result = true;
        for (GroupInfo message : list) {
            if (saveGroupInfo(message) < 0) {
                result = false;
            }
        }
        return result;

    }

    public long saveGroupInfo(GroupInfo message) {
        long result = -1;
        if (mDB.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(GROUP_ID, message.getGroupId());
            values.put(GROUP_TO_ID, message.getToId());
            values.put(GROUP_AVATAR, message.getGroupAvatar());
            values.put(GROUP_OBJECT_ID, message.getObjectId());
            values.put(GROUP_NAME, message.getGroupName());
            values.put(GROUP_NICK, message.getGroupNick());
            values.put(GROUP_DESCRIPTION, message.getGroupDescription());
            values.put(GROUP_CREATE_TIME, message.getCreatedTime());
            values.put(GROUP_CREATOR_ID, message.getCreatorId());
            values.put(GROUP_SEND_STATUS, message.getSendStatus());
            values.put(GROUP_READ_STATUS, message.getReadStatus());
            values.put(GROUP_NOTIFICATION, message.getNotification());
            values.put(GROUP_MEMBER, CommonUtil.list2string(message.getGroupMember()));
            if (!hasGroupInfo(message.getGroupId())) {
                result = mDB.insert(GROUP_TABLE, null, values);
            } else {
                LogUtil.e("在数据库中更新群结构消息");
                result = mDB.update(GROUP_TABLE, values, GROUP_OBJECT_ID + " =?", new
                        String[]{message.getObjectId()});
            }
        }
        if (result > 0) {
            LogUtil.e("插入或更新群结构消息成功");
        } else {
            LogUtil.e("插入或更新群结构消息失败");
        }
        return result;
    }

    public int queryGroupChatMessageUnreadCount(String groupId) {
        int size = 0;
        if (mDB.isOpen()) {
            Cursor cursor = mDB.query(GROUP_MESSAGE_TABLE, null, GROUP_READ_STATUS + " =? " +
                    "AND " + GROUP_ID + " =?", new String[]{Config.STATUS_VERIFY_NONE + "",
                    groupId}, null, null, null);
            if (cursor != null) {
                size = cursor.getCount();
                cursor.close();
            }
        }
        return size;
    }

    public long deleteAllGroupChatMessage(String groupId) {
        long result = -1;
        if (mDB.isOpen()) {
            if (isExistGroupChatMessage(groupId)) {
                result = mDB.delete(GROUP_MESSAGE_TABLE, GROUP_ID + " =?", new
                        String[]{groupId});
            } else {
                LogUtil.e("数据库中没有该群的消息，所以删除失败");
                result = -1;
            }
        }
        if (result > 0) {
            LogUtil.e("在数据库中删除群消息成功");
        } else {
            LogUtil.e("在数据库中删除群消息失败");
        }
        return result;
    }

    private boolean isExistGroupChatMessage(String groupId) {
        boolean result = false;
        if (mDB.isOpen()) {
            Cursor cursor = mDB.query(GROUP_MESSAGE_TABLE, null, GROUP_ID + " =?", new
                    String[]{groupId}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                result = true;
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }

    /**
     * 更新群消息为已读状态
     *
     * @param groupId  群ID
     * @param isReaded 是否已读取
     * @return 结果
     */
    public boolean updateReceivedGroupChatMessageReaded(String groupId, boolean isReaded) {
        long result = -1;
        if (mDB.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(GROUP_READ_STATUS, isReaded ? Config.STATUS_VERIFY_READED : Config
                    .STATUS_VERIFY_NONE);
            result = mDB.update(GROUP_MESSAGE_TABLE, values, GROUP_ID + " =?", new
                    String[]{groupId});
        }
        return result > 0;
    }


    private boolean hasGroupInfo(String id) {
        boolean result = false;
        if (mDB.isOpen()) {
            Cursor cursor = mDB.query(GROUP_TABLE, null, GROUP_ID + " =?", new
                    String[]{id}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                result = true;
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }


    public boolean updateGroupChatMessageNick(String groupId, String nick) {
        long result = -1;
        if (mDB.isOpen()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(GROUP_FROM_NICK, nick);
            result = mDB.update(GROUP_MESSAGE_TABLE, contentValues, GROUP_ID + " =? AND " +
                    GROUP_FROM_ID + " =?", new String[]{groupId, BmobIM.getInstance()
                    .getCurrentUid()});
        }
        if (result > 0) {
            LogUtil.e("在数据库中更新群消息上的昵称成功");
        } else {
            LogUtil.e("在数据库中更新群消息上的昵称失败");
        }
        return result > 0;
    }

    public boolean clearAllGroupChatMessages() {
        long result = -1;
        if (mDB.isOpen()) {
            result = mDB.delete(GROUP_MESSAGE_TABLE, null, null);
        }
        if (result > 0) {
            LogUtil.e("删除所有的群消息成功");
        } else {
            LogUtil.e("删除所有的群消息失败");
        }
        return result > 0;
    }

    public long deleteGroupInfo(String groupId) {
        long result = -1;
        if (mDB.isOpen()) {
            if (!hasGroupInfo(groupId)) {
                LogUtil.e("数据库中没有群结构消息可删");
            } else {
                result = mDB.delete(GROUP_TABLE, GROUP_ID + " =? ", new String[]{groupId});
                LogUtil.e("删除的群结构消息行号" + result);
            }
        }
        if (result > 0) {
            LogUtil.e("删除群结构消息成功");
        } else {
            LogUtil.e("删除群结构消息失败");
        }
        return result;
    }

    private boolean isExistGroupInfo(String uid) {
        boolean result = false;
        if (mDB.isOpen()) {
            Cursor cursor = mDB.query(GROUP_MESSAGE_TABLE, null, GROUP_FROM_ID + " =?", new
                    String[]{uid}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                result = true;
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }

    /**
     * 数据库辅助类
     */
    private class ChatSQLiteDBHelper extends SQLiteOpenHelper {
        private GroupDBConfig.DBUpdateListener mListener;

        private ChatSQLiteDBHelper(Context context, String name, SQLiteDatabase.CursorFactory
                factory, int version, GroupDBConfig.DBUpdateListener listener) {
            super(context, name, factory, version);
            this.mListener = listener;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createChatMessageTable(db);
        }

        private void createChatMessageTable(SQLiteDatabase db) {
//                        创建群组消息表
            db.execSQL(SQL_CREATE_GROUP_MESSAGE_TABLE);
//                        创建群结构表
            db.execSQL(SQL_CREATE_GROUP_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (mListener != null) {
                mListener.onUpdate(db, oldVersion, newVersion);
            }
        }
    }
}
