package com.group9.grouptivity.firebase.models;

public class GroupActivity extends KeyedDataModel {
    private String mActivityType;
    private double mExpectedCost;
    private String mLocation;

    private GroupActivity() {} //Empty constructor needed for Firebase

    public GroupActivity(String activityType, double expectedCost, String location) {
        this.mActivityType = activityType;
        this.mExpectedCost = expectedCost;
        this.mLocation = location;
    }

    /** Returns the type of activity that this is. */
    private String getActivityType() {
        return this.mActivityType;
    }

    /** Sets the type of activity that this is. */
    private void getActivityType(String activityType) {
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

    /** Returns the location associated with this activity. */
    private String getLocation() {
        return this.mLocation;
    }

    /** Sets the location associated with this activity. */
    private void setLocation(String location) {
        this.mLocation = location;
    }

}
