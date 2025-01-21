package com.example.zakatapp;

import static java.lang.String.format;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // components
        Spinner spinnerGoldType = findViewById(R.id.spinnerGoldType);
              //  String[] goldTypes = getResources().getStringArray(R.array.gold_types);
        EditText weightGold = findViewById(R.id.weightGold);
        EditText currentGold = findViewById(R.id.currentGold);
        Button btnCalculate = findViewById(R.id.btnCalculate);
        Button btnReset = findViewById(R.id.btnReset);
        Button btnBack = findViewById(R.id.btnBack);
        TextView goldValue = findViewById(R.id.goldValue);
        TextView zakatPayable = findViewById(R.id.zakatPayable);
        TextView totalZakat = findViewById(R.id.totalZakat);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // dropdown
        String[] goldTypes = {"Keep", "Wear"};
        final int[] nisab = {85}; //keep

        // Adapter for the Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, goldTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGoldType.setAdapter(adapter);

        // Listener for Spinner selection
        spinnerGoldType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    // keep
                    nisab[0] = 85;
                } else if (position == 1) {
                    //wear
                    nisab[0] = 200;

                }
//                String selectedType = goldTypes[position];
//                Toast.makeText(MainActivity.this, "Selected: " + selectedType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        //calculation
        btnCalculate.setOnClickListener(v -> {
            try {
                //input
                double weight = Double.parseDouble(weightGold.getText().toString());
                double goldValuePerGram = Double.parseDouble(currentGold.getText().toString());

                //calculate
                double totalGoldValue = weight * goldValuePerGram;
                double zakatPayableWeight = Math.max(0, weight - nisab[0]);
                double zakatPayableValue = zakatPayableWeight * goldValuePerGram;
                double totalZakatValue = zakatPayableValue * 0.025; //2.5%

                //display result
               goldValue.setText(String.format("Gold value: RM %.2f", totalGoldValue));
               zakatPayable.setText(String.format("Zakat Payable Value: RM %.2f", zakatPayableValue));
               totalZakat.setText(String.format("Total Zakat: RM %.2f", totalZakatValue));
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Please enter the valid numeric value!", Toast.LENGTH_SHORT).show();
            }
        });

        //reset
        btnReset.setOnClickListener(v -> {
            //clear input
            weightGold.setText("");
            currentGold.setText("");
            spinnerGoldType.setSelection(0);

            //clear output
            goldValue.setText("Gold Value:");
            zakatPayable.setText("Zakat Payable Value:");
            totalZakat.setText("Total Zakat:");


        });

    }
}