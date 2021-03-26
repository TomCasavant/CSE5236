package com.group9.grouptivity.firebase.models;


import java.util.ArrayList;
import java.util.List;

/** A Group Message model consisting of a key, name, list of members, and a list of invites. */
public class CompleteGroupMessage extends GroupMessage {
    private List<GroupMessageMember> mGroupMessageMemberList;
    private List<KeyedDataModel> mInvitedUserAccountKeysList;

    private CompleteGroupMessage() {
        this.mGroupMessageMemberList = new ArrayList<>();
        this.mInvitedUserAccountKeysList = new ArrayList<>();
    } //Empty constructor needed for Firebase

    public CompleteGroupMessage(String name) {
        super(name);
        this.mGroupMessageMemberList = new ArrayList<>();
        this.mInvitedUserAccountKeysList = new ArrayList<>();
    }

    public CompleteGroupMessage(String name, List<GroupMessageMember> groupMessageMemberList, List<KeyedDataModel> invitedUserAccountKeysList) {
        super(name);
        this.mGroupMessageMemberList = groupMessageMemberList;
        this.mInvitedUserAccountKeysList = invitedUserAccountKeysList;
    }

    public CompleteGroupMessage(GroupMessage groupMessage) {
        super(groupMessage);
        this.mGroupMessageMemberList = new ArrayList<>();
        this.mInvitedUserAccountKeysList = new ArrayList<>();
    }

    public CompleteGroupMessage(GroupMessage groupMessage, List<GroupMessageMember> groupMessageMemberList, List<KeyedDataModel> invitedUserAccountKeysList) {
        super(groupMessage);
        this.mGroupMessageMemberList = groupMessageMemberList;
        this.mInvitedUserAccountKeysList = invitedUserAccountKeysList;
    }

    /** Returns the list of members in this GroupMessage. */
    public List<GroupMessageMember> getMembers() {
        return this.mGroupMessageMemberList;
    }

    /** Sets the list of members in this GroupMessage. */
    public void setMembers(List<GroupMessageMember> groupMessageMemberList) {
        this.mGroupMessageMemberList = groupMessageMemberList;
    }

    /** Returns the list of keys to users with pending invites to the GroupMessage. */
    public List<KeyedDataModel> getInvites() {
        return this.mInvitedUserAccountKeysList;
    }

    /** Sets the list of keys to users with pending invites to the GroupMessage. */
    public void setInvites(List<KeyedDataModel> invitedUserAccountKeysList) {
        this.mInvitedUserAccountKeysList = invitedUserAccountKeysList;
    }

    public void addMember(GroupMessageMember groupMessageMember) {
        mGroupMessageMemberList.add(groupMessageMember);
    }

    public void addGroupMessageInvite(String inviteKey) {
        this.mInvitedUserAccountKeysList.add(new KeyedDataModel(inviteKey));
    }

    /** Clears the group message member and invites lists. */
    public void clear() {
        this.mInvitedUserAccountKeysList.clear();
        this.mGroupMessageMemberList.clear();
    }
}
