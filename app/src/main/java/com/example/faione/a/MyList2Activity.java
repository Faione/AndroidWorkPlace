package com.example.faione.a;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyList2Activity extends ListActivity implements Runnable ,AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {
    private String TAG = "mylist2";
    Handler handler;
    private List<HashMap<String, String>> listItems;
    private SimpleAdapter listItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
        this.setListAdapter(listItemAdapter);

        Thread t = new Thread(this);
        t.start();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==7){
                    listItems =(List<HashMap<String,String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(MyList2Activity.this,listItems,
                            R.layout.activity_my_list2,
                            new String[]{"ItemTitle","ItemDetail"},
                            new int[]{R.id.itemTitle,R.id.itemDetail}
                    );
                    setListAdapter(listItemAdapter);
                }
                super.handleMessage(msg);
            }
        };
        getListView().setOnItemClickListener(this);
        getListView().setOnItemLongClickListener(this);
        }

    private void initListView() {
        listItems = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", "Rate:  " + i);
            map.put("ItemDetail", "detail" + i);
            listItems.add(map);
        }
        listItemAdapter = new SimpleAdapter(this,listItems,
                R.layout.activity_my_list2,
                new String[]{"ItemTitle","ItemDetail"},
                new int[]{R.id.itemTitle,R.id.itemDetail}
        );
    }

    @Override
    public void run() {
        List<HashMap<String,String>> retlist = new ArrayList<HashMap<String,String>>();
        Document doc = null;
        try {
            Thread.sleep(10);
            doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            Log.i("TAG","run:"+doc.title());
            Elements tables = doc.getElementsByTag("table");
            Element table1 = tables.get(0);
            Log.i("TAG","run: table =" );
            Elements tds = table1.getElementsByTag("td");

            for(int i=0;i<tds.size();i+=6){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+5);


                String mt = td1.text();
                String pr = td2.text();
                Log.i("TAG","run: MoneyType and Price " + mt+ "==>"+ pr);

                HashMap<String,String> map = new  HashMap<String,String>();
                map.put("ItemTitle",mt);
                map.put("ItemDetail",pr);
                retlist.add(map);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Message msg = handler.obtainMessage(7);
        msg.obj = retlist;
        handler.sendMessage(msg);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG,"onItemClick: parent=" + parent);
        Log.i(TAG,"onItemClick: View=" + view);
        Log.i(TAG,"onItemClick: position=" + position);
        Log.i(TAG,"onItemClick: id=" + id);
        HashMap<String,String> map = ( HashMap<String,String>)getListView().getItemAtPosition(position);
        String titleStr = map.get("ItemTitle");
        String detailStr = map.get("ItemDetail");
        Log.i(TAG,"onItemClick: titleStr=" + titleStr);
        Log.i(TAG,"onItemClick: detailStr=" + detailStr);

        Intent rateCal = new Intent(this,RateCalActivity.class);
        rateCal.putExtra("title",titleStr);
        rateCal.putExtra("rate",Float.parseFloat(detailStr));
        startActivity(rateCal);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        Log.i(TAG,"onItemLongClick: 长按列表项=" + position );
       // listItems.remove(position);
        //listItemAdapter.notifyDataSetChanged();
        Log.i(TAG,"onItemLongClick: size = " + listItems.size());
        //构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("请确认是否删除当前数据").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG,"OnClick: 对话框事件处理");
                listItems.remove(position);
                listItemAdapter.notifyDataSetChanged();
            }
        }).setNegativeButton("否",null);
        builder.create().show();
        return true;
    }
}
