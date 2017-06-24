package com.example.seb.SQLdbCW;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddEmploymentActivity extends Activity {
    private EmployeeDAO employeeDAO;
    private EditText niNumberEdt;
    private EditText jobEdt;
    private EditText employerEdt;
    private EditText grossWeeklyEdt;

    private ArrayList<EmployeeDetails> employeesList = new ArrayList<>();
    private ArrayList<Employment> employmentsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getActionBar().setTitle("Add Employment");
        Intent empIntent = getIntent();
        String employee = empIntent.getStringExtra(Constants.EMP_KEY);

        employeeDAO = new EmployeeDAO(this);
        employeeDAO.open();
        niNumberEdt = (EditText) findViewById(R.id.ni_number_edt);
        jobEdt = (EditText) findViewById(R.id.job_nature_edt);
        employerEdt = (EditText) findViewById(R.id.employer_edt);
        grossWeeklyEdt = (EditText) findViewById(R.id.gross_weekly_edt);

        niNumberEdt.setText(employee);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_employment_activity, menu);
        return true;
    }

    private boolean niValidation(String niNumber) {
        employeesList.clear();
        employeeDAO.getEmployees(employeesList);
        if (niNumber.length() < 9) {
            Toast.makeText(getApplicationContext(), "NI number must be 9 characters", Toast.LENGTH_LONG).show();
            return false;
        } else {
            for (int i = 0; i < employeesList.size(); i++) {
                if (employeesList.get(i).getNiNumber().equals(niNumber)) {
                    return true;
                }
            }
        }
        Toast.makeText(getApplicationContext(), "No employee with this NI number", Toast.LENGTH_LONG).show();
        return false;
    }

    private boolean employerValidation(String niNum,String employer){
        employmentsList.clear();
        employeeDAO.getEmployments(employmentsList,niNum);
        for(int i=0; i<employmentsList.size();i++){
            if(employer.equals(employmentsList.get(i).getEmployer())){
                Toast.makeText(getApplicationContext(),"Employer Already Exists",Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    private boolean emptyField(EditText textField){
        if(textField.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Text fields are empty", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_item_save_employment) {
           if(!emptyField(niNumberEdt)&&!emptyField(jobEdt)&&!emptyField(employerEdt)&&!emptyField(grossWeeklyEdt)){
                if (niValidation(niNumberEdt.getText().toString())) {
                    if(employerValidation(niNumberEdt.getText().toString(),employerEdt.getText().toString())){
                        employeeDAO.addEmployment(niNumberEdt.getText().toString(),
                                jobEdt.getText().toString(), employerEdt.getText().toString(),
                                Double.parseDouble(grossWeeklyEdt.getText().toString()));
                        Toast.makeText(getApplicationContext(), "Employment Added", Toast.LENGTH_LONG).show();
                        jobEdt.setText("");
                        employerEdt.setText("");
                        grossWeeklyEdt.setText("");
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

