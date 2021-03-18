package com.group9.grouptivity.firebase.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.ItemClickListener;

import org.w3c.dom.Text;

import java.util.List;

public class GroupMessageInviteAdapter extends RecyclerView.Adapter<GroupMessageInviteAdapter.GroupMessageInviteViewHolder> {
    private List<GroupMessageInvite> mGroupMessageInviteList;
    private LayoutInflater mInflater;

    public GroupMessageInviteAdapter(Context context, List<GroupMessageInvite> groupMessageInviteList) {
        this.mGroupMessageInviteList = groupMessageInviteList;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public GroupMessageInviteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.groups_invites_recyclerview_row, parent, false);
        return new GroupMessageInviteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupMessageInviteViewHolder holder, int position) {
        GroupMessageInvite gmInv = mGroupMessageInviteList.get(position);
        holder.mGroupMessageId = gmInv.getGroupMessageId();
        holder.groupMessageNameTextView.setText(gmInv.getGroupMessageName());
        holder.senderUsernameTextView.setText(gmInv.getSenderUsername());
    }

    @Override
    public int getItemCount() {
        return mGroupMessageInviteList.size();
    }

    public class GroupMessageInviteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
                //TODO
                //Add the user to the GroupMessage with the given id
                //Delete the GroupMessageInvite from Firebase
            });

            mDeclineButton = itemView.findViewById(R.id.invite_accept_button);
            mDeclineButton.setOnClickListener((View v) -> {
                //TODO
                //Delete the GroupMessageInvite from Firebase
            });
        }

        @Override
        public void onClick(View view) {
            //Nothing to do onClick
        }
    }

    /** Returns the group message at a given position in the RecyclerView. */
   public GroupMessageInvite getItem(int position) {
        return mGroupMessageInviteList.get(position);
   }

}
