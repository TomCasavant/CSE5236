package com.group9.grouptivity.firebase.models;

import java.util.ArrayList;
import java.util.List;

public class UserAccount extends KeyedDataModel {
    private String mEmailAddress;
    private String mDisplayName;
    private List<GroupMessage> mGroups;

    protected UserAccount() {
        super();
        this.mEmailAddress = "null";
        this.mDisplayName = "null";
    } //Empty constructor needed for Firebase

    public UserAccount(String emailAddress) {
        this.mDisplayName = emailAddress;
        this.mEmailAddress = emailAddress;
    }

    public UserAccount(String emailAddress, String displayName){
        this.mEmailAddress = emailAddress;
        this.mDisplayName = displayName;
    }


    public List<GroupMessage> getGroups() { return this.mGroups;}
    public void setGroups(List<GroupMessage> groups) { this.mGroups = groups; }


    /**
     * Returns the username associated with the user account.
     */
    public String getDisplayName() {
        return this.mDisplayName;
    }

    /**
     * Returns the email address associated with the user account.
     */
    public String getEmailAddress() {
        return this.mEmailAddress;
    }

    /**
     * Sets the password associated with the user account to a given string.
     */
    public void setEmailAddress(String emailAddress) {
        this.mEmailAddress = emailAddress;
    }

    /**
     * Sets the password associated with the user account to a given string.
     */
    public void setDisplayName(String displayName) {
        this.mDisplayName = displayName;
    }
}