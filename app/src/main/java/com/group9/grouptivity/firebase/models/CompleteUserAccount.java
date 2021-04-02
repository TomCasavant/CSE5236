package com.group9.grouptivity.firebase.models;

import java.util.ArrayList;
import java.util.List;

public class CompleteUserAccount extends UserAccount {
    private List<GroupMessage> mGroupMessageList;
    private List<GroupMessageInvite> mGroupMessageInviteList;

    public CompleteUserAccount() {
        super();
        this.mGroupMessageList = new ArrayList<>();
        this.mGroupMessageInviteList = new ArrayList<>();
    } //Empty constructor needed for Firebase

    public CompleteUserAccount(String emailAddress, String displayName) {
        super(emailAddress, displayName);
        this.mGroupMessageList = new ArrayList<>();
        this.mGroupMessageInviteList = new ArrayList<>();
    }

    public CompleteUserAccount(String emailAddress, String displayName, List<GroupMessage> groupMessages, List<GroupMessageInvite> invites) {
        super(emailAddress, displayName);
        this.mGroupMessageList = groupMessages;
        this.mGroupMessageInviteList = invites;
    }

    /** Sets the list of group messages that the user account is currently a member of. */
    public void setGroupMessages(List<GroupMessage> groupMessages){
        this.mGroupMessageList = groupMessages;
    }

    /** Sets the list of pending group message invites the user account currently has. */
    public void setInvites(List<GroupMessageInvite> invites) {
        this.mGroupMessageInviteList = invites;
    }

    /** Returns the list of group messages that the user account is currently a member of. */
    public List<GroupMessage> getGroupMessages(){
        return this.mGroupMessageList;
    }

    /** Returns the list of pending group message invites the user account currently has. */
    public List<GroupMessageInvite> getInvites(){
        return this.mGroupMessageInviteList;
    }
}
