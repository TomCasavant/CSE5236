package com.group9.grouptivity.firebase.models.recyclerViewAdapters.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.models.AbstractMessage;

import java.util.Calendar;

public abstract class AbstractMessageViewHolder extends AbstractClickableViewHolder {
    private TextView senderUsernameTextView;
    private TextView timestampTextView;
    private static final int SECONDS_TO_MILLISECONDS = 1000;

    protected AbstractMessageViewHolder(@NonNull View itemView) {
        super(itemView);
        senderUsernameTextView = itemView.findViewById(R.id.message_sender_name_textview);
        timestampTextView = itemView.findViewById(R.id.message_timestamp_textview);
        itemView.setOnClickListener(this);
    }

    /** Sets text of the name of the sender. */
    public void setSenderUsernameText(String senderUsername) {
        this.senderUsernameTextView.setText(senderUsername);
    }

    /** Sets the timestamp text to the given seconds since Epoch. */
    public void setTimestampText(long timestampSeconds) {
        Calendar.getInstance().setTimeInMillis(timestampSeconds * SECONDS_TO_MILLISECONDS);
        timestampTextView.setText(Calendar.getInstance().toString());
    }

    /** Binds an abstract message to the viewholder. */
    public void bindMessage(AbstractMessage message) {
        message.bindMessage(this);
    }

    /** Binds the data part of all abstract messages. */
    protected void bindAbstractMessageData(AbstractMessage message) {
        this.senderUsernameTextView.setText(message.getSender());
        Calendar.getInstance().setTimeInMillis(message.getTimeStamp() * SECONDS_TO_MILLISECONDS);
        timestampTextView.setText(Calendar.getInstance().toString());
    }
}
