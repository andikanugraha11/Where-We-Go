package io.github.andikanugraha11.wherewego;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {
    private ProgressBar progressBar;

    private int progressStatus = 0;
    private static int interval = 200;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
                startApp();
                finish();
            }
        }).start();
    }

    private void doWork(){
        for(int i=0; i<100;i+=5){
            try
            {
                Thread.sleep(100);
                progressBar.setProgress(i);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void startApp(){
        Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
        startActivity(intent);
    }
}