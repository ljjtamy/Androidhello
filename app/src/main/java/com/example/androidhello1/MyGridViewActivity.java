package com.example.androidhello1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MyGridViewActivity extends AppCompatActivity {
    private static final String TAG = "grid";
    private ExchangeRateDBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gird_view);

        dbHelper = new ExchangeRateDBHelper(this);
        database = dbHelper.getReadableDatabase();

        ListView mylist = findViewById(R.id.mylist2);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        Handler handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg){
                if(msg.what==5) {
                    ArrayList<String> retlist = new ArrayList<>();
                    Cursor cursor = database.query(
                            ExchangeRateDBHelper.TABLE_RATES,
                            new String[]{ExchangeRateDBHelper.COLUMN_CURRENCY, ExchangeRateDBHelper.COLUMN_RATE},
                            null, null, null, null, null);

                    while (cursor.moveToNext()) {
                        retlist.add(cursor.getString(0) + ": " + cursor.getString(1));
                    }
                    cursor.close();

                    ListAdapter adapter = new ArrayAdapter<String>(MyGridViewActivity.this, 
                            android.R.layout.simple_list_item_1, retlist);
                    mylist.setAdapter(adapter);
                    mylist.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
                super.handleMessage(msg);
            }
        };

        Message msg = Message.obtain();
        msg.what = 5;
        handler.sendMessage(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
        dbHelper.close();
    }
}