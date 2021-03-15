package com.group9.grouptivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group9.grouptivity.firebase.models.GroupMessage;

public class GroupsFragment extends Fragment {

    View view;
    Button createGroupButton;
    private DatabaseReference mDatabase;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        view = inflater.inflate(R.layout.groups_fragment, container, false);


        createGroupButton = (Button) view.findViewById(R.id.create_group);
        createGroupButton.setOnClickListener((View v) -> {
            Toast.makeText(getActivity(), "Create Group!", Toast.LENGTH_LONG).show();
            createGroupDialog(); // When the create group button is clicked, display an input button
        });
        return view;
    }

    /* Creates a dialog box and displays it to the user to get new group name */
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
    /* Creates a group and sends the data to the firebase database */
    public void createGroup(String group_name){
        // Send new group to database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String id = mDatabase.child("groupMessages").push().getKey();
        mDatabase.child("groupMessages").child(id).setValue(new GroupMessage(group_name));

        Toast.makeText(getActivity(), group_name, Toast.LENGTH_LONG).show();
        Log.d("Create Group: ", group_name);
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