package com.project.weightmanagementsystems;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.fragment.app.DialogFragment;

public class NotificationOptInDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the Builder class for dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Get Layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_notification, null))
                //Add Action Buttons
            .setPositiveButton(R.string.notificationPositive, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Set Permissions to Send Notifications
                }
            })
            .setNegativeButton(R.string.notificationNegative, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Set Permissions to Not Send Notifications
                }
            })
            .setNeutralButton(R.string.notificationNeutral, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Cancel Dialog and Set to request after X amount of additional logins
                }
            });
        return builder.create();
    }
}
