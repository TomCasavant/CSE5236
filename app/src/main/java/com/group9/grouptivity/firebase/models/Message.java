package com.group9.grouptivity.firebase.models;

public class Message extends KeyedDataModel {
    private KeyedDataModel mGroupMessageKey; //Maybe just change to string?
    private String mSenderUsername;
    private long mTimeStamp; //Might want to change this to a Date

    protected Message() {} //Empty constructor needed for Firebase

    public Message(String groupMessageKey, String senderUserName, long timeStamp) {
        this.mGroupMessageKey = new KeyedDataModel(groupMessageKey);
        this.mSenderUsername = senderUserName;
        this.mTimeStamp = timeStamp;
    }

    /** Returns the group message key associated with this message. */
    public String getGroupMessageKey() {
        return this.mGroupMessageKey.getKey();
    }

    /** Sets the group message key associated with this message. */
    public void setGroupMessageKey(String groupMessageKey) {
        this.mGroupMessageKey.setKey(groupMessageKey);
    }

    /** Returns the username that sent this message. */
    public String getSender() {
        return this.mSenderUsername;
    }

    /** Sets the usernane that sent this message. */
    public void setSender(String senderUsername) {
        this.mSenderUsername = senderUsername;
    }

    /** Returns the timestamp that this message was sent. */
    public long getTimeStamp() {
        return this.mTimeStamp;
    }

    /** Sets the timestamp that this message was sent. */
    public void setTimeStamp(long timeStamp) {
        this.mTimeStamp = timeStamp;
    }
}
