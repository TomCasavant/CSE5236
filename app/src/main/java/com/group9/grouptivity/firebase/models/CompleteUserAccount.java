package com.group9.grouptivity.firebase.models;

import java.util.ArrayList;
import java.util.List;

public class CompleteUserAccount extends UserAccount {
    private List<String> mGroupMessageNameList;
    private List<GroupMessageInvite> mGroupMessageInviteList;

    private CompleteUserAccount() {
        this.mGroupMessageNameList = new ArrayList<>();
        this.mGroupMessageInviteList = new ArrayList<>();
    } //Empty constructor needed for Firebase

    public CompleteUserAccount(String username, String emailAddress, String displayName) {
        super(username, emailAddress, displayName);
        this.mGroupMessageNameList = new ArrayList<>();
        this.mGroupMessageInviteList = new ArrayList<>();
    }

    public CompleteUserAccount(String username, String emailAddress, String displayName, List<String> groupMessages, List<GroupMessageInvite> invites) {
        super(username, emailAddress, displayName);
        this.mGroupMessageNameList = groupMessages;
        this.mGroupMessageInviteList = invites;
    }


    /** Returns the list of group messages that the user account is currently a member of. */
    public List<String> getGroupMessageNameList(){
        return this.mGroupMessageNameList;
    }

    /** Returns the list of pending group message invites the user account currently has. */
    public List<GroupMessageInvite> getGroupMessageInvitesList(){
        return this.mGroupMessageInviteList;
    }
}
