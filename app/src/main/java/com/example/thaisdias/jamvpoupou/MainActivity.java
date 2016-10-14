package com.example.thaisdias.jamvpoupou;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sh = this.getSharedPreferences("poujamv", Context.MODE_PRIVATE);
        setImage();
    }

    void setImage(){

        if(sh.getFloat("Hunger",50f) > 25f) { /*põe pra mudar imagem-(celoba)*/
            Bitmap bm = BitmapFactory.decodeResource(this.getResources(),R.drawable.celoba);
            ImageView view = (ImageView)findViewById(R.id.imageView);
            view.setImageBitmap(bm);
        }
        if(sh.getFloat("Hunger",50f) <= 24f) {
            Bitmap b = BitmapFactory.decodeResource(this.getResources(),R.drawable.sad);
            ImageView view = (ImageView)findViewById(R.id.imageView);
            view.setImageBitmap(b);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent t = new Intent(this,PouService.class);
        startService(t);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent t = new Intent(this,PouService.class);
        startService(t);
    }

    public void giveFood(View v)
    {
        Toast.makeText(this,"Celoba is already full of love!",Toast.LENGTH_LONG).show();
        SharedPreferences.Editor ed = sh.edit();
        ed.putFloat("Hunger",sh.getFloat("Hunger",50) +25);
        if(sh.getFloat("Hunger",50) > 50) {ed.putFloat("Hunger",50);}
        if(sh.getFloat("Hunger",50) > 25) PouService.hungry = false;
        ed.apply();
        setImage();
    }

    public void giveWater(View v)
    {
        Toast.makeText(this,"Celoba is hydrated!",Toast.LENGTH_LONG).show();
        SharedPreferences.Editor ed = sh.edit();
        ed.putFloat("Thirsty",sh.getFloat("Thirsty",50) +25);
        if(sh.getFloat("Thirsty",50) > 50) {ed.putFloat("Thirsty",50);}
        if(sh.getFloat("Thirsty",50) > 25) PouService.thirsty = false;
        ed.apply();
        setImage();
    }

}
