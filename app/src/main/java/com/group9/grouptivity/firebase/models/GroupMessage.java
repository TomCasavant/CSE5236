package com.group9.grouptivity.firebase.models;


import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

public class GroupMessage {
    private String mId;
    private String mName;
    private List<GroupMessageMember> mGroupMessageMemberList;

    private GroupMessage () {
        this.mGroupMessageMemberList = new ArrayList<>();
    } //Empty constructor needed for Firebase

    public GroupMessage(String name) {
        this.mId = "null";
        this.mName = name;
        this.mGroupMessageMemberList = new ArrayList<>();
    }

    public GroupMessage(String name, List<GroupMessageMember> groupMessageMemberList){
        this.mId = "null";
        this.mName = name;
        this.mGroupMessageMemberList = groupMessageMemberList;
    }

    /** Returns the id of the GroupMessage. */
    public void getId(String id) {
        this.mId = id;
    }

    /** Sets the name of the GroupMessage. */
    public void setId(String id) {
        this.mId = id;
    }

    /** Returns the name of the GroupMessage. */
    public String getName() {
        return this.mName;
    }

    /** Sets the name of the GroupMessage. */
    public void setName(String name) {
        this.mName = name;
    }

    /** Returns the list of members in this GroupMessage. */
    public List<GroupMessageMember> getGroupMessageMemberList() {
        return this.mGroupMessageMemberList;
    }

    public void addMember(GroupMessageMember groupMessageMember) {
        mGroupMessageMemberList.add(groupMessageMember);
    }
}
