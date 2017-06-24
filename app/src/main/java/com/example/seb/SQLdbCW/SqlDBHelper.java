package com.example.seb.SQLdbCW;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlDBHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "employees.db";
    private static final String CREATE_EMPLOYEE_ENTRIES =
            "CREATE TABLE " + EmployeeDetailsContract.TABLE_NAME_EMPLOYEES + " (" +
                    EmployeeDetailsContract.COLUMN_NAME_NI_NUMBER + TEXT_TYPE + " PRIMARY KEY " + COMMA_SEP +
    EmployeeDetailsContract.COLUMN_NAME_EMPLOYEE_NAME + TEXT_TYPE + ")";
    private static final String CREATE_EMPLOYMENT_ENTRIES =
            "CREATE TABLE " + EmploymentContract.TABLE_NAME_EMPLOYEE_DETAILS + " (" +
                    EmploymentContract.COLUMN_NAME_NI_NUMBER + TEXT_TYPE  + COMMA_SEP +
                    EmploymentContract.COLUMN_NAME_JOB_NATURE+ TEXT_TYPE + COMMA_SEP +
                    EmploymentContract.COLUMN_NAME_EMPLOYER_NAME + TEXT_TYPE + COMMA_SEP+
                    EmploymentContract.COLUMN_NAME_GROSS_WEEKLY+ TEXT_TYPE + ")";

    private static final String DELETE_EMPLOYEE_ENTRIES = "DROP TABLE IF EXISTS " +
            EmployeeDetailsContract.TABLE_NAME_EMPLOYEES;

    private static final String DELETE_EMPLOYMENT_ENTRIES = "DROP TABLE IF EXISTS " +
            EmploymentContract.TABLE_NAME_EMPLOYEE_DETAILS;

    public SqlDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EMPLOYEE_ENTRIES);
        db.execSQL(CREATE_EMPLOYMENT_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_EMPLOYEE_ENTRIES);
        db.execSQL(DELETE_EMPLOYMENT_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
