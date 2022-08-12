package com.GRP.uav_android.FragmentOfHome.MyUAV;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.GRP.uav_android.Database.UserAction;
import com.GRP.uav_android.Database.UserInfo;
import com.GRP.uav_android.ExistTeamPage.Team;
import com.GRP.uav_android.R;
import com.GRP.uav_android.Simulator.SimulatorActivity;


import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author Mochuan Weichen
 * @version 3.3
 * @date 2020/4/20 update
 * @description: The my UAV page
 **/

public class MyUAVFragment extends Fragment {

    private LinearLayout taskInitation, currentTask, simulator;
    private Button accept, refuse,back0;
    private ImageView back, tip;
    private TextView TeamName, captain, uav_amount,distance, velocity, height, height_restriction, txt_battery, Task_id;
    private TextView txt_date, txt_team, teamName;
    private Task task;
    private UserInfo info = UserInfo.getInstance();
    private Team team = Team.getInstance();
    private double battery = 00.00;
    public static String task_id;
    private int taskPlaceTaken;
    private static int flag = 0;

    public static void setFlag(int num){
        flag = num;
    }

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        txt_date = root.findViewById(R.id.txt_date);
        txt_team = root.findViewById(R.id.txt_team);
        taskInitation = root.findViewById(R.id.task_view);
        currentTask = root.findViewById(R.id.current_task);
        simulator = root.findViewById(R.id.simulator);
        txt_battery = root.findViewById(R.id.txt_battery);
        tip = root.findViewById(R.id.tip);
        if(flag == 0){
            tip.setVisibility(View.INVISIBLE);
        }else{
            tip.setVisibility(View.VISIBLE);
        }
        String bat = String.valueOf(battery);
        txt_battery.setText(bat+"%");
        //get current date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date(System.currentTimeMillis());
        txt_date.setText(simpleDateFormat.format(date));
        if(!info.getTeamId().equals("no_team")){
           txt_team.setText(team.getName());
        }else{
           txt_team.setText("--");
        }

        /**set listener on TASK INVITATION VIEW*/
        taskInitation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (flag == 0) {
                    Toast toast = Toast.makeText(getActivity(), "No invitation!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    final Dialog myDialog = new Dialog(getContext());
                    /**check the task place left*/
                    try {
                        checkTaskPlace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    /**if there is no place left*/
                    if (taskPlaceTaken >= 2) {
                        Toast toast = Toast.makeText(getActivity(), "There is no place left!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        myDialog.setContentView(R.layout.invitation);
                        teamName = myDialog.findViewById(R.id.txt_cap);
                        accept = myDialog.findViewById(R.id.accept);
                        refuse = myDialog.findViewById(R.id.refuse);
                        teamName.setText(Team.getInstance().getName());
                        accept.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                //put accept code here
                                try {
                                    joinTask();
                                    UpdateTask();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                myDialog.dismiss();
                            }
                        });

                        refuse.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                //put refuse code here

                                myDialog.dismiss();
                            }
                        });
                        myDialog.setCancelable(false);
                        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        myDialog.show();
                    }
                    setFlag(0);
                    tip.setVisibility(View.INVISIBLE);
                }
            }
        } );

        currentTask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                task = Task.getInstance();
                final Dialog myDialog = new Dialog(getContext());
                if(info.getTask_id().equals("no_task")){
                    Toast toast = Toast.makeText(getActivity(), "No current task!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else {
                    myDialog.setContentView(R.layout.task);
                    TeamName = (TextView) myDialog.findViewById(R.id.task_teamName);
                    captain = (TextView) myDialog.findViewById(R.id.task_captain);
                    uav_amount = (TextView) myDialog.findViewById(R.id.uav_amount);
                    distance = (TextView) myDialog.findViewById(R.id.distance);
                    velocity = (TextView) myDialog.findViewById(R.id.velocity);
                    height = (TextView) myDialog.findViewById(R.id.height);
                    Task_id = (TextView)myDialog.findViewById(R.id.task_id);
                    height_restriction = (TextView) myDialog.findViewById(R.id.height_restriction);
                    TeamName.setText(task.getTeamName());
                    captain.setText(task.getCaptain());
                    uav_amount.setText(task.getUav_amount());
                    distance.setText(task.getDistance());
                    velocity.setText(task.getVelocity());
                    height.setText(task.getHeight());
                    height_restriction.setText(task.getHeight_restriction());
                    Task_id.setText(info.getTask_id());
                    back = myDialog.findViewById(R.id.back);
                    back.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            myDialog.dismiss();
                        }
                    });
                    Window window = myDialog.getWindow();
                    WindowManager.LayoutParams lp = window.getAttributes();
                    lp.y = 30;
                    window.setGravity(Gravity.RIGHT|Gravity.TOP);
                    window.setAttributes(lp);
                    myDialog.setCancelable(false);
                    myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    myDialog.show();
                }
            }
        });

        return root;
    }

    /**call thread to check task place taken*/
    public void checkTaskPlace() throws InterruptedException {
        Runner5 r5 = new Runner5();
        Thread t5 = new Thread(r5, "Thread-checkTeamPlace");
        t5.start(); //start thread
        t5.join(); //wait thread to finish and block main thread
    }
    /**thread*/
    class Runner5 implements Runnable{
        @Override
        public void run() {
            UserAction act = new UserAction();
            taskPlaceTaken = act.checkTaskPlace(task_id);
            info.setAircraftId(String.valueOf(1 + taskPlaceTaken));
            act.set_uav_serial();
        }
    }

    /**call thread to join the task*/
    public void joinTask() throws InterruptedException {
        Runner r = new Runner();
        Thread t = new Thread(r, "Thread-checkTeamPlace");
        t.start(); //start thread
        t.join(); //wait thread to finish and block main thread
    }
    /**thread*/
    class Runner implements Runnable{
        @Override
        public void run() {
            UserAction act = new UserAction();
            act.joinTask(task_id);
        }
    }

    /**thread2*/
    private void UpdateTask() throws InterruptedException {

        Runner4 r5 = new Runner4();
        Thread t5 = new Thread(r5, "Thread-D");
        t5.start(); //start thread
        t5.join(); //wait thread to finish and block main thread

    }

    /**run update thread*/
    class Runner4 implements Runnable{
        @Override
        public void run() {
            UserAction act = new UserAction();
            info.storeInfo(act.getUserInfo(info.getUsername()));
            act.getTaskInfo();
        }
    }

}
