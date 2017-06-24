package com.example.seb.SQLdbCW;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class EmployeeDAO {

    private SqlDBHelper dbHelper;
    private SQLiteDatabase db;

    public EmployeeDAO(Context context) {
        dbHelper = new SqlDBHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void addEmployee(String niNumber, String employeeName) {
        ContentValues values = new ContentValues();
        values.put(EmployeeDetailsContract.COLUMN_NAME_NI_NUMBER,
                niNumber);
        values.put(EmployeeDetailsContract.COLUMN_NAME_EMPLOYEE_NAME,
                employeeName);
        long newRowId =
                db.insert(EmployeeDetailsContract.TABLE_NAME_EMPLOYEES,null,values);
    }

    public void addEmployment(String niNumber, String job, String employer, double grossWeekly) {
        ContentValues values = new ContentValues();
        values.put(EmploymentContract.COLUMN_NAME_NI_NUMBER,
                niNumber);
        values.put(EmploymentContract.COLUMN_NAME_JOB_NATURE,
                job);
        values.put(EmploymentContract.COLUMN_NAME_EMPLOYER_NAME,
                employer);
        values.put(EmploymentContract.COLUMN_NAME_GROSS_WEEKLY,
                grossWeekly);
        long newRowId =
                db.insert(EmploymentContract.TABLE_NAME_EMPLOYEE_DETAILS,null,values);
    }

    public List<EmployeeDetails> getEmployees(List<EmployeeDetails> employees) {
        Cursor cursor = db.query(
                EmployeeDetailsContract.TABLE_NAME_EMPLOYEES,
                null,null,null,null,null,null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String niNumber = cursor.getString(
                    EmployeeDetailsContract.COLUMN_INDEX_NI_NUMBER);
            String employeeName = cursor.getString(
                    EmployeeDetailsContract.COLUMN_INDEX_EMPLOYEE_NAME);
            employees.add(new EmployeeDetails(niNumber,employeeName));
            cursor.moveToNext();
        }
        return employees;
    }

    public List<Employment> getEmployments(List<Employment> empDetails, String niNumb) {
        String selection;
        if (niNumb != null) {
            selection = EmploymentContract.COLUMN_NAME_NI_NUMBER + " ='" + niNumb + "'";
        }
        else {
            selection = null;
        }
        Cursor cursor = db.query(
                EmploymentContract.TABLE_NAME_EMPLOYEE_DETAILS,
                null,selection,null,null,null,null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String niNumber = cursor.getString(
                    EmploymentContract.COLUMN_INDEX_NI_NUMBER);
            String job = cursor.getString(
                    EmploymentContract.COLUMN_INDEX_JOB_NATURE);
            String employer = cursor.getString(
                    EmploymentContract.COLUMN_INDEX_EMPLOYER_NAME);
            Double grossWeekly = cursor.getDouble(
                    EmploymentContract.COLUMN_INDEX_GROSS_WEEKLY);
            empDetails.add(new Employment(niNumber,job,employer,grossWeekly));
            cursor.moveToNext();
        }
        return empDetails;
    }
    public void updateEmployment(String oldId, String oldEmployer, String newID,
                                 String job, String newEmployer, double earnings){
        ContentValues newValues= new ContentValues();

        newValues.put(EmploymentContract.COLUMN_NAME_NI_NUMBER, newID);
        newValues.put(EmploymentContract.COLUMN_NAME_JOB_NATURE, job);
        newValues.put(EmploymentContract.COLUMN_NAME_EMPLOYER_NAME, newEmployer);
        newValues.put(EmploymentContract.COLUMN_NAME_GROSS_WEEKLY, earnings);

        db.update(EmploymentContract.TABLE_NAME_EMPLOYEE_DETAILS, newValues,""+
                EmploymentContract.COLUMN_NAME_NI_NUMBER + "= '" + oldId + "' AND " +
                EmploymentContract.COLUMN_NAME_EMPLOYER_NAME + "= '" + oldEmployer + "'",null);
    }

    public void deleteEmployment(String id, String employer){
        db.delete(EmploymentContract.TABLE_NAME_EMPLOYEE_DETAILS,
                EmploymentContract.COLUMN_NAME_EMPLOYER_NAME +" = '"+ employer + "' AND " +
                EmploymentContract.COLUMN_NAME_NI_NUMBER+ "= '"+ id + "'", null);
    }
}

