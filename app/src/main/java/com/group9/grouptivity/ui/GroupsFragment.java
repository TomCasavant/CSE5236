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
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.group9.grouptivity.firebase.DataRetrievalListener;
import com.group9.grouptivity.firebase.ItemClickListener;
import com.group9.grouptivity.firebase.models.recyclerViewAdapters.GroupMessageAdapter;
import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.FirebaseRTDBHelper;
import com.group9.grouptivity.ui.models.GroupMessageViewModel;

public class GroupsFragment extends Fragment {

    private View view;
    private DrawerLayout mDrawerLayout;
    private Button mCreateGroupButton;
    private GroupMessageAdapter mGroupMessageAdapter;
    private GroupMessageViewModel mViewModel;

    /** Builds the recycler view to display the list of Group Messages the user is a part of. */
    private void buildGroupMessageRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.group_message_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DataRetrievalListener dataRetrievalListener = () -> mGroupMessageAdapter.notifyDataSetChanged();
        ItemClickListener itemClickListener = (view1, position) -> {
            //Store the groupMessage clicked on for retrieval by the GroupMessageFragment
            mViewModel.setGroupMessage(mGroupMessageAdapter.getItem(position));
            NavHostFragment.findNavController(GroupsFragment.this).navigate(R.id.action_GroupsFragment_to_GroupMessageFragment);
        };

        mGroupMessageAdapter = new GroupMessageAdapter(getActivity(), FirebaseRTDBHelper.getInstance().getGroupMessages(dataRetrievalListener), itemClickListener);
        this.mViewModel = new ViewModelProvider(requireActivity()).get(GroupMessageViewModel.class);
        recyclerView.setAdapter(mGroupMessageAdapter);
    }

    /** Sets up the main navigation view for this fragment. */
    private void setupMainNavigationView(View view) {
        NavigationView navigationView = view.findViewById(R.id.main_navView);
        View mainNavViewHeader = navigationView.getHeaderView(0); //Should only be one header view
        TextView headerDisplayNameTextView = mainNavViewHeader.findViewById(R.id.main_navview_header_displayName);
        if (FirebaseRTDBHelper.getInstance().isLoggedIn()) {
            headerDisplayNameTextView.setText(FirebaseRTDBHelper.getInstance().getCurrentUser().getDisplayName());
        }
        //Need to find a way to use id and not int literal
        navigationView.getMenu().getItem(0).setChecked(true); //Set groups menu item to checked

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case (R.id.main_navview_groups_item):
                    //Do nothing. We are already here
                    break;
                case (R.id.main_navview_activities_item):
                    NavHostFragment.findNavController(GroupsFragment.this).navigate(R.id.action_GroupsFragment_to_ActivitySearchFragment);
                    break;
                case (R.id.main_navview_invites_item):
                    NavHostFragment.findNavController(GroupsFragment.this).navigate(R.id.action_GroupsFragment_to_GroupInvitesFragment);
                    break;
                default:
                    //error?
                    break;
            }
            mDrawerLayout.closeDrawers();
            return false; //don't change the current item in case the user comes back
        });
    }

    /** Creates a dialog box and displays it to the user to get new group name */
    private void createGroupDialog(){
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

    /** Creates a group and sends the data to the firebase database */
    private void createGroup(String groupName){
        // Send new group to database
        FirebaseRTDBHelper.getInstance().addGroupMessage(groupName);
        Toast.makeText(getActivity(), groupName + " created!", Toast.LENGTH_LONG).show();
        Log.d("Create Group: ", groupName);
    }


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        view = inflater.inflate(R.layout.groups_fragment, container, false);


        mCreateGroupButton = (Button) view.findViewById(R.id.create_group);
        mCreateGroupButton.setOnClickListener((View v) -> {
            createGroupDialog(); // When the create group button is clicked, display an input button
        });


        return view;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateCurrentLoginInfo(view);

        //Prepare drawer layout and the left nav
        mDrawerLayout = view.findViewById(R.id.main_drawerLayout);
        setupMainNavigationView(view);

        //Need to build the recycler view in onViewCreated to create ViewModel and reference NavHost
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