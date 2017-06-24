package com.example.seb.SQLdbCW;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class UpdateEmployment extends Activity {
    public static Employment tempEmployment;

    private EmployeeDAO employeeDAO;
    private EditText niNumET;
    private EditText jobET;
    private EditText employerET;
    private EditText weeklyET;
    private ArrayList<Employment> employmentList= new ArrayList<>();
    private ArrayList<EmployeeDetails> employeeList= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employment);
    }

    @Override
    protected void onResume(){
        super.onResume();
        getActionBar().setTitle("Edit Employment");
        niNumET = (EditText) findViewById(R.id.update_ni_number_edt);
        jobET = (EditText) findViewById(R.id.update_job_nature_edt);
        employerET = (EditText) findViewById(R.id.update_employer_edt);
        weeklyET = (EditText) findViewById(R.id.update_gross_weekly_edt);
        employeeDAO= new EmployeeDAO(this);
        employeeDAO.open();

        niNumET.setText(tempEmployment.getNiNumber());
        jobET.setText(tempEmployment.getJobNature());
        employerET.setText(tempEmployment.getEmployer());
        weeklyET.setText(Double.toString(tempEmployment.getGrossWeekly()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_update_employment, menu);
        return true;
    }
    private boolean niValidation(String niNumber) {
        employeeList.clear();
        employeeDAO.getEmployees(employeeList);
        if (niNumber.length() < 9) {
            Toast.makeText(getApplicationContext(), "NI number must be 9 characters", Toast.LENGTH_LONG).show();
            return false;
        } else {
            for (int i = 0; i < employeeList.size(); i++) {
                if (employeeList.get(i).getNiNumber().equals(niNumber)) {
                    return true;
                }
            }
        }
        Toast.makeText(getApplicationContext(), "No employee with this NI number", Toast.LENGTH_LONG).show();
        return false;
    }


    private boolean emptyField(EditText textField){
        if(textField.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Text fields are empty", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }
    public boolean employerValidate(String oldNi, String newNi, String oldEmployer, String newEmployer){
        employmentList.clear();
        employeeDAO.getEmployments(employmentList, newNi);

        if(!oldEmployer.equals(newEmployer) || !oldNi.equals(newNi)){
            for(int i=0;i<employmentList.size();i++){
                if(newEmployer.equals(employmentList.get(i).getEmployer())){
                    Toast.makeText(getApplicationContext(),
                            "Employee already employed by this employer",Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        final String oldNI= tempEmployment.getNiNumber();
        final String oldEmployer= tempEmployment.getEmployer();

        if (id == R.id.menu_item_delete_employment) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int option) {
                    switch (option){
                        case DialogInterface.BUTTON_POSITIVE:
                            employeeDAO.deleteEmployment(oldNI,oldEmployer);
                            Toast.makeText(getApplicationContext(),"Employment Deleted",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(UpdateEmployment.this, EmploymentListActivity.class);
                            intent.putExtra(Constants.EMP_KEY,oldNI);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to delete this employment?")
                    .setNegativeButton("No", dialogClickListener)
                    .setPositiveButton("Yes", dialogClickListener).show();


        }
        if (id == R.id.menu_item_update_employment) {
            if(!emptyField(niNumET) && !emptyField(jobET) && !emptyField(employerET)&&!emptyField(weeklyET)){
                String ni= niNumET.getText().toString();
                String job= jobET.getText().toString();
                String employer= employerET.getText().toString();
                Double weekly= Double.parseDouble(weeklyET.getText().toString());
                if(niValidation(ni)){
                    if(employerValidate(oldNI,ni,oldEmployer,employer)){
                        employeeDAO.updateEmployment(oldNI, oldEmployer, ni, job, employer, weekly);
                        Toast.makeText(getApplicationContext(),"Employment Updated",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(UpdateEmployment.this, EmploymentListActivity.class);
                        intent.putExtra(Constants.EMP_KEY,oldNI);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
