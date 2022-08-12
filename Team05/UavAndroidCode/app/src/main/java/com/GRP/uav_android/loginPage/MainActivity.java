package com.GRP.uav_android.loginPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.GRP.uav_android.Database.UserAction;
import com.GRP.uav_android.MainPage.HomeActivity;
import com.GRP.uav_android.R;

/**
 * @author Mochuan
 * @version 1.0
 * @date 2020/3/19
 * @description: the login page
 **/

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login);
        final LoadingDialog loadingDialog = new LoadingDialog(MainActivity.this);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
                loadingDialog.startLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismissDialog();
                    }
                },800);
            }
        });
    }

    /**
     * submit the username and password to check
     * if passed, update the device_sn and last Login Time
     * if not, toast wrong password
     */
    public void submit(){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String n = username.getText().toString().trim();
                        String psw = password.getText().toString().trim();
                        if (n.equals("")||psw.equals("")){
                            Looper.prepare();
                            Toast toast = Toast.makeText(MainActivity.this,"Can't be empty！",Toast.LENGTH_SHORT);
                            toast.show();
                            Looper.loop();
                        }
                        UserAction ud = new UserAction();
                        boolean result = ud.login(n,psw);
                        if (!result){
                            Looper.prepare();
                            Toast toast=Toast.makeText(MainActivity.this,"Username doesn't exist or wrong password！",Toast.LENGTH_SHORT);
                            toast.show();
                            Looper.loop();
                        }else{
                            Looper.prepare();
                            Toast toast=Toast.makeText(MainActivity.this,"Login successfully!",Toast.LENGTH_SHORT);
                            toast.show();
                            Intent intent=new Intent(MainActivity.this, HomeActivity.class);
                            //intent.putExtra("name",n);
                            startActivity(intent);
                            finish();
                            Looper.loop();

                        }
                    }
                }).start();



    }
}
