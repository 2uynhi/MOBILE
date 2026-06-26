package com.lehuuquynhnhi.k234111e_mobile;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.lehuuquynhnhi.adapters.EmployeeAdapter;
import com.lehuuquynhnhi.models.Department;
import com.lehuuquynhnhi.models.Employee;

import java.util.ArrayList;
import java.util.Comparator;

public class EmployeeAdvancedManagementActivity extends AppCompatActivity {
    private void addEvents() {
        spDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Department selectedDepartment = listDepartment.get(i);
                adapterEmployee.clear();
                adapterEmployee.addAll(selectedDepartment.getListOfEmployee());
                adapterEmployee.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    ListView lvEmployee;
    ArrayList<Employee> listEmployee;
    EmployeeAdapter adapterEmployee;

    Spinner spDepartment;
    ArrayList<Department> listDepartment;
    ArrayAdapter<Department> adapterDepartment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_advanced_management);
        addViews();
        sampleData();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnuAllStatus) {
            Toast.makeText(this, R.string.mnu_all_status_order, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.mnuCompleted) {
            Toast.makeText(this, R.string.mnu_completed_order, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.mnuNotYetPayment) {
            Toast.makeText(this, R.string.mnu_not_yet_payment_order, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.mnuGoingLogistic) {
            Toast.makeText(this, R.string.mnu_going_logistic_order, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.mnuCustomerAngry) {
            Toast.makeText(this, R.string.mnu_customer_angry_order, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sampleData(){
        Department d1 = new Department("d1", "Phòng nhân sự");
        Department d2 = new Department("d2", "Phòng hành chính");
        Department d3 = new Department("d3", "Phòng tài chính");
        Department d4 = new Department("d4", "Phòng kế toán");
        listDepartment.add(d1);
        listDepartment.add(d2);
        listDepartment.add(d3);
        listDepartment.add(d4);
        d1.addEmployee(new Employee("e1", "tèo", "0348707251"));

        ArrayList<Employee> list1 = new ArrayList<>();
        list1.add(new Employee("e2", "tý", "083652816i"));
        list1.add(new Employee("e3", "bin", "0826151936"));
        d2.addListEmployee(list1);
        d2.addEmployee(new Employee("e4", "tí", "083652816i"));
        d4.addEmployee(new Employee("e5", "bon", "0826151936"));
        
        adapterDepartment.notifyDataSetChanged();
    }
    private void addViews(){
        lvEmployee = findViewById(R.id.lvEmployee);
        listEmployee = new ArrayList<>();

        adapterEmployee = new EmployeeAdapter(this, R.layout.employee_custom_item);
        lvEmployee.setAdapter(adapterEmployee);

        spDepartment = findViewById(R.id.spDepartment);
        listDepartment = new ArrayList<>();
        adapterDepartment = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listDepartment);
        adapterDepartment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDepartment.setAdapter(adapterDepartment);
    }
    
}