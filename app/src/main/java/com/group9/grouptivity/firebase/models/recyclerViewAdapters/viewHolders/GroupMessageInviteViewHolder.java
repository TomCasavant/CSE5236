package com.group9.grouptivity.firebase.models.recyclerViewAdapters.viewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.FirebaseRTDBHelper;
import com.group9.grouptivity.firebase.ItemClickListener;
import com.group9.grouptivity.firebase.models.GroupMessageInvite;

public class GroupMessageInviteViewHolder extends AbstractClickableViewHolder {
    private String mGroupMessageId;
    private TextView groupMessageNameTextView;
    private TextView senderTextView;
    private TextView senderUsernameTextView;
    private Button mAcceptButton;
    private Button mDeclineButton;

    public GroupMessageInviteViewHolder(@NonNull View itemView) {
        super(itemView);
        groupMessageNameTextView = itemView.findViewById(R.id.group_message_name);
        senderTextView = itemView.findViewById(R.id.invite_sender_textview);
        senderUsernameTextView = itemView.findViewById(R.id.invite_sender_name_textview);
        mAcceptButton = itemView.findViewById(R.id.invite_accept_button);
        mAcceptButton.setOnClickListener((View v) -> {
            FirebaseRTDBHelper.getInstance().addCurrentUserToGroupMessage(mGroupMessageId, groupMessageNameTextView.getText().toString());
            FirebaseRTDBHelper.getInstance().deleteGroupMessageInvite(mGroupMessageId);
        });

        mDeclineButton = itemView.findViewById(R.id.invite_decline_button);
        mDeclineButton.setOnClickListener((View v) -> {
            FirebaseRTDBHelper.getInstance().deleteGroupMessageInvite(mGroupMessageId);
        });
    }

    /** Sets the id of the associated groupMessage. */
    public void setGroupMessageId(String groupMessageId) {
        this.mGroupMessageId = groupMessageId;
    }

    /** Sets the text of the sender username. */
    public void setSenderUsernameText(String senderUsername) {
        this.senderUsernameTextView.setText(senderUsername);
    }

    /** Sets the text of the Group Message Name. */
    public void setGroupMessageNameText(String groupMessageName) {
        this.groupMessageNameTextView.setText(groupMessageName);
    }

    /** Binds a given groupMessageInvite to the viewholder. */
    public void bindGroupMessageInvite (GroupMessageInvite groupMessageInvite) {
        this.mGroupMessageId = groupMessageInvite.getGroupMessageId();
        this.senderUsernameTextView.setText(groupMessageInvite.getSenderUsername());
        this.groupMessageNameTextView.setText(groupMessageInvite.getGroupMessageName());
    }

}
