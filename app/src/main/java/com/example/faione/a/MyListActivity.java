package com.example.faione.a;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MyListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
 List<String> data = new ArrayList<String>();
 private  String TAG = "Mylist";
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        ListView listView = findViewById(R.id.mylist);

       for(int i = 0 ;i<10;i++){
           data.add("Item"+ i);
        }

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.nodata));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> listV, View view, int position, long id) {
        Log.i(TAG,"onItemClick: position=" + position);
        Log.i(TAG,"onItemClick: parent=" + listV);
        adapter.remove(listV.getItemAtPosition(position));
        //默认使用 adapter.notifyDataSetChanged();
    }
}
