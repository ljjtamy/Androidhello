package com.example.androidhello1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CalculateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        // 获取传递的数据
        String itemTitle = getIntent().getStringExtra("ItemTitle");
        String priceStr = getIntent().getStringExtra("Price");
        double exchangeRate = Double.parseDouble(priceStr);

        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView priceTextView = findViewById(R.id.priceTextView);
        EditText rmbInput = findViewById(R.id.rmbInput);
        Button calculateBtn = findViewById(R.id.calculateBtn);
        TextView resultTextView = findViewById(R.id.resultTextView);

        titleTextView.setText("币种: " + itemTitle);
        priceTextView.setText("汇率: " + priceStr);

        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rmbAmountStr = rmbInput.getText().toString();
                if (!rmbAmountStr.isEmpty()) {
                    double rmbAmount = Double.parseDouble(rmbAmountStr);
                    double foreignAmount = rmbAmount / exchangeRate;
                    resultTextView.setText("可兑换" + itemTitle + "金额: " + foreignAmount);
                }
            }
        });
    }
}