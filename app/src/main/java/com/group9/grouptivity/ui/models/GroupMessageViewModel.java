package com.group9.grouptivity.ui.models;

import androidx.lifecycle.ViewModel;

import com.group9.grouptivity.firebase.models.GroupMessage;

public class GroupMessageViewModel extends ViewModel {
    private GroupMessage mGroupMessage;

    /*** Gets the GroupMessage stored in this ViewModel. */
    public GroupMessage getGroupMessage() {
        return mGroupMessage;
    }

    /**
     * Sets the GroupMessage to store in this ViewModel.
     */
    public void setGroupMessage(GroupMessage groupMessage) {
        this.mGroupMessage = groupMessage;
    }
}
