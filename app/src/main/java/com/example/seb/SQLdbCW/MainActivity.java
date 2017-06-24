package com.example.seb.SQLdbCW;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private ListView employeeListView;
    private EmployeeDAO employeeDAO;
    private List<EmployeeDetails> employeeList = new ArrayList<>();
    private ArrayAdapter<EmployeeDetails> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getActionBar().setTitle("Employees List");
        employeeListView = (ListView) findViewById(R.id.employee_list_view);
        adapter = new ArrayAdapter<EmployeeDetails>(this,R.layout.custom_text_view, employeeList);
        employeeListView.setAdapter(adapter);

        employeeList.clear();
        employeeDAO = new EmployeeDAO(this);
        employeeDAO.open();
        employeeDAO.getEmployees(employeeList);
        adapter.notifyDataSetChanged();

        employeeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EmployeeDetails employee = employeeList.get(position);
                Intent intent = new Intent(getApplicationContext(), EmploymentListActivity.class);
                intent.putExtra(Constants.EMP_KEY,employee.getNiNumber());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_item_add_employee) {
            Intent intent = new Intent(this, AddEmployeeActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
