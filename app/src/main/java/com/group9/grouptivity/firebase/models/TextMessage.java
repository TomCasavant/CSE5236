package com.group9.grouptivity.firebase.models;

import com.group9.grouptivity.firebase.FirebaseRTDBHelper;
import com.group9.grouptivity.firebase.models.recyclerViewAdapters.MessageAdapter;
import com.group9.grouptivity.firebase.models.recyclerViewAdapters.viewHolders.AbstractMessageViewHolder;
import com.group9.grouptivity.firebase.models.recyclerViewAdapters.viewHolders.TextMessageViewHolder;

public class TextMessage extends AbstractMessage {
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

    @Override
    public Type retrieveType() {
        return Type.TEXT;
    }

    @Override
    public void bindMessage(AbstractMessageViewHolder holder) {
        ((TextMessageViewHolder) holder).bindMessage(this);
    }

    @Override
    public void addMessageToRTDB() {
        FirebaseRTDBHelper.getInstance().addMessage(this);
    }
}
