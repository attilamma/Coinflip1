package com.example.attila.coinflip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Button;
import android.view.animation.Animation;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final Random RANDOM = new Random();
    private ImageView coin;
    private Button coinbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coin = (ImageView) findViewById(R.id.coin);
        coinbutton = (Button) findViewById(R.id.coinbutton);
        coinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCoin();
            }
        });
    }

    private void flipCoin(){
    Animation fadeOut = new AlphaAnimation(1,0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(100);
        fadeOut.setFillAfter(true);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
                                         @Override
                                         public void onAnimationStart(Animation animation) {

                                         }

                                         @Override
                                         public void onAnimationEnd(Animation animation) {
                                             coin.setImageResource(RANDOM.nextFloat() > 0.5f ? R.drawable.yescoin : R.drawable.nocoin);
                                             Animation fadeIn = new AlphaAnimation(0,1);
                                             fadeIn.setInterpolator(new DecelerateInterpolator());
                                             fadeIn.setDuration(300);
                                             fadeIn.setFillAfter(true);

                                             coin.startAnimation(fadeIn);

                                         }

                                         @Override
                                         public void onAnimationRepeat(Animation animation) {

                                         }
                                     }

        ); coin.startAnimation(fadeOut);
    }
}
