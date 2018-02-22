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
import android.media.MediaPlayer;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    //Coinflip Values
    public static final Random RANDOM = new Random();
    private ImageView coin;
    private Button coinbutton;

    //Acceleromenter Values
    private float lastX, lastY, lastZ;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private float max = 0;


    private float deltaX = 0;
    private float deltaY = 0;
    private float deltaZ = 0;

    //Audio Value
    MediaPlayer mp        = null;


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

      //check accelerometer

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // success! we have an accelerometer

            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        } else {
            // no accelerometer, do nothing
        }

    }

    private void flipCoin(){
        mp = MediaPlayer.create(this, R.raw.coinflipaudio);
        mp.start();

        if (mp != null) {
            mp.reset();
            mp.release();
        }

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

    //onResume() register the accelerometer for listening the events
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    //onPause() unregister the accelerometer for stop listening the events
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // get the change of the x,y,z values of the accelerometer
        deltaX = Math.abs(lastX - event.values[0]);
        deltaY = Math.abs(lastY - event.values[1]);
        deltaZ = Math.abs(lastZ - event.values[2]);

        // if the change is below 2, it is just plain noise
        if (deltaX < 2)
            deltaX = 0;
        if (deltaY < 2)
            deltaY = 0;
        if (deltaZ < 2)
            deltaZ = 0;

        if (deltaX>15 || deltaY>15 || deltaZ>15){
            flipCoin();
        }
    }

}
