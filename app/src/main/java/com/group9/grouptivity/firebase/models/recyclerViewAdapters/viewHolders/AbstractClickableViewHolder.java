package com.group9.grouptivity.firebase.models.recyclerViewAdapters.viewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group9.grouptivity.firebase.ItemClickListener;

public abstract class AbstractClickableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ItemClickListener mItemClickListener;

    protected AbstractClickableViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void onClick(View view) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    /**
     * Allows click events to be caught.
     */
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

}
