package com.example.bloodbankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    android.widget.ProgressBar pb;
    public int value;
TextView count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        count=findViewById(R.id.count);
        pb=findViewById(R.id.pb);
        pb.setMax(100);
        pb.setScaleY(3f);
        progressanimation();

    }
    public void progressanimation()
    {
        android.widget.ProgressBar pb=findViewById(R.id.pb);
        ProgressBar anime=new ProgressBar(this,pb,count,0f,100f);

        anime.setDuration(8000);
         pb.setAnimation(anime);
    }
}
class ProgressBar extends Animation
{
    public static Context context;
   public static android.widget.ProgressBar pb;
    public static TextView count;
    public static float value;
    public static float from;
    public static float to;

    public ProgressBar(Context context, android.widget.ProgressBar pb, TextView count, float from, float to) {
        this.context = context;
        this.pb = pb;
        this.count = count;
        this.from = from;
        this.to = to;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public android.widget.ProgressBar getPb() {
        return pb;
    }

    public void setPb(android.widget.ProgressBar pb) {
        this.pb = pb;
    }

    public TextView getCount() {
        return count;
    }

    public void setCount(TextView count) {
        this.count = count;
    }

    public float getFrom() {
        return from;
    }

    public void setFrom(float from) {
        this.from = from;
    }

    public float getTo() {
        return to;
    }

    public void setTo(float to) {
        this.to = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        value=from+(to-from)* interpolatedTime;
        pb.setProgress((int)value);
        count.setText((int)value+" % ");
        if(value==100)
        {
            context.startActivity(new Intent(context,MainActivity.class));

        }
    }
}