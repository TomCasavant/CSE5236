package com.group9.grouptivity.firebase.models;

public class GroupMessageInvite {
    private String mGroupMessageId;
    private String mGroupMessageName;
    private String mSenderUsername;

    private GroupMessageInvite() {
    } //Empty constructor needed for Firebase

    public GroupMessageInvite(String groupMessageName, String senderUsername) {
        this.mGroupMessageName = groupMessageName;
        this.mSenderUsername = senderUsername;
    }

    public GroupMessageInvite(GroupMessage groupMessage, String senderUsername) {
        this.mGroupMessageId = groupMessage.retrieveKey();
        this.mGroupMessageName = groupMessage.getName();
        this.mSenderUsername = senderUsername;
    }

    /**
     * Returns the group message name associated with this invitation.
     */
    public String getName() {
        return this.mGroupMessageName;
    }

    /**
     * Sets the group message name associated with this invitation.
     */
    public void setName(String groupMessageName) {
        this.mGroupMessageName = groupMessageName;
    }

    /**
     * Returns the username of the sender of this invitation.
     */
    public String getSender() {
        return this.mSenderUsername;
    }

    /**
     * Sets the username of the sender of this invitation.
     */
    public void setSender(String senderUsername) {
        this.mSenderUsername = senderUsername;
    }

    /**
     * Returns the id of the group message associated with this group message.
     */
    public String retrieveGroupMessageId() {
        return this.mGroupMessageId;
    }

    /**
     * Sets the id of the group message associated with this group message.
     */
    public void setGroupMessageId(String id) {
        this.mGroupMessageId = id;
    }

}
