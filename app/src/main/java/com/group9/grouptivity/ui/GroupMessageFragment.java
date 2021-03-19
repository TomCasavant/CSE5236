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
import androidx.navigation.fragment.NavHostFragment;

import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.models.GroupMessage;

import java.security.acl.Group;

public class GroupMessageFragment extends Fragment {
    private View view;
    private Button backButton;
    private GroupMessage mGroupMessage;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        backButton = view.findViewById(R.id.invite_back_button);
        backButton.setOnClickListener((View v) -> {
            NavHostFragment.findNavController(GroupMessageFragment.this)
                    .navigate(R.id.action_GroupInvitesFragment_to_GroupsFragment);
        });
    }
}