package com.group9.grouptivity.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.DataRetrievalListener;
import com.group9.grouptivity.firebase.FirebaseRTDBHelper;
import com.group9.grouptivity.firebase.models.CompleteGroupMessage;
import com.group9.grouptivity.firebase.models.CompleteUserAccount;
import com.group9.grouptivity.firebase.models.GroupMessageInvite;
import com.group9.grouptivity.firebase.models.recyclerViewAdapters.MessageAdapter;
import com.group9.grouptivity.ui.models.GroupMessageViewModel;

public class GroupMessageFragment extends Fragment {
    private View view;
    private Button backButton;
    private Button sendButton;
    private Button sendInviteButton;
    private EditText messageEditText;
    private CompleteGroupMessage mCompleteGroupMessage;
    private TextView groupMessageNameTextView;
    private MessageAdapter messageAdapter;

    //Needed for inviting. Ideally would be block scope but couldn't find a simple solution
    private CompleteUserAccount mInvitedUser;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        view = inflater.inflate(R.layout.group_message_fragment, container, false);

        // Inflate the layout for this fragment
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GroupMessageViewModel gmViewModel = new ViewModelProvider(requireActivity()).get(GroupMessageViewModel.class);
        this.mCompleteGroupMessage = FirebaseRTDBHelper.getInstance().completeGroupMessage(gmViewModel.getGroupMessage(), new DataRetrievalListener() {
            @Override
            public void onDataRetrieval() {
                //TODO will need to update users recyclerview
            }
        });

        groupMessageNameTextView = view.findViewById(R.id.group_message_banner);
        groupMessageNameTextView.setText(this.mCompleteGroupMessage.getName());

        backButton = view.findViewById(R.id.invite_back_button);
        backButton.setOnClickListener((View v) ->
            NavHostFragment.findNavController(GroupMessageFragment.this)
                    .navigate(R.id.action_GroupMessageFragment_to_GroupsFragment)
        );

        sendInviteButton = view.findViewById(R.id.message_invite_button);
        sendInviteButton.setOnClickListener((View v) -> createSendInviteDialog());

        messageEditText = view.findViewById(R.id.message_edittext);

        sendButton = view.findViewById(R.id.message_send_button);
        sendButton.setOnClickListener((View v) -> {
            String messageBody = messageEditText.getText().toString();
            if (!messageBody.isEmpty()) {
                messageEditText.setText("");
                FirebaseRTDBHelper.getInstance().addTextMessage(messageBody, this.mCompleteGroupMessage.retrieveKey());
            }
        });

        buildGroupMessageInviteRecyclerView(view);
    }

    /** Builds the recycler view to display the messages in this GroupMessage. */
    public void buildGroupMessageInviteRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.message_recyclerview);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        DataRetrievalListener dataRetrievalListener = () -> messageAdapter.notifyDataSetChanged();
        messageAdapter = new MessageAdapter(getActivity(), FirebaseRTDBHelper.getInstance().getMessages(this.mCompleteGroupMessage.retrieveKey(), dataRetrievalListener));
        recyclerView.setAdapter(messageAdapter);
    }

    /** Creates a dialog box and displays it to the user to send an invite to another user. */
    public void createSendInviteDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.invite_user_dialog); // Assign to xml dialog layout
        TextView titleText = dialog.findViewById(R.id.invite_dialog_title);
        EditText emailEditText = dialog.findViewById(R.id.invite_dialog_editText);
        Button sendButton = dialog.findViewById(R.id.invite_dialog_send_button);
        Button cancelButton = dialog.findViewById(R.id.dialogButtonCancel);
        sendButton.setOnClickListener((View v) -> {
            sendInvite(emailEditText.getText().toString(), emailEditText);
        });
        cancelButton.setOnClickListener((View v) ->
                dialog.dismiss()
        );

        dialog.show();
    }

    public void sendInvite(String email, EditText emailEditText) {

        DataRetrievalListener drl = new DataRetrievalListener() {
            @Override
            public void onDataRetrieval() {
                String toastText;
                if (mInvitedUser.getDisplayName().equals("null")) {
                    toastText = "Unable to find user with email " + email;
                } else if (mCompleteGroupMessage.containsMember(email)) {
                    toastText = "User " + mInvitedUser.getDisplayName() + " is already in this group.";
                } else if (mCompleteGroupMessage.containsInvite(mInvitedUser.retrieveKey())) {
                    toastText = "User " + mInvitedUser.getDisplayName() + " already has a pending invite to this group.";
                } else { //Invite is valid
                    toastText = "Successfully sent invite to user " + mInvitedUser.getDisplayName() + " to the group.";
                    String senderName = FirebaseRTDBHelper.getInstance().getCurrentUser().getDisplayName();
                    GroupMessageInvite invite = new GroupMessageInvite(mCompleteGroupMessage, senderName);
                    FirebaseRTDBHelper.getInstance().sendInviteToUser(mInvitedUser.retrieveKey(), invite);
                    emailEditText.setText("");
                }
                Toast.makeText(getActivity(), toastText, Toast.LENGTH_LONG).show();

            }
        };
        mInvitedUser = FirebaseRTDBHelper.getInstance().getUserByEmail(email, drl);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("OnStart()", "Successfully ran OnStart() in Fragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("OnPause()", "Successfully ran OnPause()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("OnResume()", "Successfully ran OnResume() in Fragment");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("OnStop()", "Successfully ran OnStop() in Fragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("OnDestroy()", "Successfully ran OnDestroy() in Fragment");
    }
}