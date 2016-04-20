package com.example.mehmood.splashy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by MEHMOOD on 3/22/2016.
 */
public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashes);

        final ImageView i=(ImageView)findViewById(R.id.imageView);

        final Animation a= AnimationUtils.loadAnimation(getBaseContext(),R.anim.rot);



        i.startAnimation(a);


        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                Intent in=new Intent(getBaseContext(),MainActivity.class);
                startActivity(in);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


    }
