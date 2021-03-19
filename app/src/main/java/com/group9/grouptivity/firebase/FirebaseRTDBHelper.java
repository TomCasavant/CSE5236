package com.group9.grouptivity.firebase;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group9.grouptivity.firebase.models.GroupMessage;
import com.group9.grouptivity.firebase.models.GroupMessageInvite;
import com.group9.grouptivity.firebase.models.GroupMessageMember;
import com.group9.grouptivity.firebase.models.UserAccount;

import java.util.ArrayList;
import java.util.List;

public class FirebaseRTDBHelper {
    private static FirebaseRTDBHelper instance = new FirebaseRTDBHelper();
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DatabaseReference mCurrentUserRef;

    /* Tag for logging. */
    private static final String LOG_TAG = "FirebaseRTDBHelper";

    /* Strings used for RTDB I/O. */
    private static final String GROUP_MESSAGES_STR = "groupMessages";
    private static final String USER_ACCOUNTS_STR = "userAccounts";
    private static final String GROUP_NAME_STR = "name";
    private static final String GROUP_USERS_STR = "users";
    private static final String MUTED_STR = "isMuted";
    private static final String EMAIL_STR = "emailAddress";
    private static final String INVITES_STR = "invites";
    private static final String INVITE_SENDER_STR = "senderUsername";
    private static final String INVITE_GROUP_MESSAGE_STR = "groupMessageName";

    //private constructor for singleton
    private FirebaseRTDBHelper() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if (mAuth != null) {
            mCurrentUserRef = mDatabase.child(USER_ACCOUNTS_STR).child(mAuth.getCurrentUser().getUid());
        }
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
        if (mAuth.getCurrentUser() != null) {
            String id = mDatabase.child(GROUP_MESSAGES_STR).push().getKey();
            DatabaseReference newGroup = mDatabase.child(GROUP_MESSAGES_STR).child(id);
            newGroup.setValue(groupMessage);
            addCurrentUserToGroupMessage(id, groupMessage.getName());
        } else {
            Log.e(LOG_TAG,"Unable to retrieve user. Is one logged in?");
        }
    }

    /** Returns a list group messages of which the current user is a part. */
    public List<GroupMessage> getGroupMessages(DataRetrievalListener dataRetrievalListener) {
        List<GroupMessage> groupMessageList = new ArrayList<>();
        if (mCurrentUserRef != null) {
            DatabaseReference userGroupMessageListRef = mCurrentUserRef.child(GROUP_MESSAGES_STR);
            ValueEventListener userGroupMessageListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    groupMessageList.clear(); //Need to clear the original list to prevent duplicates
                    for (DataSnapshot child : snapshot.getChildren()) {
                        GroupMessage gm = child.getValue(GroupMessage.class);
                        gm.setId(child.getKey());
                        groupMessageList.add(gm);

                    }
                    dataRetrievalListener.onDataRetrieval(); //Notify listener
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(LOG_TAG, error.getMessage());
                    //Toast.makeText()
                    //need context for error to toast
                }
            };
            userGroupMessageListRef.addValueEventListener(userGroupMessageListener);
        } else {
            Log.e(LOG_TAG,"Unable to retrieve current user. Is one logged in?");
        }


        return groupMessageList;
    }

    /** Adds the current user to the Group Message with the given id and name. */
    public void addCurrentUserToGroupMessage(String groupMessageId, String groupMessageName) {
        if (mCurrentUserRef != null) {
            DatabaseReference groupMessageRef = mDatabase.child(GROUP_MESSAGES_STR).child(groupMessageId);
            if (groupMessageRef != null){
                DatabaseReference user = groupMessageRef.child(GROUP_USERS_STR).child(mAuth.getCurrentUser().getUid());
                user.child(EMAIL_STR).setValue(mAuth.getCurrentUser().getEmail());
                user.child(MUTED_STR).setValue(false);
                mCurrentUserRef.child(GROUP_MESSAGES_STR).child(groupMessageId).child(GROUP_NAME_STR).setValue(groupMessageName);
            } else {
                Log.e(LOG_TAG, "Unable to retrieve Group Message with the given id.");
            }
        } else {
            Log.e(LOG_TAG,"Unable to retrieve current user. Is one logged in?");
        }
    }

    /** Gets GroupMessageInvites for the current user. */
    public List<GroupMessageInvite> getGroupMessageInvites(DataRetrievalListener dataRetrievalListener) {
        List<GroupMessageInvite> groupMessageInviteList = new ArrayList<>();
        if (mCurrentUserRef != null) {
            DatabaseReference userGroupMessageInvitesListRef = mCurrentUserRef.child(INVITES_STR);
            ValueEventListener userGroupMessageInvitesListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    groupMessageInviteList.clear(); //Need to clear the original list to prevent duplicates
                    for (DataSnapshot child : snapshot.getChildren()) {
                        GroupMessageInvite gmInv = child.getValue(GroupMessageInvite.class);
                        gmInv.setGroupMessageId(child.getKey());
                        groupMessageInviteList.add(gmInv);

                    }
                    dataRetrievalListener.onDataRetrieval(); //Notify listener
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(LOG_TAG, error.getMessage());
                    //Toast.makeText()
                    //need context for error to toast
                }
            };
            userGroupMessageInvitesListRef.addValueEventListener(userGroupMessageInvitesListener);
        } else {
            Log.e(LOG_TAG,"Unable to retrieve current user. Is one logged in?");
        }


        return groupMessageInviteList;
    }

    /** Deletes the groupMessageInvite with the given id associated with the current user if it exists. */
    public void deleteGroupMessageInvite(String groupMessageInviteId) {
        if (mCurrentUserRef != null) {
            DatabaseReference gmInviteRef = mCurrentUserRef.child(INVITES_STR).child(groupMessageInviteId);
            if (gmInviteRef != null){
                gmInviteRef.removeValue();
            } else {
                Log.e(LOG_TAG, "Could not fetch a groupMessageInvite with the given id.");
            }
        } else {
            Log.e(LOG_TAG,"Unable to retrieve user. Is one logged in?");
        }
    }

    /** Checks if the user is logged in */
    public boolean isLoggedIn(){  return mAuth.getCurrentUser() != null;  }

    /** Adds a New User with the given username to the realtime firebase database. */
    public void addNewUser(UserAccount user, String uid) {
        mDatabase.child(USER_ACCOUNTS_STR).child(uid).setValue(user);
    }

    /** Function to login an existing user given the username and password */
    public void login(String username, String password, Activity activity){
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mCurrentUserRef = mDatabase.child(USER_ACCOUNTS_STR).child(mAuth.getCurrentUser().getUid());
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("FirebaseAuth:", "signInWithEmail:success");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FirebaseAuth:", "signInWithEmail:failure", task.getException());
                        }

                    }
                });
    }

    /** Function to create the account of a user given a username and password */
    public void createAccount(String username, String password, Activity activity){
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("FirebaseAuth:", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            addNewUser(new UserAccount(username), user.getUid());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FirebaseAuth", "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
    }

    public void logout(){
        //TODO RESET Current User
        mAuth.signOut();
    }

    public void createListener(AuthStateListener listener){
        mAuth.addAuthStateListener(listener);
    }



}
