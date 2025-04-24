package com.example.androidhello1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingActivity extends AppCompatActivity {

    private static final String TAG = "SettingActivity";
    private EditText inpDollar,inpEuro,inpWon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inpDollar = findViewById(R.id.dollar_rate);
        inpEuro = findViewById(R.id.euro_rate);
        inpWon = findViewById(R.id.won_rate);

        //获取传入数据
        Intent intent = getIntent();
        float dollar = intent.getFloatExtra("key_dollar",0.1f);
        float euro = intent.getFloatExtra("key_euro",0.1f);
        float won = intent.getFloatExtra("key_won",0.1f);

        Log.i(TAG, "onCreate: dollar" + dollar);
        Log.i(TAG, "onCreate: euro" + euro);
        Log.i(TAG, "onCreate: won" + won);

        //在控件中显示传入的数据
        inpDollar.setText(String.valueOf(dollar));
        inpEuro.setText(String.valueOf(euro));
        inpWon.setText(String.valueOf(won));

    }

    //

    public void save(View btn){
        //获取新的汇率
        String dollarStr = inpDollar.getText().toString();
        String euroStr = inpEuro.getText().toString();
        String wonStr = inpWon.getText().toString();

        Log.i(TAG, "save: dollarStr" + dollarStr);
        Log.i(TAG, "save: euroStr" + euroStr);
        Log.i(TAG, "save: wonStr" + wonStr);

        //转String -> float
        try {
            float dollar = Float.parseFloat(dollarStr);
            float euro = Float.parseFloat(euroStr);
            float won = Float.parseFloat(wonStr);

            //带回数据
            Intent retIntent = getIntent();
            Bundle bdl = new Bundle();
            bdl.putFloat("ret_dollar",dollar);
            bdl.putFloat("ret_euro",euro);
            bdl.putFloat("ret_won",won);
            retIntent.putExtras(bdl);
            setResult(3,retIntent);

            finish();
        } catch (NumberFormatException e) {
            Log.e(TAG, "save: 出错了", e);
        }

    }
}