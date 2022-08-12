package com.GRP.uav_android.MainPage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import com.GRP.uav_android.Database.UserInfo;
import com.GRP.uav_android.FragmentOfHome.MyUAV.MyUAVFragment;
import com.GRP.uav_android.FragmentOfHome.Team.TeamFragment;
import com.GRP.uav_android.FragmentOfHome.Profile.ProfileFragment;
import com.GRP.uav_android.R;
import com.GRP.uav_android.Simulator.SimulatorActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import java.net.URI;
import java.net.URISyntaxException;
import tech.gusavila92.websocketclient.WebSocketClient;
/**
 * @author Mochuan Weichen
 * @version 2.0
 * @date 2020/4/09
 * @description: this is the activity for the navigation bar actions, also the base for the web-socket
 **/

public class HomeActivity extends AppCompatActivity{

    BottomNavigationView navView;
    public static WebSocketClient webSocketClient;
    UserInfo info = UserInfo.getInstance();
    private String[] receivedMsg;
    String alertMsg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        /**create web-socket here*/
        createWebSocketClient();

        /**change color for the selected menu*/
        navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new MyUAVFragment()).commit();
            navView.getMenu().getItem(1).setChecked(true);
        }

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.navigation_home:
                        /**show team page*/
                        fragment = new TeamFragment();
                        break;
                    case R.id.navigation_dashboard:
                        /**show my uav page*/
                        fragment = new MyUAVFragment();
                        break;
                    case R.id.navigation_notifications:
                        /**show profile page*/
                        fragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
                return true;
            }
        });

    }

    /**
     * create web-socket client
     */
    private void createWebSocketClient() {
        URI uri;
        try {
            // Connect to local host
            uri = new URI("ws://10.0.2.2:8080/websocket/" + UserInfo.getInstance().getUserId() + "_app");
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
                Log.e("WebSocket", "Session is starting");
                webSocketClient.send("Hello World!");
            }
            @Override
            public void onTextReceived(String msg) {
                Log.i("WebSocket", "Message received: "+ msg);
                receivedMsg = msg.split(";");
                String operation = receivedMsg[0];

                switch (operation){
                    case "Connection success!":
                        break;
                    case "2":
                        alertMsg = "Please View Task Invitation in MyUAV";
                        String taskId = receivedMsg[1];
                        MyUAVFragment.task_id = taskId;
                        MyUAVFragment.setFlag(1);
                        break;
                    case "3":
                        Intent intent = new Intent(HomeActivity.this, SimulatorActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        alertMsg = receivedMsg[0];
                        break;
                }
                new Thread(){

                    public void run() {
                        Log.i("log", "run");
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(), alertMsg, Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    };

                }.start();

            }
            @Override
            public void onBinaryReceived(byte[] data) {
            }
            @Override
            public void onPingReceived(byte[] data) {
            }
            @Override
            public void onPongReceived(byte[] data) {
            }
            @Override
            public void onException(Exception e) {
                System.out.println(e.getMessage());
            }
            @Override
            public void onCloseReceived() {
                Log.i("WebSocket", "Closed ");
                System.out.println("onCloseReceived");
            }

        };
        webSocketClient.setConnectTimeout(10000);
        webSocketClient.setReadTimeout(60000);
        webSocketClient.enableAutomaticReconnection(5);
        webSocketClient.connect();
    }

}
