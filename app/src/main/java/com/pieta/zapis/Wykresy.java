package com.pieta.zapis;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.callback.OnPieSelectListener;
import com.razerdp.widget.animatedpieview.data.IPieInfo;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Wykresy extends Activity {
    final String FILENAME = "data.csv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wykresy);
        load();
    }

    private void load(){
        /*LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        String[] tmp = FileHandler.readFromFile(FILENAME, this);

        for(String t : tmp){
            TextView textView = new TextView(this);
            textView.setText(t);
            linearLayout.addView(textView);
        }*/
        Random rand = new Random();

        AnimatedPieView mAnimatedPieView = findViewById(R.id.pieChart);
        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        config.startAngle(-90);
        HashMap<String, Long> data = DataParser.parse(FileHandler.readFromFile(FILENAME,this), "7days");
        Iterator it = data.entrySet().iterator();
        while (it.hasNext()) {
            int r = rand.nextInt();
            int g = rand.nextInt();
            int b = rand.nextInt();
            Map.Entry pair = (Map.Entry)it.next();
            int color = (255 & 0xff) << 24 | (r & 0xff) << 16 | (g & 0xff) << 8 | (b & 0xff);
            config.addData(new SimplePieInfo((float)((long)pair.getValue()/1000), color, (String)pair.getKey()));
            it.remove();
        }
        config.duration(1500).drawText(true).textSize(40).strokeWidth(256).canTouch(true);
        config.focusAlpha(AnimatedPieViewConfig.FOCUS_WITH_ALPHA_REV);
        config.autoSize(true).textGravity(AnimatedPieViewConfig.ABOVE).selectListener(new OnPieSelectListener<IPieInfo>(){
            @Override
            public void onSelectPie(IPieInfo iPieInfo, boolean isFloatUp){
                Toast.makeText(Wykresy.this, iPieInfo.getDesc()+": "+iPieInfo.getValue(), Toast.LENGTH_LONG).show();
            }
        });
        mAnimatedPieView.applyConfig(config);
        mAnimatedPieView.start();

    }
}
