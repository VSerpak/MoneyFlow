package com.example.vserp.moneyflow.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vserp.moneyflow.utils.Prefs;

/**
 * Created by vserp on 5/24/2016.
 */
public class DBHelper extends SQLiteOpenHelper {


    private static final String CREATE_TABLE_EXPENSES = String.format(
            "create table " + Prefs.TABLE_NAME_EXPENSES
                    + " ( %s integer primary key autoincrement, %s integer," +
                    " %s float, %s text);",
            Prefs.FIELD_ID,
            Prefs.EXPENSE_FIELD_ID_PASSIVE,
            Prefs.EXPENSE_FIELD_VOLUME,
            Prefs.EXPENSE_FIELD_DATE);

    public DBHelper(Context context, int version) {
        super(context, Prefs.DB_NAME, null, Prefs.DB_CURRENT_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EXPENSES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
