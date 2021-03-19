package com.group9.grouptivity.firebase.models.recyclerViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.FirebaseRTDBHelper;
import com.group9.grouptivity.firebase.ItemClickListener;
import com.group9.grouptivity.firebase.models.GroupMessageInvite;
import com.group9.grouptivity.firebase.models.recyclerViewAdapters.viewHolders.GroupMessageInviteViewHolder;
import com.group9.grouptivity.firebase.models.recyclerViewAdapters.viewHolders.GroupMessageViewHolder;

import java.util.List;

public class GroupMessageInviteAdapter extends RecyclerView.Adapter<GroupMessageInviteViewHolder> {
    private List<GroupMessageInvite> mGroupMessageInviteList;
    private LayoutInflater mInflater;
    private ItemClickListener mItemClickListener;

    public GroupMessageInviteAdapter(Context context, List<GroupMessageInvite> groupMessageInviteList) {
        this.mGroupMessageInviteList = groupMessageInviteList;
        this.mInflater = LayoutInflater.from(context);
    }

    public GroupMessageInviteAdapter(Context context, List<GroupMessageInvite> groupMessageInviteList, ItemClickListener itemClickListener) {
        this.mGroupMessageInviteList = groupMessageInviteList;
        this.mInflater = LayoutInflater.from(context);
        this.mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public GroupMessageInviteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.groups_invites_recyclerview_row, parent, false);
        GroupMessageInviteViewHolder viewHolder = new GroupMessageInviteViewHolder(view);
        viewHolder.setClickListener(mItemClickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupMessageInviteViewHolder holder, int position) {
        GroupMessageInvite gmInv = mGroupMessageInviteList.get(position);
        holder.setGroupMessageId(gmInv.getGroupMessageId());
        holder.setGroupMessageNameText(gmInv.getGroupMessageName());
        holder.setSenderUsernameText(gmInv.getSenderUsername());
    }

    @Override
    public int getItemCount() {
        return mGroupMessageInviteList.size();
    }



    /** Returns the group message at a given position in the RecyclerView. */
   public GroupMessageInvite getItem(int position) {
        return mGroupMessageInviteList.get(position);
   }

}