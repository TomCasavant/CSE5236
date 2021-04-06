package com.group9.grouptivity.firebase;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group9.grouptivity.firebase.models.AbstractMessage;
import com.group9.grouptivity.firebase.models.ActivityPollMessage;
import com.group9.grouptivity.firebase.models.CompleteGroupMessage;
import com.group9.grouptivity.firebase.models.CompleteUserAccount;
import com.group9.grouptivity.firebase.models.GroupActivity;
import com.group9.grouptivity.firebase.models.GroupMessage;
import com.group9.grouptivity.firebase.models.GroupMessageInvite;
import com.group9.grouptivity.firebase.models.GroupMessageMember;
import com.group9.grouptivity.firebase.models.TextMessage;
import com.group9.grouptivity.firebase.models.UserAccount;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.PriorityQueue;

public class FirebaseRTDBHelper {
    private static FirebaseRTDBHelper instance = new FirebaseRTDBHelper();
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DatabaseReference mCurrentUserRef;
    private UserAccount mCurrentUser;

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
    private static final String MESSAGE_STR = "messages";
    private static final String TEXT_STR = "texts";
    private static final String ACTIVITY_POLL_STR = "activityPolls";
    private static final String YES_VOTE_STR = "yesVotes";
    private static final String NO_VOTE_STR = "noVotes";
    private static final String USERNAME_STR = "username";
    private static final String INVITE_SENDER_STR = "sender";
    private static final String INVITE_GROUP_MESSAGE_STR = "groupMessageName";
    private static final String DISPLAY_NAME_STR = "displayName";
    private static final String ACTIVITY_STR = "activity";
    private static final String TIMESTAMP_STR = "timeStamp";

    private static final int MILLISECONDS_TO_SECONDS = 1000;

