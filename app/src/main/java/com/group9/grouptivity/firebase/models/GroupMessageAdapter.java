package com.group9.grouptivity.firebase.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.ItemClickListener;

import java.util.List;

public class GroupMessageAdapter extends RecyclerView.Adapter<GroupMessageAdapter.GroupMessageViewHolder> {
    private List<GroupMessage> mGroupMessageList;
    private ItemClickListener mItemClickListener;
    private LayoutInflater mInflater;

    public GroupMessageAdapter(Context context, List<GroupMessage> groupMessageList) {
        this.mGroupMessageList = groupMessageList;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public GroupMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.groups_recyclerview_row, parent, false);

        return new GroupMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupMessageViewHolder holder, int position) {
        String groupName = mGroupMessageList.get(position).getName();
        holder.groupMessageTextView.setText(groupName);
    }

    @Override
    public int getItemCount() {
        return mGroupMessageList.size();
    }

    public class GroupMessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView groupMessageTextView; //not sure ab access modifier here

        public GroupMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            groupMessageTextView = itemView.findViewById(R.id.group_message_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getAdapterPosition());
            }
        }
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
