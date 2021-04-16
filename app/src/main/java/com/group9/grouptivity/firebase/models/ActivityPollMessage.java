package com.group9.grouptivity.firebase.models;

import com.group9.grouptivity.firebase.FirebaseRTDBHelper;
import com.group9.grouptivity.firebase.models.recyclerViewAdapters.viewHolders.AbstractMessageViewHolder;
import com.group9.grouptivity.firebase.models.recyclerViewAdapters.viewHolders.ActivityPollMessageViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActivityPollMessage extends AbstractMessage {
    private GroupActivity mGroupActivity;
    private List<String> mYesVotesUsernameList; //May want to change these lists to UserAccounts
    private List<String> mNoVotesUsernameList;
    private HashMap<String, Object> noVotes;
    private HashMap<String, Object> yesVotes;


    private ActivityPollMessage() {} //Empty constructor needed for Firebase

    public ActivityPollMessage(String groupMessageKey, String sender, long timeStamp, GroupActivity groupActivity) {
        super(groupMessageKey, sender, timeStamp);
        this.mGroupActivity = groupActivity;
        this.mYesVotesUsernameList = new ArrayList<>();
        this.mNoVotesUsernameList = new ArrayList<>();
    }

    public ActivityPollMessage(String groupMessageKey, String sender, long timeStamp, GroupActivity activity, List<String> yesVotesUsernameList, List<String> noVotesUsernameList) {
        super(groupMessageKey, sender, timeStamp);
        this.mGroupActivity = activity;
        this.mYesVotesUsernameList = yesVotesUsernameList;
        this.mNoVotesUsernameList = noVotesUsernameList;

    }

    /** Returns the group activity associated with this activity poll. */
    public GroupActivity getGroupActivity() {
        return mGroupActivity;
    }

    /** Returns the list of users who have voted YES on this activity poll. */
    public List<String> getYesVotesUsernameList() {
        return this.mYesVotesUsernameList;
    }

    /** Returns the list of users who have voted NO on this activity poll. */
    public List<String> getNoVotesUsernameList() {
        return this.mNoVotesUsernameList;
    }

    /** Returns the number of users who have voted YES on this activity poll. */
    public int yesVotesCount() {
        if (this.yesVotes == null) { return 0; }
        return this.yesVotes.size();
    }

    /** Returns the number of users who have voted NO on this activity poll. */
    public int noVotesCount() {
        if (this.noVotes == null) { return 0; }
        return this.noVotes.size();
    }

    /* Gets the noVotes database HashMap */
    public void setNoVotes(HashMap<String, Object> noVotes){
        this.noVotes = noVotes;
    }

    /* Gets the yesVotes database HashMap */
    public void setYesVotes(HashMap<String, Object> yesVotes){
        this.yesVotes = yesVotes;
    }

    /* Gets the activity HashMap */
    public void setActivity(HashMap<String, Object> activity){
        this.mGroupActivity = new GroupActivity((String) activity.get("activityType"), (String) activity.get("activityAddress"), (String) activity.get("activityIcon"), (String) activity.get("activityName"));
    }

    /* Gets the hashmap associated with the Yes Votes from the database */
    public HashMap<String, Object> getYesVotes(){
        return this.yesVotes;
    }

    /* Retrieves and returns the noVotes hashmap */
    public HashMap<String, Object> getNoVotes(){
        return this.noVotes;
    }

    /* Returns the GroupActivity associated with this Poll */
    public GroupActivity getActivity(){
        return this.mGroupActivity;
    }

    @Override
    public Type retrieveType() {
        return Type.ACTIVITY_POLL;
    }

    @Override
    public void bindMessage(AbstractMessageViewHolder holder) {
        ((ActivityPollMessageViewHolder) holder).bindMessage(this);
    }

    @Override
    public void addMessageToRTDB() {
        FirebaseRTDBHelper.getInstance().addMessage(this);
    }
}
