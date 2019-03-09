package com.pieta.zapis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Wykresy extends AppCompatActivity {
    final String FILENAME = "data.csv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wykresy);
        load();
    }

    private void load(){
        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        String[] tmp = FileHandler.readFromFile(FILENAME, this);

        for(String t : tmp){
            TextView textView = new TextView(this);
            textView.setText(t);
            linearLayout.addView(textView);
        }


    }
}
