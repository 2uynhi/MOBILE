package com.lehuuquynhnhi.k234111e_mobile;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class EmployeeManagementActivity extends AppCompatActivity {

    EditText edtEmployeeId, edtEmployeeName, edtPhoneNumber;
    ListView lvEmployee;
    ArrayList<String> listEmployee;
    ArrayAdapter<String> adapterEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_management);
        
        addViews();
        addEvents();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addViews() {
        edtEmployeeId = findViewById(R.id.editTextText2);
        edtEmployeeName = findViewById(R.id.editTextText3);
        edtPhoneNumber = findViewById(R.id.editTextPhone);
        lvEmployee = findViewById(R.id.lvEmployee);

        listEmployee = new ArrayList<>();
        listEmployee.add("e1-tèo-0348707251");
        listEmployee.add("e2-tý-083652816i");
        listEmployee.add("e3-bin-0826151936");

        adapterEmployee = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listEmployee);
        lvEmployee.setAdapter(adapterEmployee);
    }

    private void addEvents() {
        lvEmployee.setOnItemClickListener((parent, view, position, id) -> displayEmployeeInfo(position));
    }

    private void displayEmployeeInfo(int position) {
        String text = listEmployee.get(position);
        String[] arr = text.split("-");
        if (arr.length >= 3) {
            edtEmployeeId.setText(arr[0]);
            edtEmployeeName.setText(arr[1]);
            edtPhoneNumber.setText(arr[2]);
        }
    }

    public void saveEmployee(View view) {
        String id = edtEmployeeId.getText().toString().trim();
        String name = edtEmployeeName.getText().toString().trim();
        String phone = edtPhoneNumber.getText().toString().trim();
        
        if (id.isEmpty()) return;

        String text = id + "-" + name + "-" + phone;
        boolean isExisted = false;

        // Kiểm tra xem ID đã tồn tại trong danh sách chưa
        for (int i = 0; i < listEmployee.size(); i++) {
            String currentItem = listEmployee.get(i);
            String[] arr = currentItem.split("-");
            
            // Nếu tìm thấy ID trùng khớp
            if (arr[0].equalsIgnoreCase(id)) {
                listEmployee.set(i, text); // Chỉnh sửa/Cập nhật
                isExisted = true;
                break;
            }
        }

        // Nếu không tìm thấy ID trùng -> Thêm mới
        if (!isExisted) {
            listEmployee.add(text);
        }

        adapterEmployee.notifyDataSetChanged();
        Toast.makeText(this, getString(R.string.str_save_success), Toast.LENGTH_SHORT).show();
        
        // Clear fields
        edtEmployeeId.setText("");
        edtEmployeeName.setText("");
        edtPhoneNumber.setText("");
        edtEmployeeId.requestFocus();
    }

    public void deleteEmployee(View view) {
        String id = edtEmployeeId.getText().toString().trim();
        
        // nếu chưa chọn employee thì dùng Toast để thông báo cần chọn 
        if (id.isEmpty()) {
            Toast.makeText(this, getString(R.string.str_select_to_delete), Toast.LENGTH_SHORT).show();
            return;
        }

        // nếu đang chọn employee thì dùng AlertDialog để xác thực có muốn xóa không
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.str_confirm_delete));
        builder.setMessage(getString(R.string.str_delete_message));
        builder.setIcon(android.R.drawable.ic_delete);

        builder.setPositiveButton(getString(R.string.str_yes), (dialog, which) -> {
            boolean isDeleted = false;
            // Xử lý xóa trong danh sách
            for (int i = 0; i < listEmployee.size(); i++) {
                String[] arr = listEmployee.get(i).split("-");
                if (arr.length > 0 && arr[0].equalsIgnoreCase(id)) {
                    listEmployee.remove(i);
                    isDeleted = true;
                    break;
                }
            }
            
            if (isDeleted) {
                // cập nhật trên ListView
                adapterEmployee.notifyDataSetChanged();
                
                // Xóa trắng các ô nhập sau khi xóa thành công
                edtEmployeeId.setText("");
                edtEmployeeName.setText("");
                edtPhoneNumber.setText("");
                edtEmployeeId.requestFocus();
                
                Toast.makeText(this, getString(R.string.str_delete_success), Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton(getString(R.string.str_no), (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    public void closeEmployeeActivity(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.str_confirm_exit));
        
        View dialogView = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        
        ImageView imgYes = dialogView.findViewById(R.id.imgYes);
        ImageView imgCancel = dialogView.findViewById(R.id.imgCancel);

        if (imgYes != null) {
            imgYes.setOnClickListener(v -> {
                dialog.dismiss();
                finish();
            });
        }
        
        if (imgCancel != null) {
            imgCancel.setOnClickListener(v -> dialog.dismiss());
        }

        dialog.show();
    }
}
