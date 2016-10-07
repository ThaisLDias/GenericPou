package com.example.thaisdias.jamvpoupou;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.View;

/**
 * Created by Thaís Dias on 30/09/2016.
 */
public class GameView extends View implements Runnable {

    Handler handler = new Handler();

    public GameView(Context c)
    {
        super(c);
    }

    @Override
    public void run()
    {
        handler.postDelayed(this, 30);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {

    }

}
