package com.project.weightmanagementsystems;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class DeleteWeightDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the Builder class for dialog construction
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.delete_weight_layout, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        Button save = view.findViewById(R.id.delete_weight);
        Button cancel = view.findViewById(R.id.cancel_action);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogListener dialogListener = (DialogListener) getActivity();
                dialogListener.onFinishDeleteDialog(true);
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogListener dialogListener = (DialogListener) getActivity();
                dialogListener.onFinishDeleteDialog(false);
                dismiss();
            }
        });
    }

    public interface DialogListener {
        void onFinishDeleteDialog(boolean delete);
    }
}
