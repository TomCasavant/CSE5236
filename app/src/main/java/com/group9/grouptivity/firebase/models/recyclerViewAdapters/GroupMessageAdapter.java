package com.group9.grouptivity.firebase.models.recyclerViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.ItemClickListener;
import com.group9.grouptivity.firebase.models.GroupMessage;
import com.group9.grouptivity.firebase.models.recyclerViewAdapters.viewHolders.GroupMessageViewHolder;

import java.util.List;

public class GroupMessageAdapter extends RecyclerView.Adapter<GroupMessageViewHolder> {
    private List<GroupMessage> mGroupMessageList;
    private LayoutInflater mInflater;
    private ItemClickListener mItemClickListener;

    public GroupMessageAdapter(Context context, List<GroupMessage> groupMessageList) {
        this.mGroupMessageList = groupMessageList;
        this.mInflater = LayoutInflater.from(context);
    }

    public GroupMessageAdapter(Context context, List<GroupMessage> groupMessageList, ItemClickListener itemClickListener) {
        this.mGroupMessageList = groupMessageList;
        this.mInflater = LayoutInflater.from(context);
        this.mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public GroupMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.groups_recyclerview_row, parent, false);
        GroupMessageViewHolder viewHolder = new GroupMessageViewHolder(view);
        viewHolder.setClickListener(mItemClickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupMessageViewHolder holder, int position) {
        holder.bindGroupMessage(this.mGroupMessageList.get(position));
    }

    @Override
    public int getItemCount() {
        return mGroupMessageList.size();
    }



    /** Returns the group message at a given position in the RecyclerView. */
   public GroupMessage getItem(int position) {
        return mGroupMessageList.get(position);
   }

}
