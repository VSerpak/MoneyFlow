package com.example.vserp.moneyflow.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vserp.moneyflow.R;
import com.example.vserp.moneyflow.services.MyIntentService;
import com.example.vserp.moneyflow.utils.Prefs;

public class AddNewExpenseDialog extends DialogFragment {

    private AutoCompleteTextView actName;
    private EditText etVolume;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_add_new_expense_dialog, null, false);

        actName = (AutoCompleteTextView) view.findViewById(R.id.acNameOfExpense);
        etVolume = (EditText) view.findViewById(R.id.etVolumeOfExpense);


//        TODO set Adapter for AutocompliteTextView
        builder.setView(view)
                .setIcon(R.drawable.new_expense_icon_32)
                .setMessage(R.string.message_add_new_expense_dialog)
                .setTitle(R.string.title_add_new_expense_dialog)
                .setPositiveButton(R.string.positive_button_add_new_expense_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String expenseVolume = etVolume.getText().toString();
                        if (expenseVolume.matches("[0-9]*")) {

                            addNewExpense(actName.getText().toString(), Double.parseDouble(expenseVolume));
                        }else{
                            Context context;
                            Log.d(Prefs.LOG_TAG,"The item was not add - input correct volume!");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                Toast.makeText(getContext(), "Input correct volume", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }).setNegativeButton(R.string.negative_button_add_new_expense_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        return builder.create();
    }

    private void addNewExpense(String name, Double volume) {
        Toast.makeText(getActivity(), "Add from dialog", Toast.LENGTH_LONG).show();

        MyIntentService.startActionInsertExpense(getActivity(), name, volume);
    }
}