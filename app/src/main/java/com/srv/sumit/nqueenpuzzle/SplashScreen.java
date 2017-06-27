package com.srv.sumit.nqueenpuzzle;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {
    ProgressBar progressBar;
    ActionBar actionBar;
    Thread thread,thread1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        actionBar = getSupportActionBar();
        actionBar.hide();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(3000);
                    Intent intent=new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

        };
        thread1=new Thread(){
            @Override
            public void run() {
                try{
                    for(int i=0;i<100;i++){
                        progressBar.setProgress(i);
                        sleep(200);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();;
        thread1.start();
    }
}
