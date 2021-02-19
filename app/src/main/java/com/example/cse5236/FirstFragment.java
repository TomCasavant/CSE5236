package com.example.cse5236;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
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