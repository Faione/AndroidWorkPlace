package com.example.faione.a;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    TextView score;
    TextView score2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        score=findViewById(R.id.score);
        score2=findViewById(R.id.score2);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    public void btn1(View btn) {
        if (btn.getId()== R.id.btn_1) {
            ShowScore(1);
        }else{
            ShowScore2(1);
        }
    }
    public void btn2(View btn) {
        if (btn.getId()== R.id.btn_2) {
            ShowScore(2);
        }else{
            ShowScore2(2);
        }

    }
    public void btn3(View btn) {
        if (btn.getId()== R.id.btn_3) {
            ShowScore(3);
        }else{
            ShowScore2(3);
        }

    }
    public void btnreset(View btn) {
        score.setText("0");
        score2.setText("0");

    }
    private void ShowScore (int inc){
        Log.i("show","inc="+inc);
        String OldScore =(String)score.getText();
        int NewScore = Integer.parseInt(OldScore)+inc;
        score.setText(""+NewScore);
    }
    private void ShowScore2 (int inc){
        Log.i("show","inc="+inc);
        String OldScore =(String)score2.getText();
        int NewScore = Integer.parseInt(OldScore)+inc;
        score2.setText(""+NewScore);
    }
}
