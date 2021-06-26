package com.project.weightmanagementsystems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;

import static android.content.ContentValues.TAG;

public class RegisterActivity extends AppCompatActivity {

    EditText mUsername;
    EditText mPassword;
    EditText mGoal;
    EditText mDate;
    TrackItDatabase mTrackDb;
    private User user = new User();
    private Goals goals = new Goals();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mTrackDb = TrackItDatabase.getInstance(getApplicationContext());
        mUsername = findViewById(R.id.registerUsername);
        mPassword = findViewById(R.id.registerPassword);
        mGoal = findViewById(R.id.registerGoal);
        mDate = findViewById(R.id.setGoalDate);
        Intent intent = getIntent();

    }

    public void onRegisterClick(View view){
        Log.e(TAG, "Username: " + mUsername.getText() +"\n Password: " + mPassword.getText() + "\n Goal: " + mGoal.getText() + "\n Date: " + mDate.getText());
        user.setUser(String.valueOf(mUsername.getText()));
        user.setPassword(String.valueOf(mPassword.getText()));
        mTrackDb.addUser(user);
        user.setId(mTrackDb.getUser(user).getId());
        goals.setGoal(String.valueOf(mGoal.getText()));
        goals.setUid(user.getId());
        goals.setDate(String.valueOf(mDate.getText()));
        user.setGoals(goals);
        mTrackDb.addGoal(user);
        Log.e(TAG, "USER: " + user);

        if(mTrackDb.getUserExist(user)) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Username", user.getUser());
            startActivity(intent);
        }
    }
}