package com.group9.grouptivity.firebase.models;

public class Message {
    private GroupMessage mGroupMessage;
    private String mSenderUsername; //Might want to change this to a string of just their username
    private long mTimeStamp;

    public Message(GroupMessage groupMessage, String senderUserName, long timeStamp) {
        this.mGroupMessage = groupMessage;
        this.mSenderUsername = senderUserName;
        this.mTimeStamp = timeStamp;
    }

    /** Returns the group message associated with this message. */
    public GroupMessage getGroupMessage() {
        return this.mGroupMessage;
    }

    /** Returns the user account that sent this message. */
    public String getSender() {
        return this.mSenderUsername;
    }

    /** Returns the timestamp that this message was sent. */
    public long getTimeStamp() {
        return this.mTimeStamp;
    }
}
