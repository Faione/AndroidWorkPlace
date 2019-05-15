package com.example.faione.a;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Changerate extends AppCompatActivity {
    public final String TAG = "Changerate";
    EditText dollarText;
    EditText poundText;
    EditText yenText;
    EditText hkText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changerate);
        Intent intent = getIntent();
        float dollar2 = intent.getFloatExtra("dollar_rate_key",0.0f);
        float pound2 = intent.getFloatExtra("pound_rate_key",0.0f);
        float hk2 = intent.getFloatExtra("hk_rate_key",0.0f);
        float yen2 = intent.getFloatExtra("yen_rate_key",0.0f);

        Log.i(TAG,"onCreate: dollar2 = "+dollar2);
        Log.i(TAG,"onCreate: pound2 = "+pound2);
        Log.i(TAG,"onCreate: yen2 = "+yen2);
        Log.i(TAG,"onCreate: hk2 = "+hk2);

        dollarText = findViewById(R.id.dollar_rate);
        poundText = findViewById(R.id.pound_rate);
        yenText = findViewById(R.id.yen_rate);
        hkText = findViewById(R.id.hk_rate);

        dollarText.setText(String.valueOf(dollar2));
        poundText.setText(String.valueOf(pound2));
        yenText.setText(String.valueOf(yen2));
        hkText.setText(String.valueOf(hk2));

    }
    public void save(View btn){
        Log.i("cfg","save:");

        float newdollar_rate = Float.parseFloat(dollarText.getText().toString());
        float newpound_rate = Float.parseFloat(poundText.getText().toString());
        float newyen_rate = Float.parseFloat(yenText.getText().toString());
        float newhk_rate = Float.parseFloat(hkText.getText().toString());

        Log.i("cfg","save:new date");
        Log.i(TAG,"onCreate: newdollar_rate = "+newdollar_rate);
        Log.i(TAG,"onCreate: newpound_rate = "+newpound_rate);
        Log.i(TAG,"onCreate: newyen_rate = "+newyen_rate);
        Log.i(TAG,"onCreate: newhk_rate = "+newhk_rate);

        Intent intent = getIntent();
        Bundle bdl = new Bundle();
        bdl.putFloat("key_dollar",newdollar_rate);
        bdl.putFloat("key_pound",newpound_rate);
        bdl.putFloat("key_yen",newyen_rate);
        bdl.putFloat("key_hk",newhk_rate);
        intent.putExtras(bdl);
        setResult(2,intent);

        finish();
    }
}
