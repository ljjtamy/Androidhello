package com.example.androidhello1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText heightEditText;
    private EditText weightEditText;
    private Button calculateButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        heightEditText = findViewById(R.id.heightEditText);
        weightEditText = findViewById(R.id.weightEditText);
        calculateButton = findViewById(R.id.calculateButton);
        resultTextView = findViewById(R.id.resultTextView);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });
    }

    private void calculateBMI() {
        String heightStr = heightEditText.getText().toString();
        String weightStr = weightEditText.getText().toString();

        if (!heightStr.isEmpty() && !weightStr.isEmpty()) {
            double height = Double.parseDouble(heightStr) / 100; // 将厘米转换为米
            double weight = Double.parseDouble(weightStr);

            double bmi = weight / (height * height);

            DecimalFormat df = new DecimalFormat("#.00");
            String bmiResult = df.format(bmi);

            String healthAdvice = getHealthAdvice(bmi);

            String resultMessage = "你的 BMI 值是: " + bmiResult + "\n健康建议: " + healthAdvice;
            resultTextView.setText(resultMessage);
        } else {
            resultTextView.setText("请输入有效的身高和体重数据。");
        }
    }

    private String getHealthAdvice(double bmi) {
        if (bmi < 18.5) {
            return "你体重过轻，建议增加营养摄入，适当进行力量训练。";
        } else if (bmi >= 18.5 && bmi < 24) {
            return "你的体重正常，继续保持健康的生活方式。";
        } else if (bmi >= 24 && bmi < 28) {
            return "你体重过重，建议控制饮食，增加运动量。";
        } else {
            return "你属于肥胖，需要严格控制饮食，加强锻炼，并考虑咨询医生。";
        }
    }
}