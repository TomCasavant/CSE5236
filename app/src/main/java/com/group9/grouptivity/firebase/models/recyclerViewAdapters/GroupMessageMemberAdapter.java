package com.group9.grouptivity.firebase.models.recyclerViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.ItemClickListener;
import com.group9.grouptivity.firebase.models.GroupMessageMember;
import com.group9.grouptivity.firebase.models.recyclerViewAdapters.viewHolders.SimpleTextViewViewHolder;

import java.util.List;

public class GroupMessageMemberAdapter extends RecyclerView.Adapter<SimpleTextViewViewHolder> {
    private List<GroupMessageMember> mGroupMessageMemberList;
    private LayoutInflater mInflater;
    private ItemClickListener mItemClickListener;

    public GroupMessageMemberAdapter(Context context, List<GroupMessageMember> groupMessageMemberList) {
        this.mGroupMessageMemberList = groupMessageMemberList;
        this.mInflater = LayoutInflater.from(context);
    }

    public GroupMessageMemberAdapter(Context context, List<GroupMessageMember> groupMessageInviteList, ItemClickListener itemClickListener) {
        this.mGroupMessageMemberList = groupMessageInviteList;
        this.mInflater = LayoutInflater.from(context);
        this.mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public SimpleTextViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.simple_textview_recyclerview_row, parent, false);
        SimpleTextViewViewHolder viewHolder = new SimpleTextViewViewHolder(view);
        viewHolder.setClickListener(mItemClickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleTextViewViewHolder holder, int position) {
        holder.setText(this.mGroupMessageMemberList.get(position).getDisplayName());
    }

    @Override
    public int getItemCount() {
        return mGroupMessageMemberList.size();
    }

    /**
     * Returns the group message at a given position in the RecyclerView.
     */
    public GroupMessageMember getItem(int position) {
        return mGroupMessageMemberList.get(position);
    }

}
