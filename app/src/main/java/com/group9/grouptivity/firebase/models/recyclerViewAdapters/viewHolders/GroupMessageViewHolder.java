package com.group9.grouptivity.firebase.models.recyclerViewAdapters.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.models.GroupMessage;

public class GroupMessageViewHolder extends AbstractClickableViewHolder {
    private TextView groupMessageTextView;

    public GroupMessageViewHolder(@NonNull View itemView) {
        super(itemView);
        groupMessageTextView = itemView.findViewById(R.id.group_message_name);
        itemView.setOnClickListener(this);
    }

    /** Sets the name of the Group Message. */
    public void setGroupMessageName(String groupMessageName) {
        this.groupMessageTextView.setText(groupMessageName);
    }

    /** Binds a GroupMessage to the viewholder. */
    public void bindGroupMessage(GroupMessage groupMessage) {
        this.groupMessageTextView.setText(groupMessage.getName());
    }
}
