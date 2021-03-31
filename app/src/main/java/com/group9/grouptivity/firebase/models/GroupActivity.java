package com.group9.grouptivity.firebase.models;

public class GroupActivity extends KeyedDataModel {
    private String mActivityType;
    private String mActivityName;
    private String mActivityIcon;
    private String mActivityAddress;
    private String mActivityId;
    private String mGroupId;
    private double mExpectedCost;

    protected GroupActivity() {} //Empty constructor needed for Firebase

    public GroupActivity(String type, String address, String icon, String name){
        this.mActivityType = type;
        this.mActivityAddress = address;
        this.mActivityIcon = icon;
        this.mActivityName = name;
    }

    /** Returns the address of activity */
    public String getActivityAddress(){ return this.mActivityAddress; }

    /** Returns the icon of activity */
    public String getActivityIcon(){ return this.mActivityIcon; }

    /** Returns the name of activity */
    public String getActivityName(){ return this.mActivityName; }

    /** Returns the type of activity that this is. */
    public String getActivityType() {
        return this.mActivityType;
    }

    /** Sets the type of activity that this is. */
    public void getActivityType(String activityType) {
        this.mActivityType = activityType;
    }

    /** Returns the expected cost associated with this activity. */
    private double getExpectedCost() {
        return  this.mExpectedCost;
    }

    /** Sets the expected cost associated with this activity. */
    private void setExpectedCost(double expectedCost) {
        this.mExpectedCost = expectedCost;
    }

    /** Sets the Group ID associated with this activity. */
    public void setGroupId(String id){ this.mGroupId = id; }

    /** Gets the group ID associated with this activity. */
    public String getGroupId(){ return this.mGroupId; }

    /** Sets the activity ID associated with this activity. */
    public void setActivityId(String id){ this.mActivityId = id; }

    /** Gets the activity ID associated with this activity. */
    public String getActivityId(){ return this.mActivityId; }


}
