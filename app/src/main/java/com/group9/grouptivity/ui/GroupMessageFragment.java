package com.group9.grouptivity.ui;

import android.os.Bundle;
import android.view.KeyEvent;
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

import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.models.GroupMessage;
import com.group9.grouptivity.ui.models.GroupMessageViewModel;

import java.security.acl.Group;

public class GroupMessageFragment extends Fragment {
    private View view;
    private Button backButton;
    private GroupMessage mGroupMessage;
    private TextView groupMessageNameTextView;

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
    }
}