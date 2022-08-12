package com.GRP.uav_android.ExistTeamPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.GRP.uav_android.Database.UserInfo;
import com.GRP.uav_android.MainPage.HomeActivity;
import com.GRP.uav_android.R;
import com.GRP.uav_android.Database.UserAction;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import tech.gusavila92.websocketclient.WebSocketClient;

/**
 * @author Mochuan Shiyang Weichen
 * @version 1.4
 * @date 2020/4/19 update
 * @description: user can search and join a team in this page
 **/
public class ExistTeamActivity extends AppCompatActivity implements TeamListAdapter.OnItemClickListener{

    private RecyclerView mRView;
    private TeamListAdapter teamListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText team_info;
    private ImageView back;
    static private ArrayList<Team> teamList = new ArrayList<>();
    private int cur_teamID;

    private Dialog myDialog;

    /**
     * shows the search bar and scrollable team list
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exist_team);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
        myDialog = new Dialog(this);

        try {
            createExampleList(); //get the team list here
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        team_info = findViewById(R.id.edittext);
        team_info.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


    }

    /**
     * pop up the detailed team info and a join button
     * @param p the index of the selected team
     */
    public void showPopup(int p){
        TextView txt_name;
        TextView txt_owner;
        TextView txt_creator;
        TextView txt_time;
        TextView txt_code;
        cur_teamID = p;

        TextView txtclose;
        Button btnJoin;
        myDialog.setContentView(R.layout.popup);
        txtclose = (TextView)myDialog.findViewById(R.id.txtclose);
        //btnJoin = (Button) myDialog.findViewById(R.id.btn_join);
        txt_name = (TextView) myDialog.findViewById(R.id.txt_name);
        txt_owner = (TextView) myDialog.findViewById(R.id.txt_owner);
        txt_creator = (TextView) myDialog.findViewById(R.id.txt_creator);
        txt_time = (TextView) myDialog.findViewById(R.id.txt_time);
        txt_code = (TextView) myDialog.findViewById(R.id.txt_code);


        txt_name.setText(teamList.get(p).getName());
        txt_owner.setText(teamList.get(p).getOwner());
        txt_creator.setText(teamList.get(p).getCreator());
        txt_time.setText(teamList.get(p).getC_time());
        txt_code.setText(teamList.get(p).getUAV_code());

        txtclose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    /**
     * filter the item that contains "text" in the list
     * @param text the input text
     */
    private void filter(String text){
        ArrayList<Team> filteredList = new ArrayList<>();
        for(Team item: teamList){
            if(item.getId().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }else if(item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }else if(item.getOwner().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        teamListAdapter.filterList(filteredList);
    }

    /**
     * get the teamlist
     */
    private void createExampleList() throws InterruptedException {

            Runner1 r1 = new Runner1();
            Thread t1 = new Thread(r1, "Thread-A");
            t1.start(); //start thread
            t1.join();
            buildRecycleView(); //build UI view



    }

    /**
     *  A thread to get team list
     */
    class Runner1 implements Runnable{
        @Override
        public void run() {
            UserAction act = new UserAction();
            teamList = act.getTeamInfo_demo();
        }
    }

    /**
     * build recycle view to refresh the list view
     */
    private void buildRecycleView(){
        mRView = findViewById(R.id.recyclerView);
        mRView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        teamListAdapter = new TeamListAdapter(teamList, this);
        mRView.setLayoutManager(mLayoutManager);
        mRView.setAdapter(teamListAdapter);
    }


    @Override
    public void onItemClick(int position) {
        showPopup(position);
    }


    /**
     * send the team join request to the web end
     * @param view current view
     */
    public void sendApplication(View view) {
        Log.e("WebSocket", "Button was clicked");
        // Send button id string to WebSocket Server
        HomeActivity.webSocketClient.send("1" + ";" + teamList.get(cur_teamID).getOwner() + ";" + teamList.get(cur_teamID).getOwner_id() + ";" + UserInfo.getInstance().getUsername() + ";" + UserInfo.getInstance().getUserId() + ";");
        //HomeActivity.webSocketClient.send("1");
    }
}
