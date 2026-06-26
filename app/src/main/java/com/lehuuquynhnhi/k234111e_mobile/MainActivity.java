package com.lehuuquynhnhi.k234111e_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView txtHeader;
    Button btnClickMe, btnOpenCalculator, btnSmsSpyware, btnMultiThreading, btnMultiThreadingObject, btnFontAndMusic, btnProductCatalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addViews();
        addEvents();
    }

    private void addViews() {
        txtHeader = findViewById(R.id.txtHeader);
        btnClickMe = findViewById(R.id.btnClickMe);
        btnOpenCalculator = findViewById(R.id.btnOpenCalculator);
        btnSmsSpyware = findViewById(R.id.btnSmsSpyware);
        btnMultiThreading = findViewById(R.id.btnMultiThreading);
        btnMultiThreadingObject = findViewById(R.id.btnMultiThreadingObject);
        btnFontAndMusic = findViewById(R.id.btnFontAndMusic);
        btnProductCatalog = findViewById(R.id.btnProductCatalog);

        Intent intent = getIntent();
        String user = intent.getStringExtra("USER_NAME");
        if (user != null) {
            txtHeader.setText("Welcome " + user);
        }
    }

    private void addEvents() {
        btnClickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewsGoogleActivity.class);
                startActivity(intent);
            }
        });

        btnOpenCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
                startActivity(intent);
            }
        });

        btnSmsSpyware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SMSSpyWareActivity.class);
                startActivity(intent);
            }
        });

        btnMultiThreading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MultiThreadingActivity.class);
                startActivity(intent);
            }
        });

        btnMultiThreadingObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Giả sử có activity này, nếu chưa có sẽ báo lỗi compile
                // Intent intent = new Intent(MainActivity.this, MultiThreadingObjectActivity.class);
                // startActivity(intent);
                Toast.makeText(MainActivity.this, "Feature coming soon!", Toast.LENGTH_SHORT).show();
            }
        });

        btnFontAndMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FontAndMusicActivity.class);
                startActivity(intent);
            }
        });

        btnProductCatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CatalogCategoriesActivity.class);
                startActivity(intent);
            }
        });
    }
}
