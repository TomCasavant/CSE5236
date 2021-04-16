package com.group9.grouptivity.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.DataRetrievalListener;
import com.group9.grouptivity.firebase.FirebaseRTDBHelper;
import com.group9.grouptivity.firebase.models.recyclerViewAdapters.GroupMessageInviteAdapter;

//TODO this class
public class GroupInvitesFragment extends Fragment {
    private View view;
    private DrawerLayout mDrawerLayout;
    private GroupMessageInviteAdapter groupMessageInviteAdapter;

    /** Sets up the main navigation view for this fragment. */
    private void setupMainNavigationView(View view) {
        NavigationView navigationView = view.findViewById(R.id.main_navView);
        View mainNavViewHeader = navigationView.getHeaderView(0); //Should only be one header view
        TextView headerDisplayNameTextView = mainNavViewHeader.findViewById(R.id.main_navview_header_displayName);
        if (FirebaseRTDBHelper.getInstance().isLoggedIn()) {
            headerDisplayNameTextView.setText(FirebaseRTDBHelper.getInstance().getCurrentUser().getDisplayName());
        }

        //Need to find a way to use id and not int literal
        navigationView.getMenu().getItem(2).setChecked(true); //Set invites menu item to checked

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case (R.id.main_navview_groups_item):
                    NavHostFragment.findNavController(GroupInvitesFragment.this).navigate(R.id.action_GroupInvitesFragment_to_GroupsFragment);
                    break;
                case (R.id.main_navview_activities_item):
                    NavHostFragment.findNavController(GroupInvitesFragment.this).navigate(R.id.action_GroupInvitesFragment_to_ActivitySearchFragment);
                    break;
                case (R.id.main_navview_invites_item):
                    //Do nothing. We are already here
                    break;
                default:
                    //error?
                    break;
            }
            mDrawerLayout.closeDrawers();
            return false; //don't change the current item in case the user comes back
        });
    }

    /** Builds the recycler view to display the list of pending Group Message Invites the user has. */
    private void buildGroupMessageInviteRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.group_message_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DataRetrievalListener dataRetrievalListener = () -> groupMessageInviteAdapter.notifyDataSetChanged();
        groupMessageInviteAdapter = new GroupMessageInviteAdapter(getActivity(), FirebaseRTDBHelper.getInstance().getGroupMessageInvites(dataRetrievalListener));
        recyclerView.setAdapter(groupMessageInviteAdapter);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        //Inflate the view for this fragment
        view = inflater.inflate(R.layout.group_invites_fragment, container, false);

        buildGroupMessageInviteRecyclerView(view);

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDrawerLayout = view.findViewById(R.id.main_drawerLayout);
        setupMainNavigationView(view);
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