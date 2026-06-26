package com.lehuuquynhnhi.k234111e_mobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalculatorActivity extends AppCompatActivity {

    Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        addViews();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvents() {
        //anonymous listeners
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processFormula();
            }
        });
    }

    private void processFormula() {
        //step 1: get formula
        EditText edtFormula=findViewById(R.id.editTextText);
        String formula=edtFormula.getText().toString();

        //step 2: find api (lib) to calculate formula
        String result="";
        //result=...(formula)

        //step 3: show result
        edtFormula.setText(result);
    }

    private void addViews() {
        btnCalculate=findViewById(R.id.btnEqual);
    }

    public void processChooseValue(View view) {
        // get current view clicked
        Button btn= (Button) view;
        //get edtFormula
        EditText edtFormula=findViewById(R.id.editTextText);
        String old_value=edtFormula.getText().toString();
        String new_value=btn.getText().toString();
        edtFormula.setText(old_value+new_value);
    }
}