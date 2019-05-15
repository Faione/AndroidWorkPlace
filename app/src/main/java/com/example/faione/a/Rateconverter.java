package com.example.faione.a;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Rateconverter extends AppCompatActivity implements Runnable{
    EditText rmb;
    TextView showrlt ;
    Handler handler;
    private float dollar_rate = 6.7f;
    private float pound_rate = 8.774f;
    private float yen_rate = 0.06014f;
    private float hk_rate = 0.8559f;
    private String updateDate = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rateconverter);
        rmb = findViewById(R.id.rmb);
        showrlt = findViewById(R.id.showrlt);

        SharedPreferences sharedPreferences = getSharedPreferences("Myrate", Activity.MODE_PRIVATE);
        dollar_rate = sharedPreferences.getFloat("dollar_rate",0.0f);
        pound_rate = sharedPreferences.getFloat("pound_rate",0.0f);
        yen_rate = sharedPreferences.getFloat("yen_rate",0.0f);
        hk_rate = sharedPreferences.getFloat("hk_rate",0.0f);
        updateDate = sharedPreferences.getString("update_date","");
        //获得系统当前时间
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        final String todayStr = sdf.format(today);


        Log.i("TAG","onCreate:sp dollar_rate="+dollar_rate);
        Log.i("TAG","onCreate:sp pound_rate="+pound_rate);
        Log.i("TAG","onCreate:sp yen_rate="+yen_rate);
        Log.i("TAG","onCreate:sp hk_rate="+hk_rate);
        Log.i("TAG","onCreate:sp updateDate="+updateDate);
        Log.i("TAG","onCreate:sp todayStr="+todayStr);

        if(!todayStr.equals(updateDate)){
            //开启子线程
            Thread olrate = new Thread(this);
            olrate.start();
            Log.i("TAG","需要更新");
        }else{
            Log.i("TAG","不需要更新");
        }

         handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what == 5){
                    Bundle bdl =(Bundle) msg.obj;
                    dollar_rate =bdl.getFloat("dollar-rate");
                    pound_rate =bdl.getFloat("pound-rate");
                    yen_rate =bdl.getFloat("yen-rate");
                    hk_rate =bdl.getFloat("hk-rate");

                    Log.i("TAG","handleMessage : dollar_rate="+dollar_rate);
                    Log.i("TAG","handleMessage : pound_rate="+pound_rate);
                    Log.i("TAG","handleMessage : yen_rate="+yen_rate);
                    Log.i("TAG","handleMessage : hk_rate="+hk_rate);

                    //保存更新日期
                    SharedPreferences sharedPreferences = getSharedPreferences("Myrate",Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("update_date",todayStr);
                    editor.apply();

                    Toast.makeText(Rateconverter.this,"汇率已更新",Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };
    }
    public void OnClick(View btn){
        String stn = rmb.getText().toString();
        float rmb = 0;
        if(stn.length()>0){
            rmb = Float.parseFloat(stn);
        }else{
            Toast.makeText(this,"请输入金额",Toast.LENGTH_SHORT).show();
        }
        if(btn.getId() == R.id.btn_Dollar ){
            float r = rmb * (1/ dollar_rate) ;
            showrlt.setText(String.format("%.2f",r));
        } else if (btn.getId() == R.id.btn_Pound ){
            float r = rmb * (1/ pound_rate) ;
            showrlt.setText(String.format("%.2f",r));
        }else if (btn.getId() == R.id.btn_HK ) {
            float r = rmb * (1/ hk_rate);
            showrlt.setText(String.format("%.2f", r));
        }else{
            float r = rmb * (1/ yen_rate) ;
            showrlt.setText(String.format("%.2f",r));
        }
    }
    public void openAnother(View btn){
        //Intent Second = new Intent(this,SecondActivity.class);
        //Intent Web = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.baidu.com"));
        //Intent Tel = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:8709213"));
        openConfig();
    }

    private void openConfig() {
        Intent Config = new Intent(this,Changerate.class);
        Config.putExtra("dollar_rate_key",dollar_rate);
        Config.putExtra("pound_rate_key",pound_rate);
        Config.putExtra("yen_rate_key",yen_rate);
        Config.putExtra("hk_rate_key",hk_rate);

        Log.i("TAG","openAnother : dollar_rate_key="+dollar_rate);
        Log.i("TAG","openAnother : pound_rate_key="+pound_rate);
        Log.i("TAG","openAnother : yen_rate_key="+yen_rate);
        Log.i("TAG","openAnother : hk_rate_key="+hk_rate);

        startActivityForResult(Config,1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_set){
            openConfig();
        }else if(item.getItemId()==R.id.open_list){
            //打开列表
            Intent list = new Intent(this,MyList2Activity.class);
            startActivity(list);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1&&resultCode == 2){
            Bundle bundle = data.getExtras();
            dollar_rate = bundle.getFloat("key_dollar",0.1f);
            pound_rate = bundle.getFloat("key_pound",0.1f);
            yen_rate = bundle.getFloat("key_yen",0.1f);
            hk_rate = bundle.getFloat("key_hk",0.1f);

            Log.i("TAG","onActivityResult: dollar_rate="+dollar_rate);
            Log.i("TAG","onActivityResult: pound_rate="+pound_rate);
            Log.i("TAG","onActivityResult: yen_rate="+yen_rate);
            Log.i("TAG","onActivityResult: hk_rate="+hk_rate);

            SharedPreferences sharedPreferences = getSharedPreferences("Myrate",Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("dollar_rate",dollar_rate);
            editor.putFloat("pound_rate",pound_rate);
            editor.putFloat("yen_rate",yen_rate);
            editor.putFloat("hk_rate",hk_rate);
            editor.commit();

            Log.i("TAG","onActivityResult sp :数据已保存到sp");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void run() {
            Log.i("TAG","run running");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

//        URL url = null;
//        try {
//            url = new URL("http://www.usd-cny.com/");
//            HttpURLConnection http = (HttpURLConnection) url.openConnection();
//            InputStream in = http.getInputStream();
//
//            String html = inputStreamToString(in);
//            Document doc = Jsoup.parse(html);
//            Log.i("TAG","run:html = "+html);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Bundle bundle = new Bundle();

        bundle = getFromBoc();

        Message msg = handler.obtainMessage(5);
        //msg.obj = "Hello from run()";
        msg.obj = bundle;
        handler.sendMessage(msg);

    }

    private Bundle getFromBoc() {
        Bundle bundle = new Bundle();
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            Log.i("TAG","run:"+doc.title());
            Elements tables = doc.getElementsByTag("table");

//            int i  =1;
//           for(Element table:tables ){
//              Log.i("TAG","run: table ["+i+"]=" + table);
//              i++;
//           }

            Element table1 = tables.get(0);
            Log.i("TAG","run: table =" );
            Elements tds = table1.getElementsByTag("td");

            for(int i=0;i<tds.size();i+=6){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+5);
                Log.i("TAG","run: MoneyType and Price " + td1.text()+ "==>"+td2.text());

                String mt = td1.text();
                String pr = td2.text();

                if("美元".equals(mt)){
                    bundle.putFloat("dollar-rate",Float.parseFloat((pr))/100f);
                }else if ("英镑".equals(mt)){
                    bundle.putFloat("pound-rate",Float.parseFloat((pr))/100f);
                }else if ("日元".equals(mt)){
                    bundle.putFloat("yen-rate",Float.parseFloat((pr))/100f);
                }else if ("港币".equals(mt)){
                    bundle.putFloat("hk-rate",Float.parseFloat((pr))/100f);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bundle;
    }

    private Bundle getFromUSDCNY() {
        Bundle bundle = new Bundle();
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            Log.i("TAG","run:"+doc.title());
            Elements tables = doc.getElementsByTag("table");

//            int i  =1;
//           for(Element table:tables ){
//              Log.i("TAG","run: table ["+i+"]=" + table);
//              i++;
//           }

            Element table1 = tables.get(0);
            Log.i("TAG","run: table =" );
            Elements tds = table1.getElementsByTag("td");

            for(int i=0;i<tds.size();i+=6){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+5);
                Log.i("TAG","run: MoneyType and Price " + td1.text()+ "==>"+td2.text());

                String mt = td1.text();
                String pr = td2.text();

                if("美元".equals(mt)){
                    bundle.putFloat("dollar-rate",Float.parseFloat((pr))/100f);
                }else if ("英镑".equals(mt)){
                    bundle.putFloat("pound-rate",Float.parseFloat((pr))/100f);
                }else if ("日元".equals(mt)){
                    bundle.putFloat("yen-rate",Float.parseFloat((pr))/100f);
                }else if ("港币".equals(mt)){
                    bundle.putFloat("hk-rate",Float.parseFloat((pr))/100f);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bundle;
    }
    private String inputStreamToString (InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        for (; ; ) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }

}
