package com.group9.grouptivity.firebase.models;


import java.util.List;

public class GroupMessage {
    public String id;
    private String mName;
    private List<GroupMessageMember> mGroupMessageMemberList;

    public GroupMessage(String name) {
        this.mName = name;
    }

    public GroupMessage(String name, List<GroupMessageMember> groupMessageMemberList){
        this.mName = name;
        this.mGroupMessageMemberList = groupMessageMemberList;
    }

    /** Returns the name of the GroupMessage. */
    public String getName() {
        return this.mName;
    }

    /** Returns the list of members in this GroupMessage. */
    public List<GroupMessageMember> getGroupMessageMemberList() {
        return this.mGroupMessageMemberList;
    }
}
