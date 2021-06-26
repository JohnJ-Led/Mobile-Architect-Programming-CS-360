package com.project.weightmanagementsystems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity implements RegisterDialogFragment.OnRegisterOptionSelectedListener {

    private int mRegisterResponse;
    private User user = new User();
    private String username = "";

    FragmentManager manager = getSupportFragmentManager();
    RegisterDialogFragment registerDialog = new RegisterDialogFragment();

    @Override
    public void onRegisterClick(int which){
        mRegisterResponse = -which;
        //Log.e(TAG, "Register Response On Click: " + mRegisterResponse);
        switch (mRegisterResponse) {
            case 1:
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.putExtra(username, user.getUser());
                startActivity(intent);
                Log.e(TAG, "Response Yes");
                break;
            case 2:
                Log.e(TAG, "Response No");
                break;
            default:
                break;
        }
    }
    private TrackItDatabase mTrackDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mTrackDb = TrackItDatabase.getInstance(getApplicationContext());

    }

    public void onSignInClicked(View view){
        EditText Uid = findViewById(R.id.editTextLoginUsername);
        EditText PassW = findViewById(R.id.editTextLoginPassword);
        Log.e(TAG, "UID: " + Uid.getText() + ", Password: " + PassW.getText());

        user.setUser(String.valueOf(Uid.getText()));
        user.setPassword(String.valueOf(PassW.getText()));
        Log.e(TAG, "User Found: " + mTrackDb.getUserExist(user));
        if(mTrackDb.getUserExist(user)) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Username", user.getUser());
            startActivity(intent);
        }
        else{
            registerDialog.show(manager, "registerMessage");
        }
    }

}