    //private constructor for singleton
    private FirebaseRTDBHelper() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if (mAuth.getCurrentUser() != null) {
            mCurrentUserRef = mDatabase.child(USER_ACCOUNTS_STR).child(mAuth.getCurrentUser().getUid());
            mCurrentUser = new UserAccount(mAuth.getCurrentUser().getEmail(), mAuth.getCurrentUser().getDisplayName());
            mCurrentUser.setKey(mAuth.getCurrentUser().getUid());
        }
    }

    public static FirebaseRTDBHelper getInstance() { return instance; }

    /** Adds a Group Message with the given name to the realtime firebase database. */
    public void addGroupMessage(String groupName) {
        addGroupMessage(new GroupMessage(groupName));
    }

    /** Adds a Group Message with the given name to the realtime firebase database. */
    public void addGroupMessage(GroupMessage groupMessage) {
        if (mAuth.getCurrentUser() != null) {
            DatabaseReference newGroup = mDatabase.child(GROUP_MESSAGES_STR).push();
            newGroup.setValue(groupMessage);
            addCurrentUserToGroupMessage(newGroup.getKey(), groupMessage.getName());

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
                        gm.setKey(child.getKey());
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
            mCurrentUser.setGroups(groupMessageList);
        } else {
            Log.e(LOG_TAG,"Unable to retrieve current user. Is one logged in?");
        }

        return groupMessageList;
    }

    /** Returns a CompleteGroupMessage containing a list of users and all pending invites
     * associated with the given GroupMessage. */
    public CompleteGroupMessage completeGroupMessage(GroupMessage groupMessage, DataRetrievalListener dataRetrievalListener) {
        CompleteGroupMessage completeGroupMessage = new CompleteGroupMessage(groupMessage);
        DatabaseReference groupMessageRef = mDatabase.child(GROUP_MESSAGES_STR).child(groupMessage.retrieveKey());

            ValueEventListener groupMessageListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    completeGroupMessage.clear(); //Need to clear lists to prevent duplicates
                    for (DataSnapshot child : snapshot.child(GROUP_USERS_STR).getChildren()) {
                        GroupMessageMember member = child.getValue(GroupMessageMember.class);
                        member.setKey(child.getKey());
                        completeGroupMessage.addMember(member);
                    }
                    for (DataSnapshot child : snapshot.child(INVITES_STR).getChildren()) {
                        completeGroupMessage.addGroupMessageInvite(child.getKey());
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
            groupMessageRef.addValueEventListener(groupMessageListener);

        return completeGroupMessage;
    }

    /** Sends the given invite to the user with the given UID key.*/
    public void sendInviteToUser(String userKey, GroupMessageInvite invite) {
        //Need to add pending invite to the group message
        mDatabase.child(GROUP_MESSAGES_STR).child(invite.retrieveGroupMessageId()).child(INVITES_STR).child(userKey).setValue(false);
        //Need to add invite to the user
        DatabaseReference userInviteRef = mDatabase.child(USER_ACCOUNTS_STR).child(userKey).child(INVITES_STR).child(invite.retrieveGroupMessageId());
        userInviteRef.setValue(invite);
    }

    /** Adds the current user to the Group Message with the given id and name. */
    public void addCurrentUserToGroupMessage(String groupMessageId, String groupMessageName) {
        if (mCurrentUserRef != null) {
            DatabaseReference groupMessageRef = mDatabase.child(GROUP_MESSAGES_STR).child(groupMessageId);
            if (groupMessageRef != null) {
                //Add the user's info under the group message
                DatabaseReference user = groupMessageRef.child(GROUP_USERS_STR).child(mAuth.getCurrentUser().getUid());
                user.child(EMAIL_STR).setValue(mAuth.getCurrentUser().getEmail());
                user.child(DISPLAY_NAME_STR).setValue(mCurrentUser.getDisplayName());
                user.child(MUTED_STR).setValue(false);


                //Add the group message to the userAccount
                mCurrentUserRef.child(GROUP_MESSAGES_STR).child(groupMessageId).child(GROUP_NAME_STR).setValue(groupMessageName);

                //Remove a pending invite should one exist
                DatabaseReference inviteRef = groupMessageRef.child(INVITES_STR).child(mCurrentUser.retrieveKey());
                if (inviteRef != null) {
                    inviteRef.removeValue();
                }

            } else {
                Log.e(LOG_TAG, "Unable to retrieve Group Message with the given id.");
            }
        } else {
            Log.e(LOG_TAG,"Unable to retrieve current user. Is one logged in?");
        }
    }

    /** Removes the current user to the Group Message with the given id and name. */
    public void removeCurrentUserFromGroupMessage(String groupMessageId) {
        if (mCurrentUserRef != null) {
            DatabaseReference groupMessageRef = mDatabase.child(GROUP_MESSAGES_STR).child(groupMessageId);
            if (groupMessageRef != null) {
                //Remove the user's info under the group message
                DatabaseReference user = groupMessageRef.child(GROUP_USERS_STR).child(mAuth.getCurrentUser().getUid());
                if (user != null) {
                    user.removeValue();
                } else {
                    Log.e(LOG_TAG, "User is not a part of the given group message.");
                }

                //Remove the group message from the userAccount
                DatabaseReference groupMessage = mCurrentUserRef.child(GROUP_MESSAGES_STR).child(groupMessageId);
                if (groupMessage != null) {
                    groupMessage.removeValue();
                } else {
                    Log.e(LOG_TAG, "Group message with given id was not defined under userAccount.");
                }

            } else {
                Log.e(LOG_TAG, "Unable to retrieve Group Message with the given id.");
            }
        } else {
            Log.e(LOG_TAG,"Unable to retrieve current user. Is one logged in?");
        }
    }

    /** Changes a the Group Message with the given id to the given name. If no such Group Message exists, nothing occurs. */
    public void changeGroupMessageName(String groupMessageId, String newGroupMessageName) {

        DatabaseReference groupMessageRef = mDatabase.child(GROUP_MESSAGES_STR).child(groupMessageId);

        if (groupMessageRef != null) {
            ValueEventListener groupMessageListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String groupMessageId = snapshot.getKey();
                    String groupMessageName = (String) snapshot.child(GROUP_NAME_STR).getValue();
                    for (DataSnapshot child : snapshot.child(GROUP_USERS_STR).getChildren()) {
                        DatabaseReference userRef = mDatabase.child(USER_ACCOUNTS_STR).child(child.getKey());
                        userRef.child(GROUP_MESSAGES_STR).child(groupMessageId).child(GROUP_NAME_STR).setValue(groupMessageName);
                        //TODO also update invite names
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(LOG_TAG, error.getMessage());
                    //Toast.makeText()
                    //need context for error to toast
                }
            };

            groupMessageRef.addValueEventListener(groupMessageListener);
            groupMessageRef.child(GROUP_NAME_STR).setValue(newGroupMessageName);
        } else {
            Log.e(LOG_TAG, "Unable to retrieve Group Message with the given id.");
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
            //Remove invite from userAccount info
            DatabaseReference gmInviteRef = mCurrentUserRef.child(INVITES_STR).child(groupMessageInviteId);
            if (gmInviteRef != null){
                gmInviteRef.removeValue();
            } else {
                Log.e(LOG_TAG, "Could not fetch a groupMessageInvite with the given id.");
            }

            //Remove invite from the groupMessage info
            DatabaseReference inviteRef = mDatabase.child(GROUP_MESSAGES_STR).child(groupMessageInviteId).child(INVITES_STR).child(mCurrentUser.retrieveKey());
            if (inviteRef != null ) {
                inviteRef.removeValue();
            }
        } else {
            Log.e(LOG_TAG,"Unable to retrieve user. Is one logged in?");
        }
    }

    /** Adds a text message with the given string as a message body with the cureent user as the sender
     *  to the database under the group message with the given with current time as the timestamp. */
    public void addTextMessage(String messageBody, String groupMessageKey) {
        long timestamp = Calendar.getInstance().getTimeInMillis() / MILLISECONDS_TO_SECONDS;
        Log.d("Display", mAuth.getCurrentUser().getDisplayName());
        addMessage(new TextMessage(groupMessageKey, mAuth.getCurrentUser().getDisplayName(), timestamp, messageBody));
    }

    /** Adds the given activityPollmessage to the database. */
    public void addMessage(GroupActivity activity, String groupId) {
        DatabaseReference pollsRef = mDatabase.child(MESSAGE_STR).child(groupId).child(ACTIVITY_POLL_STR);
        DatabaseReference newPollRef = pollsRef.push();
        newPollRef.child(ACTIVITY_STR).setValue(activity);
        newPollRef.child(INVITE_SENDER_STR).setValue(mAuth.getCurrentUser().getDisplayName());
        long timestamp = Calendar.getInstance().getTimeInMillis() / MILLISECONDS_TO_SECONDS;
        newPollRef.child(TIMESTAMP_STR).setValue(timestamp);
    }

    /** Adds the given textmessage to the database. */
    public void addMessage(TextMessage textMessage) {
        DatabaseReference gmTextRef = mDatabase.child(MESSAGE_STR).child(textMessage.retrieveGroupMessageKey()).child(TEXT_STR);
        DatabaseReference textMessageRef = gmTextRef.push();
        textMessageRef.setValue(textMessage);
    }

    /** Adds the given message to the Firebase database. */
    public void addMessage(AbstractMessage message) {
        message.addMessageToRTDB();
    }

    /** Gets the messages corresponding to the given GroupMessageKey sorted in reverse order by timestamp. */
    public List<AbstractMessage> getMessages(String groupMessageKey, DataRetrievalListener dataRetrievalListener) {
        ArrayList<AbstractMessage> messagesList = new ArrayList<>();

        DatabaseReference groupMessageRef = mDatabase.child(MESSAGE_STR).child(groupMessageKey);
        if(groupMessageRef != null) {
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    messagesList.clear(); //Need to clear the original queue to prevent duplicates
                    PriorityQueue<AbstractMessage> sortedMessages = new PriorityQueue<>();
                    for (DataSnapshot child : snapshot.child(TEXT_STR).getChildren()) {
                        TextMessage textMessage = child.getValue(TextMessage.class);
                        textMessage.setKey(child.getKey());
                        sortedMessages.add(textMessage);
                    }
                    for (DataSnapshot child : snapshot.child(ACTIVITY_POLL_STR).getChildren()) {
                        String id = child.getKey();
                        ActivityPollMessage activityPollMessage = child.getValue(ActivityPollMessage.class);
                        activityPollMessage.getActivity().setActivityId(id);
                        activityPollMessage.getActivity().setGroupId(groupMessageKey);

                        Log.d("Activity Poll: ", activityPollMessage.getActivity().toString());
                        activityPollMessage.setKey(child.getKey());
                        sortedMessages.add(activityPollMessage);
                    }
                    //Move the sorted message into the list in order
                    while (!sortedMessages.isEmpty()) {
                        messagesList.add(sortedMessages.remove());
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
            groupMessageRef.addValueEventListener(valueEventListener);
        } else {
            Log.e(LOG_TAG, "Unable to retrieve messages for Group Message with the given id.");
        }


        return messagesList;
    }

    /** Returns a UserAccount with the given email, or null if no userAccount with the given email
     * is found on the firebase RTDB. */
    public CompleteUserAccount getUserByEmail(String email, DataRetrievalListener dataRetrievalListener) {
        CompleteUserAccount userAccount = new CompleteUserAccount();
        mDatabase.child(USER_ACCOUNTS_STR).get().addOnCompleteListener((Task<DataSnapshot> task) -> {
                //Iterate over all the users, until one is found to match the given email
                for (DataSnapshot user:
                     task.getResult().getChildren()) {
                    if (user.child(EMAIL_STR).getValue().equals(email)) {
                        userAccount.setDisplayName((String) user.child(DISPLAY_NAME_STR).getValue());
                        userAccount.setEmailAddress(email);
                        for (DataSnapshot gmSnap : user.child(GROUP_MESSAGES_STR).getChildren()) {
                            GroupMessage gm = gmSnap.getValue(GroupMessage.class);
                            gm.setKey(gmSnap.getKey());
                            userAccount.getGroupMessages().add(gm);
                        }

                        for (DataSnapshot gmInvSnap : user.child(INVITES_STR).getChildren()) {
                            GroupMessageInvite gmInv = gmInvSnap.getValue(GroupMessageInvite.class);
                            gmInv.setGroupMessageId(gmInvSnap.getKey());
                            userAccount.getInvites().add(gmInv);
                        }
                        userAccount.setKey(user.getKey());
                        break;
                    }
                }
                dataRetrievalListener.onDataRetrieval();
        });
        return userAccount;
    }

    /** Checks if the user is logged in */
    public boolean isLoggedIn(){  return mAuth.getCurrentUser() != null;  }

    /** Adds a New User with the given username to the realtime firebase database. */
    public void addNewUser(UserAccount user, String uid) {
        DatabaseReference account = mDatabase.child(USER_ACCOUNTS_STR).child(uid);
        account.setValue(user);
    }

    /** Function to login an existing user given the username and password */
    public void login(String username, String password, Activity activity){
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(activity, (Task<AuthResult> task) -> {
                        if (task.isSuccessful()) {
                            mCurrentUserRef = mDatabase.child(USER_ACCOUNTS_STR).child(mAuth.getCurrentUser().getUid());
                            //TODO tweak to include display name
                            mCurrentUser = new UserAccount(mAuth.getCurrentUser().getEmail());
                            mCurrentUser.setKey(mAuth.getCurrentUser().getUid());
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(LOG_TAG, "signInWithEmail:success");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(LOG_TAG, "signInWithEmail:failure", task.getException());
                        }
                });
    }

    /** Function to create the account of a user given a username and password */
    public void createAccount(String email, String password, String nickname, Activity activity){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, (Task<AuthResult> task) ->  {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(LOG_TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(nickname).build();
                            mAuth.getCurrentUser().updateProfile(profileUpdates);
                            this.mCurrentUser = new UserAccount(email, nickname);
                            this.mCurrentUser.setKey(user.getUid());
                            addNewUser(this.mCurrentUser, this.mCurrentUser.retrieveKey());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(LOG_TAG, "createUserWithEmail:failure", task.getException());
                        }
                });
    }

    public void logout(){
        //TODO RESET Current User
        mAuth.signOut();
    }
    /**
        Pushes a vote to the database at the corresponding activity, and removes the vote from the other category if necessary
        choice - boolean, true if voting 'yes' and false if voting 'no'
    */
    public void vote(boolean choice, String activity_id, String group_id){
        DatabaseReference activity = mDatabase.child(MESSAGE_STR).child(group_id).child(ACTIVITY_POLL_STR).child(activity_id);
        if (choice){
            // Yes Vote
            activity.child(YES_VOTE_STR).child(mAuth.getUid()).setValue(mAuth.getCurrentUser().getEmail());
            // Delete No Vote if exists
            activity.child(NO_VOTE_STR).child(mAuth.getUid()).removeValue();
        } else {
            // No Vote
            activity.child(NO_VOTE_STR).child(mAuth.getUid()).setValue(mAuth.getCurrentUser().getEmail());
            // Delete Yes Vote if exists
            activity.child(YES_VOTE_STR).child(mAuth.getUid()).removeValue();
        }
    }

    /**
        Checks if a vote has been cast by the user, if it has adjust the UI to gray out the button they didn't choose
    */
    public void getVote(String activity_id, String group_id, Button upvote, Button downvote){
        DatabaseReference activity = mDatabase.child(MESSAGE_STR).child(group_id).child(ACTIVITY_POLL_STR).child(activity_id);

        activity.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean upvoted = snapshot.child(YES_VOTE_STR).hasChild(mAuth.getCurrentUser().getUid());
                boolean downvoted = snapshot.child(NO_VOTE_STR).hasChild(mAuth.getCurrentUser().getUid());
                if (upvoted){
                    // user voted yes
                    downvote.setBackgroundColor(Color.GRAY);
                    upvote.setBackgroundColor(Color.parseColor("#FFBB86FC"));
                } else {
                    if (downvoted) {
                        // User voted no
                        upvote.setBackgroundColor(Color.GRAY);
                        downvote.setBackgroundColor(Color.parseColor("#FFBB86FC"));
                    } else{
                        // User has not voted
                        upvote.setBackgroundColor(Color.parseColor("#FFBB86FC"));
                        downvote.setBackgroundColor(Color.parseColor("#FFBB86FC"));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
    public void createListener(AuthStateListener listener){
        mAuth.addAuthStateListener(listener);
    }

    /** Returns a UserAccount representing the current user. */
    public UserAccount getCurrentUser() {
        return this.mCurrentUser;
    }


}
