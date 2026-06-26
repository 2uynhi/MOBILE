package com.lehuuquynhnhi.k234111e_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.lehuuquynhnhi.models.Employee;

public class AddEmployeeActivity extends AppCompatActivity {

    EditText edtId, edtName, edtPhone;
    AutoCompleteTextView actBirthplace;
    ArrayAdapter<String> adapterBirthplace;
    ImageView imgSave, imgCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_employee);
        addViews();
        addEvents();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvents() {
        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processAddEmployee();
            }
        });
    }

    private void processAddEmployee() {
        String id=edtId.getText().toString();
        String name=edtName.getText().toString();
        String phone=edtPhone.getText().toString();
        String birthplace=actBirthplace.getText().toString();

        Employee emp=new Employee(id,name,phone,birthplace);
        Intent intent=getIntent();
        intent.putExtra("NEW_EMPLOYEE", emp);
        //đánh dấu gói tin gửi về với mã là 888 (result code)
        setResult(888,intent);
        //phải đóng màn hình này lại
        //vì màn hình kia cần có chế độ Forefround life (onresume phải thực thi)
        //thì mới tương tác lấy data đc
        finish();
    }

    private void addViews() {
        edtId=findViewById(R.id.edtId);
        edtName=findViewById(R.id.edtName);
        edtPhone=findViewById(R.id.edtPhone);
        actBirthplace=findViewById(R.id.actBirthplace);
        adapterBirthplace=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        String []arrBirthplace=getResources().getStringArray(R.array.list_birthplace);
        adapterBirthplace.addAll(arrBirthplace);
        actBirthplace.setAdapter(adapterBirthplace);

        imgSave=findViewById(R.id.imgSave);
        imgCancel=findViewById(R.id.imgCancel);
    }



}