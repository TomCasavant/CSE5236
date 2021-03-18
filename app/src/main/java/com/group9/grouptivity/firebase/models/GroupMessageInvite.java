package com.group9.grouptivity.firebase.models;

public class GroupMessageInvite {
    private String mGroupMessageId;
    private String mGroupMessageName;
    private String mSenderUsername;

    private GroupMessageInvite() {} //Empty constructor needed for Firebase

    public GroupMessageInvite(String groupMessageName, String senderUsername) {
        this.mGroupMessageName = groupMessageName;
        this.mSenderUsername = senderUsername;
    }

    /** Returns the group message name associated with this invitation. */
    public String getGroupMessageName() {
        return this.mGroupMessageName;
    }

    /** Returns the username of the sender of this invitation. */
    public String getSenderUsername() {
        return this.mSenderUsername;
    }

    /** Returns the id of the group message associated with this group message. */
    public String getGroupMessageId() {return this.mGroupMessageId;}

}
