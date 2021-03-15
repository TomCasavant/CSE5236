package com.group9.grouptivity.firebase.models;

public class TextMessage extends Message {
    private String mMessage;

    TextMessage() {} //Empty constructor needed for Firebase

    public TextMessage(GroupMessage groupMessage, String sender, long timeStamp, String messageText) {
        super(groupMessage, sender, timeStamp);
        this.mMessage = messageText;
    }

    /** Returns the message text associated with this text message. */
    public String getMessage() {
        return this.mMessage;
    }

}
