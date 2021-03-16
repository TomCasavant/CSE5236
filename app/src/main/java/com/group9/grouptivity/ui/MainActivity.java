package com.group9.grouptivity.ui;

import android.app.Dialog;
import android.os.Bundle;

import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.FirebaseRTDBHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d("OnCreate()", "Successfully ran Activity OnCreate()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){
            case R.id.action_settings:
                return true;
            case R.id.login_button:
                createLoginDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "Activity OnStart() ran", Toast.LENGTH_LONG).show();
        Log.d("OnStart()", "Successfully ran Activity OnStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(), "Activity OnPause() ran", Toast.LENGTH_LONG).show();
        Log.d("OnPause()", "Successfully ran Activity OnPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "Activity OnResume() ran", Toast.LENGTH_LONG).show();
        Log.d("OnResume()", "Successfully ran Activity OnResume()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(), "Activity OnStop() ran", Toast.LENGTH_LONG).show();
        Log.d("OnStop()", "Successfully ran Activity OnStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "Activity OnDestroy() ran", Toast.LENGTH_LONG).show();
        Log.d("OnDestroy()", "Successfully ran Activity OnDestroy()");
    }

    /* Creates a dialog box and displays it to the user to allow them to sign in */
    public void createLoginDialog(){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.login_page); // Assign to xml dialog layout

        // Get username and password text boxes
        TextView username = (TextView) dialog.findViewById(R.id.username);
        TextView password = (TextView) dialog.findViewById(R.id.password);

        Button loginButton = (Button) dialog.findViewById(R.id.loginButton);
        Button createAccountButton = (Button) dialog.findViewById(R.id.createAccountButton);
        loginButton.setOnClickListener((View v) -> {
            FirebaseRTDBHelper.getInstance().login(username.getText().toString(), password.getText().toString(), this);
            dialog.dismiss();
        });
        createAccountButton.setOnClickListener((View v) -> {
            FirebaseRTDBHelper.getInstance().createAccount(username.getText().toString(), password.getText().toString(), this);
            dialog.dismiss();
        });

        dialog.show();
    }

}