package com.group9.grouptivity.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.DataRetrievalListener;
import com.group9.grouptivity.firebase.FirebaseRTDBHelper;
import com.group9.grouptivity.firebase.models.GroupMessage;
import com.group9.grouptivity.firebase.models.recyclerViewAdapters.MessageAdapter;
import com.group9.grouptivity.ui.models.GroupMessageViewModel;

public class GroupMessageFragment extends Fragment {
    private View view;
    private Button backButton;
    private Button sendButton;
    private EditText messageEditText;
    private GroupMessage mGroupMessage;
    private TextView groupMessageNameTextView;
    private MessageAdapter messageAdapter;

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
        this.mGroupMessage = gmViewModel.getGroupMessage();

        groupMessageNameTextView = view.findViewById(R.id.group_message_banner);
        groupMessageNameTextView.setText(this.mGroupMessage.getName());

        backButton = view.findViewById(R.id.invite_back_button);
        backButton.setOnClickListener((View v) -> {
            NavHostFragment.findNavController(GroupMessageFragment.this)
                    .navigate(R.id.action_GroupMessageFragment_to_GroupsFragment);
        });

        messageEditText = view.findViewById(R.id.message_edittext);

        sendButton = view.findViewById(R.id.message_send_button);
        sendButton.setOnClickListener((View v) -> {
            String messageBody = messageEditText.getText().toString();
            if (!messageBody.isEmpty()) {
                messageEditText.setText("");
                FirebaseRTDBHelper.getInstance().addTextMessage(messageBody, this.mGroupMessage.retrieveKey());
            }
        });

        buildGroupMessageInviteRecyclerView(view);
    }

    /** Builds the recycler view to display the messages in this GroupMessage. */
    public void buildGroupMessageInviteRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.message_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DataRetrievalListener dataRetrievalListener = () -> messageAdapter.notifyDataSetChanged();
        messageAdapter = new MessageAdapter(getActivity(), FirebaseRTDBHelper.getInstance().getMessages(mGroupMessage.retrieveKey(), dataRetrievalListener));
        recyclerView.setAdapter(messageAdapter);
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