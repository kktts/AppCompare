package com.konstantinost.appcompare;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Konstantinos on 13/12/2015.
 */
public class ResultActivity extends AppCompatActivity{

    SharedPreferences settings;
    String percentageResult="0";
    private String resultText ="Similarity found:";
    private String disclaimerText="The data were collected by comparing the information that collected from mobile device with the data from the database.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity_layout);

        settings = getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);
        percentageResult=settings.getString("result", "N/A");
        float value1=makeFloatNumber(percentageResult);
        float value2=100-makeFloatNumber(percentageResult);
        float values[]={value1,value2};

        LinearLayout pieLayout = (LinearLayout) findViewById(R.id.pie_layout);
        values = calculateDegrees(values);
        MyGraphview graphview = new MyGraphview(this, values);
        pieLayout.addView(graphview);

        TextView text1=(TextView)findViewById(R.id.result_textView_header);
        TextView text2=(TextView)findViewById(R.id.result_textView_result);
        text1.setText(resultText);
        text2.setText(percentageResult);

        TextView text3=(TextView)findViewById(R.id.disclaimer_textview);
        text3.setText(disclaimerText);

    }
    private float[] calculateDegrees(float[] data) {

        for (int i = 0; i < data.length; i++) {
            data[i] = 360 * (data[i] / 100);
        }
        return data;
    }

    public class MyGraphview extends View {
        private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private float[] arc_degrees;
        float temp = 0;

        public MyGraphview(Context context, float[] values) {
            super(context);
            arc_degrees = new float[values.length];
            for (int i = 0; i < values.length; i++) {
                arc_degrees[i] = values[i];
                Log.d("tag", String.valueOf(arc_degrees[i]));
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            RectF rectf = new RectF(10,10,convertDpToPixel(150,getContext()),convertDpToPixel(150,getContext()));
            for (int i = 0; i < arc_degrees.length; i++) {
                if (i == 0) {
                    paint.setColor(Color.parseColor("#8BC34A"));
                    canvas.drawArc(rectf, 0, arc_degrees[i], true, paint);
                } else {
                    temp += arc_degrees[i - 1];

                    paint.setColor(Color.parseColor("#2196F3"));
                    canvas.drawArc(rectf, temp, arc_degrees[i], true, paint);
                }
            }
        }
    }

    public Float makeFloatNumber(String str) {
        if (str != null && str.length() > 0 ) {
            str = str.substring(0, str.length()-1);
        }
        return Float.valueOf(str);
    }

    public static float convertDpToPixel(int dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

}
