package com.example.vserp.moneyflow.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.vserp.moneyflow.db.DBHelper;
import com.example.vserp.moneyflow.utils.Prefs;

public class MyContentProvider extends ContentProvider {

    private SQLiteDatabase database;
    private DBHelper dbHelper;

    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final int URI_EXPENSE_CODE = 1;
    public static final int URI_EXPENSE_NAME_CODE = 2;
    public static final int URI_RAW_QUERY_ALL_EXPENSES_CODE = 3;

    static {
        uriMatcher.addURI(Prefs.URI_EXPENSES_AUTHORITIES,
                Prefs.URI_EXPENSES_TYPE,
                URI_EXPENSE_CODE);
        uriMatcher.addURI(Prefs.URI_EXPENSES_NAMES_AUTHORITIES,
                Prefs.URI_EXPENSES_NAMES_TYPE,
                URI_EXPENSE_NAME_CODE);
        uriMatcher.addURI(Prefs.URI_EXPENSES_NAMES_AUTHORITIES,
                Prefs.URI_ALL_EXPENSES_TYPE,
                URI_RAW_QUERY_ALL_EXPENSES_CODE
                );
    }

    public MyContentProvider() {
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext(), Prefs.DB_CURRENT_VERSION);
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        long id;
        Uri insertUri = null;

        database = dbHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case URI_EXPENSE_CODE:
                id = database.insert(Prefs.TABLE_NAME_EXPENSES, null, values);
                insertUri = ContentUris.withAppendedId(Prefs.URI_EXPENSES, id);
                getContext().getContentResolver().notifyChange(insertUri, null);
                break;
            case URI_EXPENSE_NAME_CODE:
                id = database.insert(Prefs.TABLE_NAME_EXPENSE_NAMES, null, values);
                insertUri = ContentUris.withAppendedId(Prefs.URI_EXPENSES_NAMES, id);
                getContext().getContentResolver().notifyChange(insertUri, null);
                break;
        }
        return insertUri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        database = dbHelper.getWritableDatabase();

        Cursor cursor = null;

        switch (uriMatcher.match(uri)) {
            case URI_EXPENSE_CODE:
                cursor = database.query(Prefs.TABLE_NAME_EXPENSES, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            case URI_EXPENSE_NAME_CODE:
                cursor = database.query(Prefs.TABLE_NAME_EXPENSE_NAMES,projection,
                        selection,selectionArgs,null,null,sortOrder);
                break;
            case URI_RAW_QUERY_ALL_EXPENSES_CODE:
                cursor = database.rawQuery(Prefs.RAW_QUERY_ALL_EXPENSES,null);
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
