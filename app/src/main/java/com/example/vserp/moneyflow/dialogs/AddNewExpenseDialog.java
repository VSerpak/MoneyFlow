package com.example.vserp.moneyflow.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vserp.moneyflow.R;
import com.example.vserp.moneyflow.services.MyIntentService;

public class AddNewExpenseDialog extends DialogFragment implements TextWatcher {

    private AutoCompleteTextView acName;
    private EditText etVolume;
    private CheckBox chbCritical;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_add_new_expense_dialog, null, true);

        acName = (AutoCompleteTextView) view.findViewById(R.id.acNameOfExpense);
        etVolume = (EditText) view.findViewById(R.id.etVolumeOfExpense);
        chbCritical = (CheckBox) view.findViewById(R.id.chbCriticalExpense);

        autocompleteAdapter();

        builder.setView(view)
                .setIcon(R.drawable.new_expense_icon_32)
                .setMessage(R.string.message_add_new_expense_dialog)
                .setTitle(R.string.title_add_new_expense_dialog)
                .setPositiveButton(R.string.positive_button_add_new_expense_dialog,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                addNewExpense();
                            }
                        }
                )
                .setNegativeButton(R.string.negative_button_add_new_expense_dialog,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dismiss();
                            }
                        }
                );
        return builder.create();
    }

    private void autocompleteAdapter() {

        //Create and activate resources file for autocomplete:
        Resources res = getResources();
        String[] expensesList = res.getStringArray(R.array.expenses_list);

        //Create and set the adapter for the acName element autocomplete:
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, expensesList);

        acName.setAdapter(adapter);
    }

    private void addNewExpense() {

        String name;
        Float volume;
        int critical = 0;

        name = acName.getText().toString();
        volume = Float.valueOf(etVolume.getText().toString());

        if (chbCritical.isChecked())
            critical = 1;

        //Check the correct completion of the requested form and if yes - new expense addition
        if ((name.equals(""))) {
            Toast.makeText(getActivity(), "Nothing was added - fill in the form correctly!", Toast.LENGTH_SHORT).show();
        } else {
            if (volume == 0) {
                Toast.makeText(getActivity(), "Nothing was added - fill in the form correctly!", Toast.LENGTH_SHORT).show();
            } else {
                MyIntentService.startActionInsertExpense(getActivity(), name, volume, critical);
                Toast.makeText(getActivity(), "The expenses were updated successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}

