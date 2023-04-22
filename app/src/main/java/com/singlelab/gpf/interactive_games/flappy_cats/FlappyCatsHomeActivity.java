package com.singlelab.gpf.interactive_games.flappy_cats;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import com.singlelab.gpf.R;
import com.singlelab.gpf.interactive_games.flappy_cats.Game.StartGame;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;

public class FlappyCatsHomeActivity extends AppCompatActivity {

    ImageView  btn_logout, btn_exit;

    ViewFlipper v_flipper;

    CircleImageView profile_img;
    TextView maxscore, username;

    GifImageView cat, cat_angry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_flappy_cats_home);

        btn_logout = findViewById(R.id.btn_logout);

        btn_exit = findViewById(R.id.btn_exit);



        v_flipper = findViewById(R.id.viewflipper);

        profile_img = findViewById(R.id.profile_img);
        maxscore = findViewById(R.id.maxscore);
        username = findViewById(R.id.username);

        cat = findViewById(R.id.cat);
        cat_angry = findViewById(R.id.cat_angry);


        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.fade_in_activity, R.anim.fade_out_activity);
                finish();
            }
        });

        int images[] = {R.drawable.bg1, R.drawable.bg2, R.drawable.bg3};

        for (int image: images) {
            setImageInFlip(image);
        }

        Animation animUpDown;

        animUpDown = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.up_down);

        cat.startAnimation(animUpDown);
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, StartGame.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in_activity, R.anim.fade_out_activity);
        finish();
    }

    private void setImageInFlip(int imgUrl) {
        ImageView image = new ImageView(getApplicationContext());
        image.setBackgroundResource(imgUrl);
        v_flipper.addView(image);
        v_flipper.setFlipInterval(5000);
        v_flipper.setAutoStart(true);

        v_flipper.setInAnimation(this, R.anim.fade_in);
        v_flipper.setOutAnimation(this, R.anim.fade_out);
    }
}
