package com.example.thaisdias.jamvpoupou;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    SharedPreferences sh;
    boolean pouIsAlive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent t = new Intent(this,PouService.class);
        if(!isMyServiceRunning(PouService.class))
        startService(t);

        sh = this.getSharedPreferences("poujamv", Context.MODE_PRIVATE);

        if(sh.getFloat("Hunger",50f) <= 0 || sh.getFloat("Thirsty",50f) <= 0)
        {
            ((Button)findViewById(R.id.food)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.water)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.revive)).setVisibility(View.VISIBLE);
            Bitmap bm = BitmapFactory.decodeResource(this.getResources(),R.drawable.dead);
            ImageView view = (ImageView)findViewById(R.id.imageView);
            view.setImageBitmap(bm);
        }
        else
        {
            setImage();
            ((TextView)findViewById(R.id.ft)).setText(sh.getFloat("Hunger",50f)+"");
            ((TextView)findViewById(R.id.wt)).setText(sh.getFloat("Thirsty",50f)+"");
        }
    }

    void setImage()
    {
        if(sh.getFloat("Hunger",50f) > 25f)
        {
            /*põe pra mudar imagem-(celoba)*/
            Bitmap bm = BitmapFactory.decodeResource(this.getResources(),R.drawable.celoba);
            ImageView view = (ImageView)findViewById(R.id.imageView);
            view.setImageBitmap(bm);
        }
        if(sh.getFloat("Hunger",50f) <= PouService.LIMIT)
        {
            Bitmap b = BitmapFactory.decodeResource(this.getResources(),R.drawable.sad);
            ImageView view = (ImageView)findViewById(R.id.imageView);
            view.setImageBitmap(b);
        }
    }

    public void revivePou(View v)
    {
        SharedPreferences.Editor ed = sh.edit();
        ed.putFloat("Hunger",50);
        ed.putFloat("Thirsty",50);
        ed.apply();
        Intent i = new Intent(this,MainActivity.class);
        finish();
        startActivity(i);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    public void giveFood(View v)
    {
        //Toast.makeText(this,"Celoba is already full of love!",Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor ed = sh.edit();
        ed.putFloat("Hunger",sh.getFloat("Hunger",50) +25);
        if(sh.getFloat("Hunger",50) > 50) {ed.putFloat("Hunger",50);}
        if(sh.getFloat("Hunger",50) > 25) PouService.hungry = false;
        ed.apply();
        setImage();
        ((TextView)findViewById(R.id.ft)).setText(sh.getFloat("Hunger",50f)+"");
    }

    public void giveWater(View v)
    {
        //Toast.makeText(this,"Celoba is hydrated!",Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor ed = sh.edit();
        ed.putFloat("Thirsty",sh.getFloat("Thirsty",50) +25);
        if(sh.getFloat("Thirsty",50) > 50) {ed.putFloat("Thirsty",50);}
        if(sh.getFloat("Thirsty",50) > 25) PouService.thirsty = false;
        ed.apply();
        setImage();
        ((TextView)findViewById(R.id.wt)).setText(sh.getFloat("Thirsty",50f)+"");
    }

    private boolean isMyServiceRunning(Class<?> serviceClass)
    {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (serviceClass.getName().equals(service.service.getClassName())) return true;
        }
        return false;
    }

}
