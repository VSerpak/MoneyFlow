package com.example.vserp.moneyflow.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;

import com.example.vserp.moneyflow.utils.Prefs;

import java.util.Calendar;

public class MyIntentService extends IntentService {

    private static final String ACTION_INSERT_EXPENSE = "com.example.vserp.moneyflow.services.action.INSERT_EXPENSE";

    private static final String EXTRA_EXPENSE_NAME = "com.example.vserp.moneyflow.services.extra.EXPENSE_NAME";
    private static final String EXTRA_EXPENSE_VOLUME = "com.example.vserp.moneyflow.services.extra.EXPENSE_VOLUME";

    public MyIntentService() {
        super("MyIntentService");
    }

    public static void startActionInsertExpense(Context context, String name, Double volume) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_INSERT_EXPENSE);
        intent.putExtra(EXTRA_EXPENSE_NAME, name);
        intent.putExtra(EXTRA_EXPENSE_VOLUME, volume);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INSERT_EXPENSE.equals(action)) {
                final String name = intent.getStringExtra(EXTRA_EXPENSE_NAME);
                final Float volume = intent.getFloatExtra(EXTRA_EXPENSE_VOLUME,0);
                handleActionInsertExpense(name, volume);
            }
        }
    }

    private void handleActionInsertExpense(String name, Float volume) {
        ContentValues cv = new ContentValues();
        String date = String.valueOf(Calendar.getInstance().getTimeInMillis());

        cv.put(Prefs.EXPENSE_FIELD_ID_PASSIVE,1);
        cv.put(Prefs.EXPENSE_FIELD_VOLUME,volume);
        cv.put(Prefs.EXPENSE_FIELD_DATE,date);

        getContentResolver().insert(Prefs.URI_EXPENSES,cv);
    }
}
