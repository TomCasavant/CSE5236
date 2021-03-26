package com.group9.grouptivity.firebase.models;

public class GroupMessageMember extends KeyedDataModel {
    private String mEmailAddress;
    private boolean mIsMuted;

    protected GroupMessageMember() {} //Empty constructor needed for Firebase

    public GroupMessageMember(String name){
        this.mEmailAddress = name;
        this.mIsMuted = false;
    }

    public GroupMessageMember(String name, boolean isMuted){
        this.mEmailAddress = name;
        this.mIsMuted = isMuted;
    }

    /** Returns the email address of the GroupMessageMember. */
    public String getEmailAddress() {
        return this.mEmailAddress;
    }

    /** Sets the email of the GroupMessageMember. */
    public String setEmailAddress() {
        return this.mEmailAddress;
    }

    /** Returns whether the GroupMessage Member has the GroupMessage muted. */
    public boolean getIsMuted() {
        return this.mIsMuted;
    }

    /** Sets the value of isMuted to the given boolean. */
    public void setIsMuted(boolean isMuted) {
        this.mIsMuted = isMuted;
    }
}
