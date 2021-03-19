package com.group9.grouptivity.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.DataRetrievalListener;
import com.group9.grouptivity.firebase.models.GroupMessageInvite;
import com.group9.grouptivity.firebase.models.GroupMessageInviteAdapter;

import java.util.ArrayList;
import java.util.List;

//TODO this class
public class GroupInvitesFragment extends Fragment {
    private View view;
    private Button backButton;
    private GroupMessageInviteAdapter groupMessageInviteAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        //Inflate the view for this fragment
        view = inflater.inflate(R.layout.group_invites_fragment, container, false);



        buildGroupMessageRecyclerView(view);

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backButton = view.findViewById(R.id.invite_back_button);
        backButton.setOnClickListener((View v) -> {
            NavHostFragment.findNavController(GroupInvitesFragment.this)
                    .navigate(R.id.action_GroupInvitesFragment_to_GroupsFragment);
        });
    }

    /** Builds the recycler view to display the list of Group Messages the user is a part of. */
    public void buildGroupMessageRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.group_message_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DataRetrievalListener dataRetrievalListener = new DataRetrievalListener() {
            @Override
            public void onDataRetrieval() {
                groupMessageInviteAdapter.notifyDataSetChanged();
            }
        };
        List<GroupMessageInvite> gmList = new ArrayList<>();
        gmList.add(new GroupMessageInvite("Group1", "Adam"));
        gmList.add(new GroupMessageInvite("Group2", "Beth"));
        groupMessageInviteAdapter = new GroupMessageInviteAdapter(getActivity(), gmList);
        recyclerView.setAdapter(groupMessageInviteAdapter);
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