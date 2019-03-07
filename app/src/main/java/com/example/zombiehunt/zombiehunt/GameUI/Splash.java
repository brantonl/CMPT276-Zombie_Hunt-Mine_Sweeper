package com.example.zombiehunt.zombiehunt.GameUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zombiehunt.zombiehunt.R;

//description: this class is the setup of the welcoming animation
public class Splash extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;
    MyThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        textView = findViewById(R.id.startText);
        imageView = findViewById(R.id.startImg);

        Animation alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        textView.startAnimation(alphaAnimation);
        imageView.startAnimation(alphaAnimation);

        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        imageView.startAnimation(rotateAnimation);

        thread = new MyThread();
        thread.start();

       skipButton();

    }

    private void skipButton() {
        Button skip =  findViewById(R.id.skipAnimation);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (thread != null && thread.isAlive())
                {
                    thread.bRun = false;
                }
                //Skipping the animation
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private class MyThread extends Thread{
        final Intent i = new Intent(getApplicationContext(), MainActivity.class);
        public boolean bRun = true;

        @Override
        public void run()
        {
            try
            {
                sleep(5000);
                if (bRun)
                {
                    startActivity(i);
                    finish();
                }
                else
                {
                    finish();
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
