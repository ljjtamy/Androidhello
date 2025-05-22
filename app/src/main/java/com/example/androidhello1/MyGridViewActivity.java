package com.example.androidhello1;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gird_view);

        ListView mylist = findViewById(R.id.mylist2);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        List<String> list_data = new ArrayList<>(100);
        for (int i=1; i<100; i++){
            list_data.add("Item" + i);
        }

//        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list_data);
//        mylist.setAdapter(adapter);

        Handler handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg){
                if(msg.what==5) {
                    Bundle bundle = (Bundle) msg.obj;
                    ArrayList<String> retlist = bundle.getStringArrayList("mylist");
                    ListAdapter adapter = new ArrayAdapter<String>(MyGridViewActivity.this, android.R.layout.simple_list_item_1, retlist);

                    //绑定
                    mylist.setAdapter(adapter);
                    //显示
                    mylist.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
                    super.handleMessage(msg);
                }
            };

        Thread t = new Thread(new MyTask(handler));
        t.start();
        Log.i(TAG, "onCreate: 启动线程");


    }

    public class MyTask implements Runnable {
        private Handler handler;

        public MyTask(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            // 模拟耗时操作
            try {
                Thread.sleep(2000); // 延迟2秒

                // 创建数据
                ArrayList<String> resultList = new ArrayList<>();
                for (int i = 1; i <= 20; i++) {
                    resultList.add("New Item " + i);
                }

                // 将数据封装到 Bundle
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("mylist", resultList);

                // 创建 Message 并发送到主线程
                Message msg = Message.obtain();
                msg.what = 5;
                msg.obj = bundle;
                handler.sendMessage(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}