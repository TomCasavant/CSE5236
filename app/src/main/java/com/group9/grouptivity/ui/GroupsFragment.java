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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group9.grouptivity.firebase.models.GroupMessageAdapter;
import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.FirebaseRTDBHelper;

public class GroupsFragment extends Fragment implements GroupMessageAdapter.ItemClickListener, FirebaseRTDBHelper.DataRetrievalListener {

    private View view;
    private Button createGroupButton;
    private GroupMessageAdapter groupMessageAdapter;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        view = inflater.inflate(R.layout.groups_fragment, container, false);


        createGroupButton = (Button) view.findViewById(R.id.create_group);
        createGroupButton.setOnClickListener((View v) -> {
            Toast.makeText(getActivity(), getString(R.string.create_group_title), Toast.LENGTH_LONG).show();
            createGroupDialog(); // When the create group button is clicked, display an input button
        });

        buildGroupMessageRecyclerView(view);

        return view;
    }

    public void buildGroupMessageRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.group_message_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        groupMessageAdapter = new GroupMessageAdapter(getActivity(), FirebaseRTDBHelper.getInstance().getGroupMessages(this));
        groupMessageAdapter.setClickListener(this);
        recyclerView.setAdapter(groupMessageAdapter);
    }



    public void onDataRetrieval() {
        groupMessageAdapter.notifyDataSetChanged();
    }

    public void onItemClick (View view, int position) {
        Toast.makeText(getActivity(),"You clicked "+ groupMessageAdapter.getItem(position).getName(), Toast.LENGTH_LONG).show();
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
        Toast.makeText(getActivity(), groupName, Toast.LENGTH_LONG).show();
        Log.d("Create Group: ", groupName);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.groups_fragment_activities_button).setOnClickListener((View v) ->
            NavHostFragment.findNavController(GroupsFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment)
        );
    }

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(getContext(), "OnStart() ran in Fragment", Toast.LENGTH_LONG).show();
        Log.d("OnStart()", "Successfully ran OnStart() in Fragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(getContext(), "OnPause() ran in Fragment", Toast.LENGTH_LONG).show();
        Log.d("OnPause()", "Successfully ran OnPause()");
    }

    @Override
    public void onResume() {
        super.onResume();

        Toast.makeText(getContext(), "OnResume() ran in Fragment", Toast.LENGTH_LONG).show();
        Log.d("OnResume()", "Successfully ran OnResume() in Fragment");
    }

    @Override
    public void onStop() {
        super.onStop();
        Toast.makeText(getContext(), "OnStop() ran in Fragment", Toast.LENGTH_LONG).show();
        Log.d("OnStop()", "Successfully ran OnStop() in Fragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getContext(), "OnDestroy() ran in Fragment", Toast.LENGTH_LONG).show();
        Log.d("OnDestroy()", "Successfully ran OnDestroy() in Fragment");
    }

}