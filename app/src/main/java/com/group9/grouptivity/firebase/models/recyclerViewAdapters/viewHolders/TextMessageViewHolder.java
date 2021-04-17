package com.group9.grouptivity.firebase.models.recyclerViewAdapters.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.models.TextMessage;

public class TextMessageViewHolder extends AbstractMessageViewHolder {
    private TextView messageBodyTextView;

    public TextMessageViewHolder(@NonNull View itemView) {
        super(itemView);
        this.messageBodyTextView = itemView.findViewById(R.id.text_message_body_textview);
        itemView.setOnClickListener(this);
    }

    /**
     * Sets the body text of this message.
     */
    public void setMessageBodyText(String messageBody) {
        this.messageBodyTextView.setText(messageBody);
    }

    /**
     * Binds a textMessage to the viewholder.
     */
    public void bindMessage(TextMessage textMessage) {
        super.bindAbstractMessageData(textMessage);
        this.messageBodyTextView.setText(textMessage.getMessage());
    }

}
