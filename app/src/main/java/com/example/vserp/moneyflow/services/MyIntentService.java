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

    private boolean needToAddNewExpenseName = true;
    private int position = 0;//cursor position

    public MyIntentService() {
        super("MyIntentService");
    }

    public static void startActionInsertExpense(Context context, String name, Float volume, boolean isCritical) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_INSERT_EXPENSE);
        intent.putExtra(EXTRA_EXPENSE_NAME, name);
        intent.putExtra(EXTRA_EXPENSE_VOLUME, volume);
        intent.putExtra(EXTRA_EXPENSE_CRITICAL, isCritical);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INSERT_EXPENSE.equals(action)) {
                final String name = intent.getStringExtra(EXTRA_EXPENSE_NAME);
                final Float volume = intent.getFloatExtra(EXTRA_EXPENSE_VOLUME, 0);
                final boolean isCritical = intent.getBooleanExtra(EXTRA_EXPENSE_CRITICAL, false);
                handleActionInsertExpense(name, volume, isCritical);
            }
        }
    }

    private void handleActionInsertExpense(String name, Float volume, boolean isCritical) {

        ContentValues cv = new ContentValues();

        cv.put(Prefs.EXPENSE_NAMES_FIELD_NAME, name);
        cv.put(Prefs.EXPENSE_NAMES_FIELD_CRITICAL, isCritical);

        Cursor c = getContentResolver().query(Prefs.URI_EXPENSES_NAMES, null, null, null, null);

        if (c != null) {
            checkExpenseNameAvailability(c, cv, name);

            passiveIdApplication(c, cv);

            c.close();
        }

        String date = String.valueOf(Calendar.getInstance().getTimeInMillis());

        cv.put(Prefs.EXPENSE_FIELD_VOLUME, volume);
        cv.put(Prefs.EXPENSE_FIELD_DATE, date);

        getContentResolver().insert(Prefs.URI_EXPENSES, cv);
    }

    //Check if the input expense exist in the table expense_name
    //If yes - it will not include it into the table
    private void checkExpenseNameAvailability(Cursor c, ContentValues cv, String name) {

        if (c.moveToFirst()) {
            do {
                if ((c.getString(c.getColumnIndex(Prefs.EXPENSE_NAMES_FIELD_NAME))).equals(name)) {
                    needToAddNewExpenseName = false;
                    position = c.getPosition();
                }
            } while (c.moveToNext());
            if (needToAddNewExpenseName) {
                getContentResolver().insert(Prefs.URI_EXPENSES_NAMES, cv);
            }
        } else
            getContentResolver().insert(Prefs.URI_EXPENSES_NAMES, cv);
        cv.clear();
    }

    //Coordinate the _id from the "table_expenses" table with the adding id_passive
    //depending of the existing expenses name in the "table_expense_names"
    private void passiveIdApplication(Cursor c, ContentValues cv) {

        try {
            if (needToAddNewExpenseName) {
                c.moveToLast();
                cv.put(Prefs.EXPENSE_FIELD_ID_PASSIVE, Integer.valueOf(c.getString(c.getColumnIndex(Prefs.FIELD_ID))) + 1);
            } else {
                c.moveToPosition(position);
                cv.put(Prefs.EXPENSE_FIELD_ID_PASSIVE, Integer.valueOf(c.getString(c.getColumnIndex(Prefs.FIELD_ID))));
            }
        } catch (android.database.CursorIndexOutOfBoundsException e) {
            cv.put(Prefs.EXPENSE_FIELD_ID_PASSIVE, 1);
        }
    }

}