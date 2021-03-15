package com.group9.grouptivity.firebase.models;

public class GroupActivity {
    private String mActivityType; //Maybe use an enum?
    private double mExpectedCost;
    private String mLocation;

    public GroupActivity(String activityType, double expectedCost, String location) {
        this.mActivityType = activityType;
        this.mExpectedCost = expectedCost;
        this.mLocation = location;
    }

    /** Returns the type of activity that this is. */
    private String getActivityType() {
        return this.mActivityType;
    }

    /** Returns the expected cost associated with this activity. */
    private double getExpectedCost() {
        return  this.mExpectedCost;
    }

    /** Returns the location associated with this activity. */
    private String getLocation() {
        return this.mLocation;
    }
}
