package com.group9.grouptivity.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.models.GroupMessage;
import com.group9.grouptivity.firebase.models.GroupMessageMember;

import java.util.ArrayList;
import java.util.List;

public class FirebaseRTDBHelper {
    private static FirebaseRTDBHelper instance = new FirebaseRTDBHelper();
    private DatabaseReference mDatabase;

    /* Strings used for RTDB I/O. */
    private static final String GROUP_MESSAGES_STR = "groupMessages";
    private static final String USER_ACCOUNTS_STR = "userAccounts";

    //private constructor for singleton
    private FirebaseRTDBHelper() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseRTDBHelper getInstance() {
        return instance;
    }

    /** Adds a Group Message with the given name to the realtime firebase database. */
    public void addGroupMessage(String groupName) {
        addGroupMessage(new GroupMessage(groupName));
    }

    /** Adds a Group Message with the given name to the realtime firebase database. */
    public void addGroupMessage(String groupName, List<GroupMessageMember> groupMessageMemberList) {
        addGroupMessage(new GroupMessage(groupName, groupMessageMemberList));
    }

    /** Adds a Group Message with the given name to the realtime firebase database. */
    public void addGroupMessage(GroupMessage groupMessage) {
        String id = mDatabase.child(GROUP_MESSAGES_STR).push().getKey();
        mDatabase.child(GROUP_MESSAGES_STR).child(id).setValue(groupMessage);
    }

    //TODO finish this jauwnt
    /** Returns a list group messages of which the UserAccount with given username is a part. */
    public List<GroupMessage> getGroupMessages(String username) {
        List<GroupMessage> groupMessageList = new ArrayList<>();
        mDatabase.child(USER_ACCOUNTS_STR).child(username).child(GROUP_MESSAGES_STR).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()){
                    Log.e("firebase","Error getting data", task.getException());

                } else { //task is successful
                    for (DataSnapshot child : task.getResult().getChildren()) {
                        child.getKey();
                    }
                }
            }
        });
        return groupMessageList;
    }

}
