package com.group9.grouptivity.firebase.models;

import com.group9.grouptivity.firebase.models.recyclerViewAdapters.viewHolders.AbstractMessageViewHolder;

public abstract class AbstractMessage extends KeyedDataModel implements Comparable<AbstractMessage> {

    public enum Type {
        TEXT, ACTIVITY_POLL
    }

    private KeyedDataModel mGroupMessageKey; //Maybe just change to string?
    private String mSenderUsername;
    private long mTimeStamp; //Might want to change this to a Date

    protected AbstractMessage() {} //Empty constructor needed for Firebase

    protected AbstractMessage(String groupMessageKey, String senderUserName, long timeStamp) {
        this.mGroupMessageKey = new KeyedDataModel(groupMessageKey);
        this.mSenderUsername = senderUserName;
        this.mTimeStamp = timeStamp;
    }

    /** Returns the type associated with this message. */
    public abstract Type retrieveType();

    /** Binds the mesasge to the given viewholder. */
    public abstract void bindMessage(AbstractMessageViewHolder holder);

    /** Adds the message to the Firebase RTDB. */
    public abstract void addMessageToRTDB();

    /** Returns the group message key associated with this message. */
    public String retrieveGroupMessageKey() {
        return this.mGroupMessageKey.retrieveKey();
    }

    /** Sets the group message key associated with this message. */
    public void setGroupMessageKey(String groupMessageKey) {
        this.mGroupMessageKey.setKey(groupMessageKey);
    }

    /** Returns the username that sent this message. */
    public String getSender() {
        return this.mSenderUsername;
    }

    /** Sets the usernane that sent this message. */
    public void setSender(String senderUsername) {
        this.mSenderUsername = senderUsername;
    }

    /** Returns the timestamp that this message was sent. */
    public long getTimeStamp() {
        return this.mTimeStamp;
    }

    /** Sets the timestamp that this message was sent. */
    public void setTimeStamp(long timeStamp) {
        this.mTimeStamp = timeStamp;
    }

    /* I wanted to use a comparator for this, but something about the API may not be up to the
    current version. So instead the natural ordering is reverse timestamp order. */
    @Override
    public int compareTo(AbstractMessage abstractMessage) {
        int comp = Long.compare(this.mTimeStamp, abstractMessage.mTimeStamp);
        return comp;
    }

}
