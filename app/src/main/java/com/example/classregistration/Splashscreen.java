package com.example.classregistration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Splashscreen extends Activity {

    private void startAnimations() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        animation.reset();
        LinearLayout linearLayout = findViewById(R.id.lin_lay);
        linearLayout.clearAnimation();
        linearLayout.startAnimation(animation);
        animation = AnimationUtils.loadAnimation(this, R.anim.translate);
        animation.reset();
        ImageView imageView = findViewById(R.id.splash);
        imageView.clearAnimation();

        imageView.startAnimation(animation);

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent intent = new Intent(Splashscreen.this, LoginActivity.class);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        };


        splashTread.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        startAnimations();


    }


}
