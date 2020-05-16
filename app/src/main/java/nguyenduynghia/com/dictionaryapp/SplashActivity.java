package nguyenduynghia.com.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        Thread welcomeThread = new Thread() {
//
//            @Override
//            public void run() {
//                try {
//                    super.run();
//                    sleep(3000);  //Delay of 1 seconds
//                    Intent intent;
//                    intent=new Intent(SplashActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                } catch (Exception e) {
//
//                }
//            }
//        };
//        welcomeThread.start();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, 3000);
    }

}
