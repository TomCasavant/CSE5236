package com.group9.grouptivity.firebase.models.recyclerViewAdapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    @NonNull
    @Override
    public GroupMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.groups_recyclerview_row, parent, false);
        GroupMessageViewHolder viewHolder = new GroupMessageViewHolder(view);
        if (mItemClickListener != null) {
            viewHolder.setClickListener(mItemClickListener);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupMessageViewHolder holder, int position) {
        String groupName = mGroupMessageList.get(position).getName();
        holder.setGroupMessageName(groupName);

        // Alternate background colors on list
        if (position % 2 == 0){
            holder.itemView.setBackgroundColor(Color.parseColor("#FF0000"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#0000FF"));
        }
    }

    @Override
    public int getItemCount() {
        return mGroupMessageList.size();
    }



    /** Returns the group message at a given position in the RecyclerView. */
   public GroupMessage getItem(int position) {
        return mGroupMessageList.get(position);
   }

   /** Allows click events to be caught. */
   public void setClickListener(ItemClickListener itemClickListener) {
       this.mItemClickListener = itemClickListener;
   }



}
