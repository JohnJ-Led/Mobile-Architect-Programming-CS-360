package com.project.weightmanagementsystems;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddWeightDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState){
        //Use the Builder class for dialog construction
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,   ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_weight_layout, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final EditText addWeight = view.findViewById(R.id.addWeightInput);
        final EditText addUnit = view.findViewById(R.id.addUnitInput);

        Button save = view.findViewById(R.id.save_weight);
        Button cancel = view.findViewById(R.id.cancel_action);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogListener dialogListener = (DialogListener) getActivity();
                dialogListener.onFinishEditDialog(addWeight.getText().toString(), addUnit.getText().toString(), true);
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface DialogListener {
        void onFinishEditDialog(String weight, String unit, boolean add);

    }
}
