package com.group9.grouptivity.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group9.grouptivity.firebase.DataRetrievalListener;
import com.group9.grouptivity.firebase.ItemClickListener;
import com.group9.grouptivity.firebase.models.recyclerViewAdapters.GroupMessageAdapter;
import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.FirebaseRTDBHelper;
import com.group9.grouptivity.ui.models.GroupMessageViewModel;

public class GroupsFragment extends Fragment {

    private View view;
    private Button createGroupButton;
    private Button viewInvitesButton;
    private GroupMessageAdapter groupMessageAdapter;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        view = inflater.inflate(R.layout.groups_fragment, container, false);


        createGroupButton = (Button) view.findViewById(R.id.create_group);
        createGroupButton.setOnClickListener((View v) -> {
            createGroupDialog(); // When the create group button is clicked, display an input button
        });


        return view;
    }

    /** Builds the recycler view to display the list of Group Messages the user is a part of. */
    public void buildGroupMessageRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.group_message_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DataRetrievalListener dataRetrievalListener = new DataRetrievalListener() {
            @Override
            public void onDataRetrieval() {
                groupMessageAdapter.notifyDataSetChanged();
            }
        };
        groupMessageAdapter = new GroupMessageAdapter(getActivity(), FirebaseRTDBHelper.getInstance().getGroupMessages(dataRetrievalListener));
        GroupMessageViewModel gmViewModel = new ViewModelProvider(requireActivity()).get(GroupMessageViewModel.class);
        groupMessageAdapter.setClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Store the groupMessage clicked on for retrieval by the GroupMessageFragment
                gmViewModel.setGroupMessage(groupMessageAdapter.getItem(position));
                NavHostFragment.findNavController(GroupsFragment.this).navigate(R.id.action_GroupsFragment_to_GroupMessageFragment);
            }
        });
                recyclerView.setAdapter(groupMessageAdapter);
    }

    /** Creates a dialog box and displays it to the user to get new group name */
    public void createGroupDialog(){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.create_group_dialog); // Assign to xml dialog layout
        TextView text = (TextView) dialog.findViewById(R.id.new_group_name);
        Button okButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button cancelButton = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        okButton.setOnClickListener((View v) -> {
            createGroup(text.getText().toString()); // Create new group
            dialog.dismiss();
        });
        cancelButton.setOnClickListener((View v) ->
            dialog.dismiss()
        );

        dialog.show();
    }
    /** Creates a group and sends the data to the firebase database */
    public void createGroup(String groupName){
        // Send new group to database
        FirebaseRTDBHelper.getInstance().addGroupMessage(groupName);
        Toast.makeText(getActivity(), groupName + " created!", Toast.LENGTH_LONG).show();
        Log.d("Create Group: ", groupName);
    }
    private void updateCurrentLoginInfo(View view){
        // Checks if user is logged in, if not send to login page
        view.getRootView().findViewById(R.id.loginButton).setOnClickListener((View v) -> {
            if (!FirebaseRTDBHelper.getInstance().isLoggedIn()){
                NavHostFragment.findNavController(GroupsFragment.this)
                        .navigate(R.id.loginFragment);
            } else {
                FirebaseRTDBHelper.getInstance().logout();
                NavHostFragment.findNavController(GroupsFragment.this)
                        .navigate(R.id.loginFragment);
            }
        });
        if (!FirebaseRTDBHelper.getInstance().isLoggedIn()){
            NavHostFragment.findNavController(GroupsFragment.this)
                    .navigate(R.id.loginFragment);
        }
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.groups_fragment_activities_button).setOnClickListener((View v) ->
            NavHostFragment.findNavController(GroupsFragment.this)
                    .navigate(R.id.action_GroupsFragment_to_SecondFragment)
        );
        updateCurrentLoginInfo(view);
        viewInvitesButton = view.findViewById(R.id.groups_view_invites_button);
        viewInvitesButton.setOnClickListener((View v) ->{
            NavHostFragment.findNavController(GroupsFragment.this).navigate(R.id.action_GroupsFragment_to_GroupInvitesFragment);
        });

        //Need to build the recycler view onViewCreated to create ViewModel and reference NavHost
        buildGroupMessageRecyclerView(view);
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