package com.example.androidhello1;

import android.util.Log;
import android.widget.TextView;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ScoringActivity extends AppCompatActivity {

    private static final String TAG = "ScoringActivity";
    private TextView scoring1;
    private TextView scoring2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scoring);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        scoring1 = findViewById(R.id.TeamA_score);
        scoring2 = findViewById(R.id.TeamB_score);
    }

    public void click(View btn){
        Log.i(TAG, "click: 11111111");
        //获取原有分数
        String s1 = (String) scoring1.getText();
        String s2 = (String) scoring2.getText();

        //String -> int
        int ints1 = Integer.parseInt(s1);
        int ints2 = Integer.parseInt(s2);
        Log.i(TAG, "click: ints1=" + ints1);
        Log.i(TAG, "click: ints2=" + ints2);

        if(btn.getId()==R.id.TeamA_add3){
            ints1 += 3;
        }
        else if(btn.getId()==R.id.TeamA_add2){
            ints1 += 2;
        }
        else if(btn.getId()==R.id.TeamA_add1){
            ints1 += 1;
        }
        else if(btn.getId()==R.id.TeamB_add3){
            ints2 += 3;
        }
        else if(btn.getId()==R.id.TeamB_add2) {
            ints2 += 2;
        }
        else if(btn.getId()==R.id.TeamB_add1){
            ints2 += 1;
        }
        //显示结果
        scoring1.setText(String.valueOf(ints1));
        scoring2.setText(String.valueOf(ints2));
    }
}
