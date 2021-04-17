package com.group9.grouptivity.firebase.models.recyclerViewAdapters.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.group9.grouptivity.R;

public class SimpleTextViewViewHolder extends AbstractClickableViewHolder {
    private TextView mTextView;

    public SimpleTextViewViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.simple_textview_recyclerview_textview);
    }

    /**
     * Sets the text of the TextView.
     */
    public void setText(String text) {
        this.mTextView.setText(text);
    }

}
