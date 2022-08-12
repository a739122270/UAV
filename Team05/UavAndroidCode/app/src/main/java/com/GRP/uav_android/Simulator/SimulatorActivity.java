package com.GRP.uav_android.Simulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.GRP.uav_android.Database.UserAction;
import com.GRP.uav_android.Database.UserInfo;
import com.GRP.uav_android.ExistTeamPage.Team;
import com.GRP.uav_android.FragmentOfHome.MyUAV.Task;
import com.GRP.uav_android.MainPage.HomeActivity;
import com.GRP.uav_android.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import tech.gusavila92.websocketclient.WebSocketClient;

/**
 * @author Mochuan Weichen
 * @version 2.0
 * @date 2020/4/20
 * @description: this is a simulator simulates the move/battery level/progress of the drone
 **/
public class SimulatorActivity extends AppCompatActivity {

    private ArrayList<Position> pos = new ArrayList<>();
    private ArrayList<Position> path = new ArrayList<>();
    private TextView battery, progress, txt_id;
    private Button finish;
    UserInfo info = UserInfo.getInstance();
    Task task = Task.getInstance();
    private double unit_time = 1;                           /**refresh period*/
    private int size;                                       /**the number of simulated points*/
    private double bat1 = 70 + Math.random() * (30);        /**simulated battery level at start up*/


    DecimalFormat batteryLeft = new DecimalFormat("######0.00");
    DecimalFormat position = new DecimalFormat("######0.000000");

    /**the thread to run the simulator(show the position and progresss) and send location back to the web-end*/
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
                if(path.size() != 0) {
                    this.update();
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(this, 1000);
                }else{
                    ImageView imageView = (ImageView) findViewById(R.id.refresh);
                    /**stop animation*/
                    imageView.clearAnimation();
                    finish.setVisibility(View.VISIBLE);
                    Toast toast=Toast.makeText(getApplicationContext(),"Simulation finished!",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    /**stop update*/
                    handler.removeCallbacks(runnable);
                }
            }

        /**update the information on the board*/
        void update() {
            double pro = 0;
            TextView lat, longt;
            lat = findViewById(R.id.uav_lat);
            longt = findViewById(R.id.uav_long);

            /**simulate battery level*/
            bat1 -= Math.abs(Math.random())/5;
            String ba1 = batteryLeft.format(bat1);
            battery.setText(ba1 + "%");

            /**update the location*/
            String l1 = position.format(path.get(0).getLatitude());
            String l2 = position.format(path.get(0).getLongtitude());
            lat.setText(l1);
            longt.setText(l2);

            /**calculate the progress*/
            pro = 100 * (size - path.size() + 1) / size;
            progress.setText(pro + "%");

            /**send info to the web*/
            HomeActivity.webSocketClient.send("5;" + info.getTask_id() + ";" + task.getCaptain() + ";" + pro + ";" + info.getAircraftId() + ";" + ba1 + ";" + path.get(0).getLatitude() + ";" + path.get(0).getLongtitude() + ";");

            /**remove the past position*/
            path.remove(0);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulator);

        /**animation*/
        ImageView imageView = (ImageView) findViewById(R.id.refresh);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
        LinearInterpolator lin = new LinearInterpolator();
        animation.setInterpolator(lin);
        imageView.startAnimation(animation);

        /**show uav serial number in current team*/
        txt_id = findViewById(R.id.txt_id);
        txt_id.setText(info.getAircraftId());

        /**show the battery level*/
        battery = findViewById(R.id.txt_battery);
        String ba = batteryLeft.format(bat1);
        battery.setText(ba + "%");

        /**progress*/
        progress = findViewById(R.id.txt_progress);

        /**finish button*/
        finish = findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SimulatorActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /**get the position from database*/
        try {
            GetPositions();
            Toast toast=Toast.makeText(this,"Task Started!",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**simulate the intermediate position*/
        IntermediatePosition();
        size = path.size();

        /**start thread*/
        handler.postDelayed(runnable, 1000);
    }

    @Override
    protected void onDestroy() {
        /**stop thread*/
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        /**prevent going back so that simulator won't be interrupted*/
        moveTaskToBack(false);
        //super.onBackPressed();
    }

    /**the algorithm to get the simulated position*/
    public void IntermediatePosition(){
        double velocity = Double.parseDouble(task.getVelocity());

        /**to set the offset to the calculated positon*/
        //haven't done, but easy to realize

        /**if there is more than 1 node left*/
        while(pos.size() != 1){

            double unit_distance = velocity * unit_time;

            /**current start position and current end position*/
            double start_lat = pos.get(0).getLatitude();
            double start_lot = pos.get(0).getLongtitude();
            double end_lat = pos.get(1).getLatitude();
            double end_lot= pos.get(1).getLongtitude();


            /**get the distance*/
            double dis_lat = end_lat - start_lat;
            double dis_lot = end_lot -start_lot;
            double distance = GetDistance.getDistance(start_lot,start_lat,end_lot,end_lat);
            double quotient = distance / unit_distance;
            //double remainder = distance % unit_distance;
            if(quotient > 1){
                /**if can't finish in one turn*/
                pos.set(0,new Position(start_lat + dis_lat/quotient, start_lot + dis_lot/quotient));
                path.add(new Position(start_lat + dis_lat/quotient, start_lot + dis_lot/quotient));
                unit_time = 1;
            }else if(quotient == 1) {
                /**finished in one turn exactly*/
                pos.remove(0);
                path.add(new Position(end_lat,end_lot));
                unit_time = 1;
            }else{
                /**too short, need to take next path*/
                double cost_time = (unit_time * (1-quotient));
                pos.remove(0);
                unit_time = 1 - cost_time;
            }



            }
    }

    class Runner implements Runnable{
        @Override
        public void run() {
            UserAction act = new UserAction();
            act.getAircraftId();
            pos = act.getPosition(info.getUserId(),info.getUsername());
        }
    }

    private void GetPositions() throws InterruptedException {

        Runner r5 = new Runner();
        Thread t5 = new Thread(r5, "Thread-D");
        t5.start(); //start thread
        t5.join(); //wait thread to finish and block main thread

    }

}




