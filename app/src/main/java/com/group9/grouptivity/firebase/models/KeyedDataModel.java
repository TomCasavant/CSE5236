package com.group9.grouptivity.firebase.models;

/**
 * Model for a key-value data object. Designed for extension to determine value.
 */
public class KeyedDataModel {
    protected String mKey;

    protected KeyedDataModel() {
        mKey = "null";
    } //empty constructor needed for firebase

    public KeyedDataModel(String key) {
        this.mKey = key;
    }


    /**
     * Gets the key associated with this data model.
     */
    public String retrieveKey() {
        return this.mKey;
    }

    /**
     * Sets the key associated with this data model to the incoming value.
     */
    public void setKey(String key) {
        this.mKey = key;
    }

}
