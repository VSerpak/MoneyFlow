package com.example.vserp.moneyflow.utils;

import android.net.Uri;

/**
 * Created by vserp on 5/24/2016.
 */
public class Prefs {

    public static final String LOG_TAG = "Money_Flow";
    public static final String LOG_WARN_SQL = "SQLite";

    //The password constants:
    public static final String FN_FIELD_NAME = "first_name";
    public static final String LN_FIELD_NAME = "last_name";
    public static final String BIRTHDAY_FIELD_NAME = "birthday";
    public static final String EMAIL_FIELD_NAME = "email";
    public static final String API_SERVER = "http://cityfinder.esy.es";

    //The Database constants:
    public static final String DB_NAME = "Money_Flow_DB";
    public static final int DB_CURRENT_VERSION = 2;
    public static final String FIELD_ID = "_id";

    //The Table Expenses constants:
    public static final String TABLE_EXPENSES = "expenses";
    public static final String EXPENSE_FIELD_ID_PASSIVE = "id_passive";
    public static final String EXPENSE_FIELD_VOLUME = "volume";
    public static final String EXPENSE_FIELD_DATE = "date";

    //The Table Expense Names
    public static final String TABLE_EXPENSE_NAMES = "expense_names";
    public static final String EXPENSE_NAMES_FIELD_NAME = "name";
    public static final String EXPENSE_NAMES_FIELD_CRITICAL = "critical";

    //The provider constants:
    public static final String URI_EXPENSES_AUTHORITIES = "com.example.vserp.moneyflow.provider";

    public static final String URI_EXPENSES_TYPE = "expenses";
    public static final Uri URI_EXPENSES = Uri.parse("content://" + URI_EXPENSES_AUTHORITIES + "/" + URI_EXPENSES_TYPE);

    public static final String URI_EXPENSES_NAMES_TYPE = "expenses_names";
    public static final Uri URI_EXPENSES_NAMES = Uri.parse("content://" + URI_EXPENSES_AUTHORITIES + "/" + URI_EXPENSES_NAMES_TYPE);

    public static final String RAW_QUERY_ALL_EXPENSES = "SELECT expense_names.name, expenses.volume, expense_names.critical, expenses.date FROM expenses INNER JOIN expense_names ON expense_names._id = expenses.id_passive;";
    public static final String URI_ALL_EXPENSES_TYPE = "all_expenses";
    public static final Uri URI_ALL_EXPENSES = Uri.parse("content://" + URI_EXPENSES_AUTHORITIES + "/" + URI_ALL_EXPENSES_TYPE);
}
