package com.group9.grouptivity.firebase.models.recyclerViewAdapters.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.models.ActivityPollMessage;
import com.group9.grouptivity.firebase.models.TextMessage;

//TODO
public class ActivityPollMessageViewHolder extends AbstractMessageViewHolder {

    public ActivityPollMessageViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    public void bindMessage(ActivityPollMessage activityPollMessage) {
        super.bindAbstractMessageData(activityPollMessage);
    }

}
