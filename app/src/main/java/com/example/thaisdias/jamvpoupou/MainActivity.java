package com.example.thaisdias.jamvpoupou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));
    }

    public void buttonOnClick (View b)
    {
        Toast.makeText(this, "Notification OK. Do something now!!", Toast.LENGTH_LONG).show();
    }



}
