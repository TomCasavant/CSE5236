package com.group9.grouptivity.firebase;

import com.group9.grouptivity.firebase.GroupMessage;

import java.util.ArrayList;
import java.util.List;

public class UserAccount {
    private String mUsername;
    private String mPassword;
    private String mEmailAddress;
    private List<GroupMessage> mGroupMessages;
    private List<GroupMessage> mInvites;

    public UserAccount(String username, String password, String emailAddress) {
        this.mUsername = username;
        this.mPassword = password;
        this.mEmailAddress = emailAddress;
        this.mGroupMessages = new ArrayList<>();
        this.mInvites = new ArrayList<>();
    }

    public UserAccount(String username, String password, String emailAddress, List<GroupMessage> groupMessages, List<GroupMessage> invites) {
        this.mUsername = username;
        this.mPassword = password;
        this.mEmailAddress = emailAddress;
        this.mGroupMessages = groupMessages;
        this.mInvites = invites;
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
    public List<GroupMessage> getGroupMessageList(){
        return this.mGroupMessages;
    }

    /** Returns the list of pending group message invites the user account currently has. */
    public List<GroupMessage> getInvitesList(){
        return this.mInvites;
    }
}
