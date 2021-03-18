package com.group9.grouptivity.firebase.models;


import java.util.List;

public class GroupMessageMember {
    private String mName;
    private boolean mIsMuted;

    private GroupMessageMember() {} //Empty constructor needed for Firebase

    public GroupMessageMember(String name){
        this.mName = name;
        this.mIsMuted = false;
    }

    public GroupMessageMember(String name, boolean isMuted){
        this.mName = name;
        this.mIsMuted = isMuted;
    }

    /** Returns the name of the GroupMessage. */
    public String getName() {
        return this.mName;
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
