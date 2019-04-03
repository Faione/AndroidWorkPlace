package com.example.faione.a;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView out;
    EditText inp;
    double a,b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperatureconverter);
        out= findViewById(R.id.txtout);
        inp= findViewById(R.id.inp);
        Button btn= findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        TextView out= findViewById(R.id.txtout);
        a = Double.parseDouble(inp.getText().toString());
        if(a>-273.15){
           b = a*1.8 + 32.0 ;
           out.setText("华摄氏度："+String.format("%.2f",b)+"℉");}
        else
            out.setText("请输入理论存在的温度");
    }
}
