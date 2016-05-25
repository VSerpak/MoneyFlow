package com.example.vserp.moneyflow.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.vserp.moneyflow.R;
import com.example.vserp.moneyflow.services.MyIntentService;

public class AddNewExpenseDialog extends DialogFragment {

    EditText etName;
    EditText etVolume;
    CheckBox chbCritical;
    int critical = 0;
    String name;
    Float volume;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_add_new_expense_dialog,null,true);

        etName = (EditText) view.findViewById(R.id.acNameOfExpense);
        etVolume = (EditText) view.findViewById(R.id.etVolumeOfExpense);
        chbCritical = (CheckBox)view.findViewById(R.id.chbCriticalExpense);

//        TODO set Adapter for AutocompliteTextView

        builder.setView(view)
                .setIcon(R.drawable.new_expense_icon_32)
                .setMessage(R.string.message_add_new_expense_dialog)
                .setTitle(R.string.title_add_new_expense_dialog)
                .setPositiveButton(R.string.positive_button_add_new_expense_dialog,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (chbCritical.isChecked())
                                    critical = 1;

                                name = etName.getText().toString();
                                volume = Float.valueOf(etVolume.getText().toString());

                                if (!(name.equals(""))){
                                    addNewExpense();
                                }
                                dismiss();
                            }
                        })
                .setNegativeButton(R.string.negative_button_add_new_expense_dialog,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dismiss();
                            }
                        });
        return builder.create();
    }

    private void addNewExpense() {

        MyIntentService.startActionInsertExpense(getActivity(),name, volume, critical);
    }
}

