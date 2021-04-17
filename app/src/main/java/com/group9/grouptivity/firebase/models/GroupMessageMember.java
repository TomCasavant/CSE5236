package com.group9.grouptivity.firebase.models;

public class GroupMessageMember extends UserAccount {
    private boolean mIsMuted;

    protected GroupMessageMember() {
        super();
    } //Empty constructor needed for Firebase

    public GroupMessageMember(String emailAddress) {
        super(emailAddress);
        this.mIsMuted = false;
    }

    public GroupMessageMember(String emailAddress, String displayName) {
        super(emailAddress, displayName);
        this.mIsMuted = false;
    }

    public GroupMessageMember(String emailAddress, String displayName, boolean isMuted) {
        super(emailAddress, displayName);
        this.mIsMuted = isMuted;
    }

    /**
     * Returns whether the GroupMessage Member has the GroupMessage muted.
     */
    public boolean getIsMuted() {
        return this.mIsMuted;
    }

    /**
     * Sets the value of isMuted to the given boolean.
     */
    public void setIsMuted(boolean isMuted) {
        this.mIsMuted = isMuted;
    }
}
