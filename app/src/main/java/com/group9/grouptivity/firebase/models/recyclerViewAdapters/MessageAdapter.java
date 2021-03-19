package com.group9.grouptivity.firebase.models.recyclerViewAdapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.ItemClickListener;
import com.group9.grouptivity.firebase.models.AbstractMessage;
import com.group9.grouptivity.firebase.models.ActivityPollMessage;
import com.group9.grouptivity.firebase.models.GroupMessage;
import com.group9.grouptivity.firebase.models.TextMessage;
import com.group9.grouptivity.firebase.models.recyclerViewAdapters.viewHolders.AbstractMessageViewHolder;
import com.group9.grouptivity.firebase.models.recyclerViewAdapters.viewHolders.ActivityPollMessageViewHolder;
import com.group9.grouptivity.firebase.models.recyclerViewAdapters.viewHolders.TextMessageViewHolder;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<AbstractMessageViewHolder> {
    private List<AbstractMessage> mMessageList;
    private LayoutInflater mInflater;
    private ItemClickListener mItemClickListener;

    public MessageAdapter(Context context, List<AbstractMessage> messageList) {
        this.mMessageList = messageList;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AbstractMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        AbstractMessageViewHolder messageViewHolder = null;
        switch (AbstractMessage.Type.values()[viewType]) { //convert viewType to the right enum
            case TEXT:
                view = mInflater.inflate(R.layout.textmessage_recyclerview_row, parent, false);
                messageViewHolder = new TextMessageViewHolder(view);
                break;
            case ACTIVITY_POLL:
                view = mInflater.inflate(R.layout.activitypoll_recyclerview_row, parent, false);
                messageViewHolder = new ActivityPollMessageViewHolder(view);
                break;
            default:
                //this should never happen
                break;
        }

        if(mItemClickListener != null) {
            messageViewHolder.setClickListener(mItemClickListener);
        }

        return  messageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AbstractMessageViewHolder holder, int position) {
        holder.bindMessage(this.mMessageList.get(position));
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.mMessageList.get(position).getType().ordinal();
    }

    /** Returns the group message at a given position in the RecyclerView. */
   public AbstractMessage getItem(int position) {
        return mMessageList.get(position);
   }

   /** Allows click events to be caught. */
   public void setClickListener(ItemClickListener itemClickListener) {
    this.mItemClickListener = itemClickListener;
   }





}
