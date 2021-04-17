package com.group9.grouptivity.firebase.models;


/**
 * A Group Message Model consisting of solely a name and key.
 */
public class GroupMessage extends KeyedDataModel {
    private String mName;

    protected GroupMessage() {
        this.mName = "null";
    } //Empty constructor needed for Firebase

    public GroupMessage(String name) {
        this.mName = name;
    }

    public GroupMessage(GroupMessage groupMessage) {
        this.mKey = groupMessage.mKey;
        this.mName = groupMessage.mName;
    }

    /**
     * Returns the name of the GroupMessage.
     */
    public String getName() {
        return this.mName;
    }

    /**
     * Sets the name of the GroupMessage.
     */
    public void setName(String name) {
        this.mName = name;
    }


}
