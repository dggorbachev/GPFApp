package com.singlelab.gpf.interactive_games.flappy_cats.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class Background {

    Bitmap bitmap;
    Bitmap bitmapReversed;

    int width;
    int height;
    boolean reversedFirst;
    float speed;

    int xClip;
    int startY;
    int endY;

    Background(Context context, int screenWidth, int screenHeight, String bitmapName, int sY, int eY, float s){


        int resID = context.getResources().getIdentifier(bitmapName,
                "drawable", context.getPackageName());


        bitmap = BitmapFactory.decodeResource(context.getResources(), resID);



        reversedFirst = false;





        xClip = 0;


        startY = sY * (screenHeight / 100);
        endY = eY * (screenHeight / 100);
        speed = s;


        bitmap = Bitmap.createScaledBitmap(bitmap, screenWidth,
                (endY - startY)
                , true);


        width = bitmap.getWidth();
        height = bitmap.getHeight();


        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1);
        bitmapReversed = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

    }

    public void update(long fps){


        xClip -= speed/fps;
        if (xClip >= width) {
            xClip = 0;
            reversedFirst = !reversedFirst;
        } else if (xClip <= 0) {
            xClip = width;
            reversedFirst = !reversedFirst;

        }
    }
}