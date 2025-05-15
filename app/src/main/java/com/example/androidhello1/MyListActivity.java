package com.example.androidhello1;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;


import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.androidhello1.CalculateActivity;

import com.example.androidhello1.MyAdapter;

public class MyListActivity extends ListActivity implements AdapterView.OnItemClickListener{

    private static final String TAG = "MyListActivity";
    private ArrayList<HashMap<String, String>> listItems;
    private SimpleAdapter listItemAdapter;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initlistview();

        setListAdapter(listItemAdapter);

        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==9){
                    Log.i(TAG, "handleMessage: what=" + msg.what);
                    listItems = (ArrayList<HashMap<String,String>>)msg.obj;
//                    listItemAdapter = new SimpleAdapter(MyListActivity.this, listItems,//listItems 数据源
//                            R.layout.list_item,//ListItemlXML布局实现
//                            new String[]{"ItemTitle", "Price"},
//                            new int[]{R.id.itemTitle, R.id.price}
//                    );
                    MyAdapter adapter = new MyAdapter(MyListActivity.this,R.layout.list_item,listItems);
                    setListAdapter(adapter);

                }
                super.handleMessage(msg);
            }
        };

        getListView().setOnItemClickListener(this);

        //定义一个线程获取数据
        Thread t = new Thread(()->{
            ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
            try {
                Document doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
                Elements tables = doc.getElementsByTag("table");
                Element table = tables.get(1);
                Elements trs = table.getElementsByTag("tr");
                trs.remove(0);
                for(Element tr: trs){
                    Elements tds = tr.children();
                    Element td1 = tds.first();
                    Element td2 = tds.get(5);
                    String str1 = td1.text();
                    String str2 = td2.text();

                    Log.i(TAG, "onCreate: run:td1=" + str1 + "->" + str2);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("ItemTitle", str1); //币种
                    map.put("Price", str2); //价格
                    list.add(map);

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Message msg = handler.obtainMessage(9,list);
            handler.sendMessage(msg);

        });
        t.start();
    }

    private void initlistview() {
        listItems = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", "Rate: " + i);
            map.put("Price", "detail" + i);
            listItems.add(map);
        }
        //生成适配器的Item和动态数组对应的元素
        listItemAdapter = new SimpleAdapter(this, listItems,//listItems 数据源
                R.layout.list_item,//ListItemlXML布局实现
                new String[]{"ItemTitle", "Price"},
                new int[]{R.id.itemTitle, R.id.price}
        );
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String,String>  map = (HashMap<String, String>)getListView().getItemAtPosition(position);
        String titleStr = map.get("ItemTitle");
        String priceStr = map.get("Price");

        // 跳转到详细页面
        Intent intent = new Intent(MyListActivity.this, CalculateActivity.class);
        intent.putExtra("ItemTitle", titleStr);
        intent.putExtra("Price", priceStr);
        startActivity(intent);

        Log.i(TAG,"onItemClick: titlestr="+ titleStr);
        Log.i(TAG,"onItemClick: detailstr="+ priceStr);
        Log.i(TAG,"onItemClick: positionr="+ position);
    }
}