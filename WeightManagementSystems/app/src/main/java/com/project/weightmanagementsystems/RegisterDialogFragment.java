package com.project.weightmanagementsystems;

import android.content.Context;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;


public class RegisterDialogFragment extends DialogFragment {

    public interface OnRegisterOptionSelectedListener {
        void onRegisterClick(int which);
    }
    private OnRegisterOptionSelectedListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the Builder class for dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Get Layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.fragment_register_dialog, null))
                //Add Action Buttons and set response
                .setPositiveButton(R.string.registerYes, (dialog, which) -> mListener.onRegisterClick(which))
                .setNegativeButton(R.string.registerNo, (dialog, which) ->  mListener.onRegisterClick(which));
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        mListener = (OnRegisterOptionSelectedListener) context;
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mListener = null;
    }
}