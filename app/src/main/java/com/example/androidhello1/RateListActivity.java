package com.example.androidhello1;

import static java.lang.System.in;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

@SuppressLint("HandlerLeak")
public class RateListActivity extends ListActivity {

    private static final String TAG = "RateListActivity";
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] list_data = {"one", "two", "three", "four"};
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list_data);
        setListAdapter(adapter);

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                // 接收返回的数据
                if (msg.what == 9) {
                    List<String> list2 = (List<String>) msg.obj;
                    ListAdapter adapter2 = new ArrayAdapter<String>(RateListActivity.this, android.R.layout.simple_list_item_1, list2);
                    setListAdapter(adapter2);
                }
                super.handleMessage(msg);
            }
        };

        Thread t = new Thread(() -> {
            // 获取数据，带回主线程
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // 带回汇率
            URL url = null;
            HttpURLConnection http;
            try {
                url = new URL("https://www.huilvbiao.com/bank/spdb");
                http = (HttpURLConnection) url.openConnection();
                InputStream in = http.getInputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String charset = getCharsetFromResponse(http);
            if (charset == null) {
                charset = "UTF-8"; // 默认使用 UTF-8
            }

            Document doc = null;
            try {
                doc = Jsoup.connect("https://www.huilvbiao.com/bank/spdb").get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Log.i(TAG, "run: title = " + doc.title());

            Element table = doc.select("table.table-bordered.table-sm.table-striped").first();

            List<String> list1 = new ArrayList<>();
            if (table != null) {
                Elements trs = table.getElementsByTag("tr");
                trs.remove(0);

                for (Element tr : trs) {
                    Elements tds = tr.children();
                    if (tds.size() >= 5) { // Make sure there are enough columns
                        Element td1 = tds.get(0); // Currency name
                        Element td2 = tds.get(2); // Selling rate

                        String currency = td1.text();
                        String sellingRate = td2.text();

                        list1.add(currency + ":" + sellingRate);
                        Log.i(TAG, "onCreate:" + currency + ":" + sellingRate);
                    }
                }
            }

            Log.i(TAG, "onCreate: 返回数据");


            Message msg = handler.obtainMessage(9, list1);
            handler.sendMessage(msg);
        });
        t.start();
    }

    private String inputStream2String(InputStream inputStream)
            throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "utf-8");
        while (true) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }

    private String getCharsetFromResponse(HttpURLConnection http) {
        String contentType = http.getContentType();
        if (contentType != null) {
            String[] parts = contentType.split(";");
            for (String part : parts) {
                part = part.trim();
                if (part.toLowerCase().startsWith("charset=")) {
                    return part.substring("charset=".length());
                }
            }
        }
        return null;
    }
}