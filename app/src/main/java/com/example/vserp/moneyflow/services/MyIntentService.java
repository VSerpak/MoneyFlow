package com.example.vserp.moneyflow.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.example.vserp.moneyflow.utils.Prefs;

import java.util.Calendar;

public class MyIntentService extends IntentService {

    private static final String ACTION_INSERT_EXPENSE = "com.example.vserp.moneyflow.services.action.INSERT_EXPENSE";

    private static final String EXTRA_EXPENSE_NAME = "com.example.vserp.moneyflow.services.extra.EXPENSE_NAME";
    private static final String EXTRA_EXPENSE_VOLUME = "com.example.vserp.moneyflow.services.extra.EXPENSE_VOLUME";
    private static final String EXTRA_EXPENSE_CRITICAL = "com.example.vserp.moneyflow.services.extra.EXPENSE_CRITICAL";
    ;

    public MyIntentService() {
        super("MyIntentService");
    }

    public static void startActionInsertExpense(Context context, String name, Float volume, int critical) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_INSERT_EXPENSE);
        intent.putExtra(EXTRA_EXPENSE_NAME, name);
        intent.putExtra(EXTRA_EXPENSE_VOLUME, volume);
        intent.putExtra(EXTRA_EXPENSE_CRITICAL, critical);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INSERT_EXPENSE.equals(action)) {
                final String name = intent.getStringExtra(EXTRA_EXPENSE_NAME);
                final Float volume = intent.getFloatExtra(EXTRA_EXPENSE_VOLUME, 0);
                final int critical = intent.getIntExtra(EXTRA_EXPENSE_CRITICAL, 0);
                handleActionInsertExpense(name, volume, critical);
            }
        }
    }

    private void handleActionInsertExpense(String name, Float volume, int critical) {
        ContentValues cv = new ContentValues();
        ContentValues cv1 = new ContentValues();

        String date = String.valueOf(Calendar.getInstance().getTimeInMillis());

        cv1.put(Prefs.EXPENSE_NAMES_FIELD_NAME, name);
        cv1.put(Prefs.EXPENSE_NAMES_FIELD_CRITICAL, critical);

        Cursor c = getContentResolver().query(Prefs.URI_EXPENSES_NAMES, null, null, null, null);
        int acc = 0;
        int position = 0;

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    if ((c.getString(c.getColumnIndex(Prefs.EXPENSE_NAMES_FIELD_NAME))).equals(name)) {
                        acc++;
                        position = c.getPosition();
                    }
                } while (c.moveToNext());
                if (acc == 0) {
                    getContentResolver().insert(Prefs.URI_EXPENSES_NAMES, cv1);

                }
            } else
                getContentResolver().insert(Prefs.URI_EXPENSES_NAMES, cv1);
        }

        try {
            if (acc == 0) {
                c.moveToLast();
                cv.put(Prefs.EXPENSE_FIELD_ID_PASSIVE, Integer.valueOf(c.getString(c.getColumnIndex(Prefs.FIELD_ID))) + 1);
            } else {
                c.moveToPosition(position);
                cv.put(Prefs.EXPENSE_FIELD_ID_PASSIVE, Integer.valueOf(c.getString(c.getColumnIndex(Prefs.FIELD_ID))));
            }
        } catch (android.database.CursorIndexOutOfBoundsException e) {
            cv.put(Prefs.EXPENSE_FIELD_ID_PASSIVE, 1);
        }

        cv.put(Prefs.EXPENSE_FIELD_VOLUME, volume);
        cv.put(Prefs.EXPENSE_FIELD_DATE, date);

        c.close();

        getContentResolver().insert(Prefs.URI_EXPENSES, cv);
    }
}