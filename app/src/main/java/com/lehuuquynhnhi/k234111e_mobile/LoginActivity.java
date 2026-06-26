package com.lehuuquynhnhi.k234111e_mobile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUserName, edtPassword;
    private TextView txtMessage;
    private CheckBox chkSaveInfor;
    private RadioButton radAdmin, radEmployee;
    private Button btnLogin;

    private static final String SHARED_PREF_NAME = "loginInfor";
    private static final int REQUEST_PERMISSION_CODE = 100;

    // BroadcastReceiver to monitor internet state
    BroadcastReceiver internetStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Bất kỳ khi nào internet/mobile data change state tự bay vào đây
            String action = intent.getAction();
            if (action != null && action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                if (btnLogin != null) {
                    btnLogin.setEnabled(isConnected);
                }

                if (txtMessage != null) {
                    if (isConnected) {
                        txtMessage.setText("");
                    } else {
                        txtMessage.setText("Không có kết nối mạng!");
                        txtMessage.setTextColor(Color.RED);
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addControls();
        // copyDatabase(); 
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSavedInfor();
        
        // Register BroadcastReceiver
        IntentFilter internetFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(internetStateReceiver, internetFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister BroadcastReceiver
        if (internetStateReceiver != null) {
            unregisterReceiver(internetStateReceiver);
        }
    }

    /**
     * Kiểm tra và yêu cầu cấp quyền
     */
    private void checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CALL_PHONE},
                    REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Quyền gọi điện đã được cấp", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Ứng dụng cần quyền gọi điện để hoạt động đầy đủ", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Khởi tạo các thành phần giao diện
     */
    private void addControls() {
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        txtMessage = findViewById(R.id.txtMessage);
        chkSaveInfor = findViewById(R.id.chkSaveInfor);
        radAdmin = findViewById(R.id.radAdmin);
        radEmployee = findViewById(R.id.radEmployee);
        btnLogin = findViewById(R.id.btnLogin);
    }

    /**
     * Tải thông tin đăng nhập đã lưu từ SharedPreferences
     */
    private void loadSavedInfor() {
        SharedPreferences preferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        boolean isSaved = preferences.getBoolean("Saved", false);
        if (isSaved) {
            edtUserName.setText(preferences.getString("UserName", ""));
            edtPassword.setText(preferences.getString("Password", ""));
        }
        chkSaveInfor.setChecked(isSaved);
    }

    /**
     * Lưu thông tin đăng nhập vào SharedPreferences
     */
    private void saveLoginInfor(String username, String password, boolean isSaved) {
        SharedPreferences preferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (isSaved) {
            editor.putString("UserName", username);
            editor.putString("Password", password);
        } else {
            editor.remove("UserName");
            editor.remove("Password");
        }
        editor.putBoolean("Saved", isSaved);
        editor.apply();
    }

    /**
     * Xử lý sự kiện khi nhấn nút Đăng nhập
     */
    public void loginSystem(View view) {
        String username = edtUserName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        boolean isSaved = chkSaveInfor.isChecked();

        // Kiểm tra logic đăng nhập đơn giản
        if (username.equalsIgnoreCase("admin") && password.equals("123")) {
            txtMessage.setText(R.string.str_save_success);
            txtMessage.setTextColor(Color.BLUE);

            saveLoginInfor(username, password, isSaved);

            Intent intent;
            if (radAdmin.isChecked()) {
                intent = new Intent(LoginActivity.this, MainActivity.class);
            } else {
                intent = new Intent(LoginActivity.this, EmployeeAdvancedManagementActivity.class);
            }
            intent.putExtra("USER_NAME", username);
            startActivity(intent);

            String welcomeMsg = getString(R.string.str_welcome).trim() + " " + username;
            Toast.makeText(this, welcomeMsg, Toast.LENGTH_SHORT).show();
        } else {
            txtMessage.setText(R.string.str_invalid_login);
            txtMessage.setTextColor(Color.RED);
        }
    }

    /**
     * Xử lý sự kiện khi nhấn nút Thoát với hộp thoại xác nhận
     */
    public void exitSystem(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.str_confirm_exit);
        builder.setMessage(R.string.str_ask_exit);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton(R.string.str_yes, (dialog, which) -> finish());
        builder.setNegativeButton(R.string.str_no, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
