package com.project.weightmanagementsystems;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class EditWeightDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the Builder class for dialog construction
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_weight_layout, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final EditText editWeight = view.findViewById(R.id.editWeightInput);
        final EditText editUnit = view.findViewById(R.id.editUnitInput);

        Button save = view.findViewById(R.id.save_weight);
        Button cancel = view.findViewById(R.id.cancel_action);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddWeightDialog.DialogListener dialogListener = (AddWeightDialog.DialogListener) getActivity();
                dialogListener.onFinishEditDialog(editWeight.getText().toString(), editUnit.getText().toString(), false);
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogListener dialogListener = (DialogListener) getActivity();
                dismiss();
            }
        });
    }

    public interface DialogListener {
        void onFinishEditDialog(String weight, String unit, boolean add);
    }
}
