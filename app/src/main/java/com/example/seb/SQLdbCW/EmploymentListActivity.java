package com.example.seb.SQLdbCW;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class EmploymentListActivity extends Activity {
    private ListView employmentListView;
    private TextView employeeDetails;
    private TextView employeeFinanceDetails;
    private EmployeeDAO employeeDAO;
    private List<Employment> employmentList = new ArrayList<>();
    private ArrayAdapter<Employment> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employment_list);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_employment_list_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_item_add_employment) {
            Intent empIntent = getIntent();
            String employee = empIntent.getStringExtra(Constants.EMP_KEY);
            Intent intent = new Intent(this, AddEmploymentActivity.class);
            intent.putExtra(Constants.EMP_KEY,employee);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        getActionBar().setTitle("Employment List");
        double totalWeekly = 0;
        double totalNIC= 0;
        String contributing = "No";

        Intent intent = getIntent();
        String employee = intent.getStringExtra(Constants.EMP_KEY);

        employmentListView = (ListView) findViewById(R.id.employment_list_view);
        adapter = new ArrayAdapter<Employment>(this,R.layout.custom_text_view, employmentList);
        employmentListView.setAdapter(adapter);

        employmentList.clear();
        employeeDAO = new EmployeeDAO(this);
        employeeDAO.open();
        employeeDAO.getEmployments(employmentList,employee);
        adapter.notifyDataSetChanged();

        for(int i=0;i<employmentList.size();i++){
            double weeklyEarn=employmentList.get(i).getGrossWeekly();

            if(weeklyEarn>112){
                contributing= "Yes"; }

            totalWeekly +=  weeklyEarn;
            totalNIC+= employmentList.get(i).niPayable(weeklyEarn);
        }
        employeeFinanceDetails = (TextView) findViewById(R.id.emp_finance_info_txt_view);
        employeeFinanceDetails.setText("NI Number: " + employee + "\nTotal Weekly(£): "+ totalWeekly +
                "\nNIC paid (£): " + totalNIC+ "   Contributing: "+ contributing);

        employmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UpdateEmployment.tempEmployment = employmentList.get(position);

                Intent intent = new Intent(EmploymentListActivity.this, UpdateEmployment.class);
                startActivity(intent);
            }
        });
    }
}
