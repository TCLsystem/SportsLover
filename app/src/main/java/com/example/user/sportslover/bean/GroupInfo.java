package com.example.user.sportslover.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class GroupInfo extends BmobObject {

        /**
         * 群名
         */
        private String groupName;
        /**
         * 群描述
         */
        private String groupDescription;
        /**
         * 群ID
         */
        private String groupId;
        /**
         * 创建者ID
         */
        private String creatorId;
        /**
         * 创建时间
         */
        private String createdTime;
        /**
         * 群组成员
         */
        private List<String> groupMember;
        /**
         * 群头像
         */
        private String groupAvatar;
        private String groupNick;

        /**
         * 去读状态
         */
        private Integer readStatus;
        /**
         * 发送状态
         */
        private Integer sendStatus;

        private String toId;


        private String notification;

        public String getNotification() {
                return notification;
        }

        public void setNotification(String notification) {
                this.notification = notification;
        }

        public String getToId() {
                return toId;
        }

        public void setToId(String toId) {
                this.toId = toId;
        }

        public Integer getSendStatus() {
                return sendStatus;
        }

        public void setSendStatus(Integer sendStatus) {
                this.sendStatus = sendStatus;
        }

        public Integer getReadStatus() {
                return readStatus;
        }

        public void setReadStatus(Integer readStatus) {
                this.readStatus = readStatus;
        }

        public String getGroupNick() {
                return groupNick;
        }

        public void setGroupNick(String groupNick) {
                this.groupNick = groupNick;
        }

        public String getGroupName() {
                return groupName;
        }

        public void setGroupName(String groupName) {
                this.groupName = groupName;
        }

        public String getGroupDescription() {
                return groupDescription;
        }

        public void setGroupDescription(String groupDescription) {
                this.groupDescription = groupDescription;
        }

        public String getGroupId() {
                return groupId;
        }

        public void setGroupId(String groupId) {
                this.groupId = groupId;
        }

        public String getCreatorId() {
                return creatorId;
        }

        public void setCreatorId(String creatorId) {
                this.creatorId = creatorId;
        }

        public String getCreatedTime() {
                return createdTime;
        }

        public void setCreatedTime(String createdTime) {
                this.createdTime = createdTime;
        }

        public List<String> getGroupMember() {
                return groupMember;
        }

        public void setgroupMember(List<String> groupMember) {
                this.groupMember = groupMember;
        }

        public String getGroupAvatar() {
                return groupAvatar;
        }

        public void setGroupAvatar(String groupAvatar) {
                this.groupAvatar = groupAvatar;
        }
}
