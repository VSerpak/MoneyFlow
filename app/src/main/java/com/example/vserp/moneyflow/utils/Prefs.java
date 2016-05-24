package com.example.vserp.moneyflow.utils;

import android.net.Uri;

/**
 * Created by vserp on 5/24/2016.
 */
public class Prefs {

    public static final String LOG_TAG = "MoneyFlow";

    //The Database constants:
    public static final String DB_NAME = "MoneyFlowDB";
    public static final int DB_CURRENT_VERSION = 1;
    public static final String FIELD_ID = "_id";

    //The Table Expenses constants:
    public static final String TABLE_NAME_EXPENSES = "expenses";
    public static final String EXPENSE_FIELD_ID_PASSIVE = "id_passive";
    public static final String EXPENSE_FIELD_VOLUME = "volume";
    public static final String EXPENSE_FIELD_DATE = "date";

    //The provider constants:
    public static final String URI_EXPENSES_AUTHORITIES = "com.example.vserp.moneyflow.provider";
    public static final String URI_EXPENSES_TYPE = "expenses";
    public static final Uri URI_EXPENSES = Uri.parse("content://" + URI_EXPENSES_AUTHORITIES + "/" + URI_EXPENSES_TYPE);
}
