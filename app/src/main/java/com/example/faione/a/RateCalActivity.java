package com.example.faione.a;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RateCalActivity extends AppCompatActivity {
    String TAG = "rateCal";
    EditText inp2;
    float rate = 0f;
    float rmb = 0f;
    String tltle2 = "";
    String stn="asdc";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_cal);
        final String title = getIntent().getStringExtra("title");
        rate = getIntent().getFloatExtra("rate",0f);
        tltle2 = title;
        Log.i(TAG, "OnCreate: title = " + title);
        Log.i(TAG, "OnCreate: rate = " + rate);
        ((TextView)findViewById(R.id.title2)).setText(title);
        inp2 = findViewById(R.id.inp2);
        inp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    rmb = Float.parseFloat(s.toString());
                    float r = rmb * (100/rate) ;
                    ((TextView)findViewById(R.id.show2)).setText(rmb + "人民币==>"+String.format("%.2f",r)+tltle2);

                }else{
                    Toast.makeText(RateCalActivity.this,"请输入金额",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
