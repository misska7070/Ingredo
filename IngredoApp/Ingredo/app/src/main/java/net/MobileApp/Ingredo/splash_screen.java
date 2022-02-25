package net.MobileApp.Ingredo;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class splash_screen extends AppCompatActivity
{
    private static int SPLASH_TIME_OUT= 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen2);
        new Handler().postDelayed(new Runnable(){

            public  void run(){
                Intent homeintent = new Intent(splash_screen.this,StartOption.class);
                startActivity(homeintent);
                finish();
            }

        },SPLASH_TIME_OUT);
    }
}
