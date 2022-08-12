package com.GRP.uav_android.FragmentOfHome.Team;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.GRP.uav_android.Database.UserAction;
import com.GRP.uav_android.Database.UserInfo;
import com.GRP.uav_android.ExistTeamPage.ExistTeamActivity;
import com.GRP.uav_android.ExistTeamPage.Team;
import com.GRP.uav_android.R;
import com.GRP.uav_android.loginPage.LoadingDialog;

/**
 * @author Mochuan
 * @version 1.0
 * @date 2020/3/20
 * @description: Team page
 **/
public class TeamFragment extends Fragment {

    private TextView searchTeam, team, owner, type, location, status, member1, member2, member3, member4,member5,history1,history2;
    private ImageView m1,m2,m3,m4,m5, refresh;
    private Button quit, yes, no;
    private UserInfo info = UserInfo.getInstance();


    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        team = (TextView)view.findViewById(R.id.team);
        owner = (TextView)view.findViewById(R.id.owner);
        type = (TextView)view.findViewById(R.id.type);
        location = (TextView)view.findViewById(R.id.location);
        status = (TextView)view.findViewById(R.id.status);
        member1 = (TextView)view.findViewById(R.id.member1);
        member2 = (TextView)view.findViewById(R.id.member2);
        member3 = (TextView)view.findViewById(R.id.member3);
        member4 = (TextView)view.findViewById(R.id.member4);
        member5 = (TextView)view.findViewById(R.id.member5);
        m1 = (ImageView)view.findViewById(R.id.m1);
        m2 = (ImageView)view.findViewById(R.id.m2);
        m3 = (ImageView)view.findViewById(R.id.m3);
        m4 = (ImageView)view.findViewById(R.id.m4);
        m5 = (ImageView)view.findViewById(R.id.m5);
        history1 = (TextView)view.findViewById(R.id.history1);
        history2 = (TextView)view.findViewById(R.id.history2);

        /**quit button listener*/
        quit = (Button)view.findViewById(R.id.quit);
        quit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                /**show dialog*/
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.quit);
                yes = dialog.findViewById(R.id.btn_yes);
                /**if yes*/
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Quit();
                            Toast toast=Toast.makeText(getActivity(),"Quit successfully!",Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            Update();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                });
                /**if no*/
                no = dialog.findViewById(R.id.btn_no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        /**seach team view listner*/
        searchTeam = (TextView)view.findViewById(R.id.searchTeam);
        final LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        searchTeam.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                /**show Existing Team activity*/
                Intent intent = new Intent(getActivity(), ExistTeamActivity.class);
                startActivity(intent);
                loadingDialog.startLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismissDialog();
                    }
                },1500);
            }
        });

        /**update the text view in this page*/
        UpdateTextView();

        /**refresh button lisener*/
        refresh = (ImageView)view.findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    /**update team/task/user information*/
                    Update();
                    UpdateTextView();
                    openAnimate();
                    Toast toast=Toast.makeText(getActivity(),"refresh successfully!",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        return view;
    }

    /**update the textView */
    private void UpdateTextView(){
        Team teamInfo = Team.getInstance();
        Member mem = Member.getInstance();
        if(info.getTeamId().equals("no_team")){
            team.setText("--");
            owner.setText("--");
            type.setText("--");
            location.setText("--");
            status.setText("--");
            member1.setText("--");
            member2.setText("--");
            member3.setText("--");
            member4.setText("--");
            member5.setText("--");
            m1.setVisibility(View.INVISIBLE);
            m2.setVisibility(View.INVISIBLE);
            m3.setVisibility(View.INVISIBLE);
            m4.setVisibility(View.INVISIBLE);
            m5.setVisibility(View.INVISIBLE);
            history1.setText("--");
            history2.setText("--");
            quit.setClickable(false);
        }else {

            team.setText(teamInfo.getName());
            owner.setText(teamInfo.getOwner());
            type.setText("Matrice 200");
            location.setText("unnc");

            /**checks if there is such number of members*/
            if(!mem.getName1().equals("0")){
                member1.setText(mem.getName1());
            }else{
                member1.setVisibility(View.INVISIBLE);
                m1.setVisibility(View.INVISIBLE);
            }
            if(!mem.getName2().equals("0")){
                member2.setText(mem.getName2());
            }else{
                member2.setVisibility(View.INVISIBLE);
                m2.setVisibility(View.INVISIBLE);
            }
            if(!mem.getName3().equals("0")){
                member3.setText(mem.getName3());
            }else{
                member3.setVisibility(View.INVISIBLE);
                m3.setVisibility(View.INVISIBLE);
            }
            if(!mem.getName4().equals("0")){
                member4.setText(mem.getName4());
            }else{
                member4.setVisibility(View.INVISIBLE);
                m4.setVisibility(View.INVISIBLE);
            }
            if(!mem.getName5().equals("0")){
                member5.setText(mem.getName5());
            }else{
                member5.setVisibility(View.INVISIBLE);
                m5.setVisibility(View.INVISIBLE);
            }
            history1.setText("--");
            history2.setText("--");
        }
    }

    /**start animation*/
    private void openAnimate(){
        refresh.animate().rotation(360).setDuration(600).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                refresh.setRotation(0);
                refresh.setEnabled(true);
            }

        });
    };


    /**thread1*/
    private void Quit() throws InterruptedException {

        Runner4 r4 = new Runner4();
        Thread t4 = new Thread(r4, "Thread-D");
        t4.start(); //start thread
        t4.join(); //wait thread to finish and block main thread

    }

    /**run quit thread*/
    class Runner4 implements Runnable{
        @Override
        public void run() {
            UserAction act = new UserAction();
            act.quitTeam();
        }
    }

    /**thread2*/
    private void Update() throws InterruptedException {

        Runner5 r5 = new Runner5();
        Thread t5 = new Thread(r5, "Thread-D");
        t5.start(); //start thread
        t5.join(); //wait thread to finish and block main thread

    }

    /**run update thread*/
    class Runner5 implements Runnable{
        @Override
        public void run() {
            UserAction act = new UserAction();
            info.storeInfo(act.getUserInfo(info.getUsername()));
            act.getTaskInfo();
            act.getTeamInfo(info.getTeamId());
            act.findMembers(info.getTeamId());
        }
    }
}
