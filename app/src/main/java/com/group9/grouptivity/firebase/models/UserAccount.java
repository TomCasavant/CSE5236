package com.group9.grouptivity.firebase.models;

import java.util.ArrayList;
import java.util.List;

public class UserAccount extends KeyedDataModel {
    private String mUsername;
    private String mEmailAddress;

    protected UserAccount() {
        this.mUsername = "null";
        this.mEmailAddress = "null";
    } //Empty constructor needed for Firebase

    public UserAccount(String emailAddress) {
        this.mUsername = emailAddress;
        this.mEmailAddress = emailAddress;
    }

    public UserAccount(String username, String emailAddress) {
        this.mUsername = username;
        this.mEmailAddress = emailAddress;
    }


    /**
     * Returns the username associated with the user account.
     */
    public String getUsername() {
        return this.mUsername;
    }

    /**
     * Returns the username associated with the user account.
     */
    public void settUsername(String username) {
        this.mUsername = username;
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
}