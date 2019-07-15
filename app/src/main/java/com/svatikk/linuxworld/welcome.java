package com.svatikk.linuxworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
public class welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        ImageView imageView = (ImageView)findViewById(R.id.image);


        //TextView textView =(TextView)findViewById(R.id.CompanyLogo);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);


        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.welcome_fade_in);

        imageView.setAnimation(animation);


        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                //WHEN ACTIVITY STARTS ADD PROGRESS BAR IF NECESSARY

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));



            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

}
