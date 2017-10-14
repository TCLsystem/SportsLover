package com.example.user.sportslover.bean;

import cn.bmob.v3.BmobObject;

public class BaseMessage extends BmobObject {

        /**
         * 会话类型:群聊或单聊
         */
        private String conversationType;

        /**
         * 发送者头像
         */
        private String belongAvatar;
        /**
         * 发送者昵称
         */
        private String belongNick;
        /**
         * 发送者用户名
         */
        private String belongUserName;

        /**
         * 发送的消息内容
         */
        private String content;
        /**
         * 发送方的ID
         */
        private String belongId;

        /**
         * 消息的类型
         */
        private Integer msgType;

        /**
         * 消息读取的状态
         */
        private Integer readStatus;

        /**
         * 消息发送的状态
         */
        private Integer sendStatus;

        /**
         * 消息创建的时间
         */
        private String createTime;

        public String getBelongAvatar() {
                return belongAvatar;
        }

        public void setBelongAvatar(String belongAvatar) {
                this.belongAvatar = belongAvatar;
        }

        public String getBelongNick() {
                return belongNick;
        }

        public void setBelongNick(String belongNick) {
                this.belongNick = belongNick;
        }

        public String getBelongUserName() {
                return belongUserName;
        }

        public void setBelongUserName(String belongUserName) {
                this.belongUserName = belongUserName;
        }

        public String getContent() {
                return content;
        }

        public void setContent(String content) {
                this.content = content;
        }

        public String getBelongId() {
                return belongId;
        }

        public void setBelongId(String belongId) {
                this.belongId = belongId;
        }

        public Integer getMsgType() {
                return msgType;
        }

        public void setMsgType(Integer msgType) {
                this.msgType = msgType;
        }

        public Integer getReadStatus() {
                return readStatus;
        }

        public void setReadStatus(Integer readStatus) {
                this.readStatus = readStatus;
        }

        public Integer getSendStatus() {
                return sendStatus;
        }

        public void setSendStatus(Integer sendStatus) {
                this.sendStatus = sendStatus;
        }

        public String getCreateTime() {
                return createTime;
        }

        public void setCreateTime(String createTime) {
                this.createTime = createTime;
        }

        public String getConversationType() {
                return conversationType;
        }

        public void setConversationType(String conversationType) {
                this.conversationType = conversationType;
        }

        @Override
        public boolean equals(Object obj) {
                if (obj instanceof BaseMessage) {
                        BaseMessage baseMessage = (BaseMessage) obj;
                        return baseMessage.getCreateTime().equals(getCreateTime()) && baseMessage.getBelongId().equals(getBelongId());
                }
                return false;
        }
}
