package com.group9.grouptivity.firebase.models;

import java.util.ArrayList;
import java.util.List;

public class ActivityPollMessage extends Message {
    private GroupActivity mGroupActivity;
    private List<String> mYesVotesUsernameList; //May want to change these lists to UserAccounts
    private List<String> mNoVotesUsernameList;

    public ActivityPollMessage(GroupMessage groupMessage, String sender, long timeStamp, GroupActivity groupActivity) {
        super(groupMessage, sender, timeStamp);
        this.mGroupActivity = groupActivity;
        this.mYesVotesUsernameList = new ArrayList<>();
        this.mNoVotesUsernameList = new ArrayList<>();
    }

    public ActivityPollMessage(GroupMessage groupMessage, String sender, long timeStamp, GroupActivity groupActivity, List<String> yesVotesUsernameList, List<String> noVotesUsernameList) {
        super(groupMessage, sender, timeStamp);
        this.mGroupActivity = groupActivity;
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
        return this.mYesVotesUsernameList.size();
    }

    /** Returns the number of users who have voted NO on this activity poll. */
    public int noVotesCount() {
        return this.mNoVotesUsernameList.size();
    }
}
