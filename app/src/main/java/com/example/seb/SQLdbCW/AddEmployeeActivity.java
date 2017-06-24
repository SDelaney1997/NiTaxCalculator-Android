package com.example.seb.SQLdbCW;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddEmployeeActivity extends Activity {
    private EmployeeDAO employeeDAO;
    private EditText niNumberEdt;
    private EditText employeeNameEdt;

    private ArrayList<EmployeeDetails> employees= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_employee_activity, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getActionBar().setTitle("Add Employee");

        employeeDAO = new EmployeeDAO(this);
        employeeDAO.open();
        niNumberEdt = (EditText) findViewById(R.id.ni_number_edt);
        employeeNameEdt = (EditText) findViewById(R.id.employee_name_edt);
    }

    private boolean emptyField(EditText textField){
        if(textField.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Text fields are empty", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private boolean allowedNi(String niNumber) {
        employees.clear();
        employeeDAO.getEmployees(employees);
        if (niNumber.length() < 9) {
            Toast.makeText(getApplicationContext(),"NI number must be longer",Toast.LENGTH_LONG).show();
            return false;}
        else {
            for(int i=0;i<employees.size();i++){
                if(niNumberEdt.getText().toString().equals(employees.get(i).getNiNumber())){
                    Toast.makeText(getApplicationContext(),"NI number already exists",Toast.LENGTH_LONG).show();
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_item_save_employee) {
            if(!emptyField(niNumberEdt)&&!emptyField(employeeNameEdt)){
                if(allowedNi(niNumberEdt.getText().toString())) {
                    employeeDAO.addEmployee(niNumberEdt.getText().toString(),
                            employeeNameEdt.getText().toString());
                    Toast.makeText(getApplicationContext(),"Employee Added",Toast.LENGTH_LONG).show();
                    niNumberEdt.setText("");
                    employeeNameEdt.setText("");
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
