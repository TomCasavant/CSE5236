package com.group9.grouptivity.firebase;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.models.GroupMessage;
import com.group9.grouptivity.firebase.models.GroupMessageMember;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class FirebaseRTDBHelper {
    private static FirebaseRTDBHelper instance = new FirebaseRTDBHelper();
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    /* Strings used for RTDB I/O. */
    private static final String GROUP_MESSAGES_STR = "groupMessages";
    private static final String USER_ACCOUNTS_STR = "userAccounts";

    //private constructor for singleton
    private FirebaseRTDBHelper() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    public static FirebaseRTDBHelper getInstance() { return instance; }

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

    /* Function to login an existing user given the username and password */
    public void login(String username, String password, Activity activity){
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("FirebaseAuth:", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FirebaseAuth:", "signInWithEmail:failure", task.getException());
                        }

                    }
                });
    }

    /* Function to create the account of a user given a username and password */
    public void createAccount(String username, String password, Activity activity){
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("FirebaseAuth:", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FirebaseAuth", "createUserWithEmail:failure", task.getException());
                        }
                    }
                });

    }

}
