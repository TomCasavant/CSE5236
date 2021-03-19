package com.group9.grouptivity.firebase.models;

public class TextMessage extends Message {
    private String mMessage;

    private TextMessage() { } //Empty constructor needed for Firebase

    public TextMessage(String groupMessageKey, String sender, long timeStamp, String messageText) {
        super(groupMessageKey, sender, timeStamp);
        this.mMessage = messageText;
    }

    /** Returns the message text associated with this text message. */
    public String getMessage() {
        return this.mMessage;
    }

    /** Sets the message text associated with this text message. */
    public void setMessage(String message) {
        this.mMessage = message;
    }


}
