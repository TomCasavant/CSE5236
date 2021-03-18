package com.group9.grouptivity.firebase.models;

import java.util.ArrayList;
import java.util.List;

public class UserAccount {
    private String mUsername;
    private String mPassword;
    private String mEmailAddress;
    private List<String> mGroupMessageNameList;
    private List<GroupMessageInvite> mGroupMessageInviteList;

    private UserAccount() {} //Empty constructor needed for Firebase

    public UserAccount(String emailAddress) {
        this.mEmailAddress = emailAddress;
        this.mGroupMessageNameList = new ArrayList<>();
        this.mGroupMessageInviteList = new ArrayList<>();
    }

    public UserAccount(String username, String password, String emailAddress, List<String> groupMessages, List<GroupMessageInvite> invites) {
        this.mUsername = username;
        this.mPassword = password;
        this.mEmailAddress = emailAddress;
        this.mGroupMessageNameList = groupMessages;
        this.mGroupMessageInviteList = invites;
    }



    /** Returns the username associated with the user account. */
    public String getUsername() {
        return this.mUsername;
    }

    /** Returns the password associated with the user account. */
    public String getPassword() {
        return this.mPassword;
    }

    /** Sets the password associated with the user account to a given string. */
    public void setPassword(String password) {
        this.mPassword = password;
    }

    /** Returns the email address associated with the user account. */
    public String getEmailAddress() {
        return this.mEmailAddress;
    }

    /** Sets the password associated with the user account to a given string. */
    public void setEmailAddress(String emailAddress) {
        this.mEmailAddress = emailAddress;
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
