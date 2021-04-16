package com.group9.grouptivity.firebase.models.recyclerViewAdapters.viewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.FirebaseRTDBHelper;
import com.group9.grouptivity.firebase.models.ActivityPollMessage;
import com.group9.grouptivity.firebase.models.GroupActivity;
import com.squareup.picasso.Picasso;

//TODO
public class ActivityPollMessageViewHolder extends AbstractMessageViewHolder {

    private TextView placeType;
    private TextView placeName;
    private TextView placeAddress;
    private TextView upvote_count;
    private TextView downvote_count;
    private ImageView icon;
    private String activityId;
    private String groupId;

    /* Sets all the views to their associated variables for later use */
    public ActivityPollMessageViewHolder(@NonNull View itemView) {
        super(itemView);

        // Get all the attribute views
        this.placeType = itemView.findViewById(R.id.place_type);
        this.placeName = itemView.findViewById(R.id.place_name);
        this.placeAddress = itemView.findViewById(R.id.place_address);

        // Get the voting views
        this.upvote_count = itemView.findViewById(R.id.upvote_count);
        this.downvote_count = itemView.findViewById(R.id.downvote_count);

        // Gets the image associated with this item
        this.icon = itemView.findViewById(R.id.icon);

        //Manage click listeners
        itemView.setOnClickListener(this);
        itemView.findViewById(R.id.upvote).setOnClickListener(this);
        itemView.findViewById(R.id.downvote).setOnClickListener(this);
    }

    public void onClick(View v){
        // Handle vote presses
        int id = v.getId();
        if (id == R.id.upvote){
            // Cast vote for 'Yes'
            FirebaseRTDBHelper.getInstance().vote(true, this.activityId, this.groupId);
        } else if (id == R.id.downvote){
            // Cast vote for 'No'
            FirebaseRTDBHelper.getInstance().vote(false, this.activityId, this.groupId);
        }
    }

    /** Sets all the attributes to their corresponding text or image values */
    public void setAttributes(String placeType, String placeName, String placeAddress, int upvote_count, int downvote_count, String icon, String activityId, String groupId){
        // Set the text elements of each attribute
        this.placeType.setText(placeType);
        this.placeName.setText(placeName);
        this.placeAddress.setText(placeAddress);
        this.upvote_count.setText("Votes: " + upvote_count);
        this.downvote_count.setText("Votes: " + downvote_count);

        // Save necessary information about the activity and group this element is associated with it
        this.activityId = activityId;
        this.groupId = groupId;
        Picasso.get().load(icon).into(this.icon); // Load and set the image associatd with this activity
    }

    /** Assigns the buttons and attaches an event listener to change the color of buttons once a vote is cast */
    private void set_buttons(){
        Button upVoteButton = itemView.findViewById(R.id.upvote);
        Button downVoteButton = itemView.findViewById(R.id.downvote);
        FirebaseRTDBHelper.getInstance().getVote(this.activityId, this.groupId, upVoteButton, downVoteButton);
    }

    /** Binds an activityPollMessage to the viewholder. */
    public void bindMessage(ActivityPollMessage activityPollMessage) {
        super.bindAbstractMessageData(activityPollMessage);
        GroupActivity activity = activityPollMessage.getActivity();
        this.setAttributes(activity.getActivityType(), activity.getActivityName(), activity.getActivityAddress(), activityPollMessage.yesVotesCount(), activityPollMessage.noVotesCount(), activity.getActivityIcon(), activity.getActivityId(), activity.getGroupId());
        set_buttons();
    }

}
