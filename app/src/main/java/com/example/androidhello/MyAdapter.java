package com.example.androidhello1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapter extends ArrayAdapter {
    private static final String TAG = "MyAdapter";

    public MyAdapter( Context context, int resource, ArrayList<HashMap<String,String>> list) {
        super(context, resource, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView= convertView;
        if(itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Map<String, String> map = (Map<String, String>) getItem(position);
        TextView title = itemView.findViewById(R.id.itemTitle);
        TextView price = itemView.findViewById(R.id.price);
        title.setText("Title:" + map.get("ItemTitle"));
        price.setText("Price:" + map.get("Price"));
        return itemView;

    }
}
