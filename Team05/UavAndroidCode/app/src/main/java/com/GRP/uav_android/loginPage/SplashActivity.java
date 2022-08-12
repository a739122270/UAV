package com.GRP.uav_android.loginPage;

import androidx.appcompat.app.AppCompatActivity;

import com.GRP.uav_android.Database.UserAction;
import com.GRP.uav_android.Database.UserInfo;
import com.GRP.uav_android.MainPage.HomeActivity;
import com.GRP.uav_android.R;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * @author Mochuan
 * @version 1.0
 * @date 2020/3/28
 * @description: shows the team icon as a loading page at the start up
 **/

public class SplashActivity extends AppCompatActivity {


    int status; //check if user has timed out

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        /**
         * call the cookie() to check whether to show up login page
         */
        try {
            checkCookie();
            if(status == -1){
                Toast toast=Toast.makeText(this,"Please check your network!",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(status == 0){
                    /**show the home page if not timed out*/
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else{
                    /**show the login page if timed out*/
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        },2000);

    }

    /**
     * call cookie in the USERACTION
     */
    private void checkCookie() throws InterruptedException {

        Runner1 r1 = new Runner1();
        Thread t1 = new Thread(r1, "Thread-A");
        t1.start(); //start thread
        t1.join(); //wait thread to finish and block main thread

    }
    /**
     * thread to call cookie
     */
    class Runner1 implements Runnable{
        @Override
        public void run() {
            UserAction act = new UserAction();
            status = act.cookie(getDeviceSN());
        }
    }

    /**
     * calculate a unique device serial number, not the sn provided by the producer
     */
    public String getDeviceSN(){
        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 +
                Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +
                Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 +
                Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 +
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 digits

        return m_szDevIDShort;
    }

}
