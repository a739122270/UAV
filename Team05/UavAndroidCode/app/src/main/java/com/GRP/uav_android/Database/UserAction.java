package com.GRP.uav_android.Database;

import android.util.Log;

import com.GRP.uav_android.ExistTeamPage.Team;
import com.GRP.uav_android.FragmentOfHome.MyUAV.Task;
import com.GRP.uav_android.FragmentOfHome.Team.Member;
import com.GRP.uav_android.Simulator.Position;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * @author Mochuan ShiYang
 * @version 2.1
 * @date 2020/4/27 update
 * @description: the class of most of the actions related to the database
 * @var info An instance of class UserInfo
 * @var team An instance of class Team
 * @var task An instance of class Task
 * @var teamList An arrayList contains the information of Teams
 * @var position An arrayList contains positions that drone needs to pass by
 **/

public class UserAction {

    private ArrayList<Team> teamList = new ArrayList<>();
    private ArrayList<Position> position = new ArrayList<>();


    JdbcUtil jdbcUtil = JdbcUtil.getInstance();
    Connection conn = jdbcUtil.getConnection();
    UserInfo info = UserInfo.getInstance();
    Team team = Team.getInstance();
    Task task = Task.getInstance();

    /**
     * check whether there is a username corresponding to this password
     * @param username
     * @param password
     */
    public boolean login(String username, String password) {
        if (conn == null) {
            Log.i(TAG, "login:conn is null");
            return false;
        } else {
            String sql = "SELECT * FROM t_user WHERE username=?";
            //get md5password
            MD5 md5 = new MD5();
            String md5Psw = md5.GetMD5Password(username, password, conn);
            try {
                PreparedStatement pres = conn.prepareStatement(sql);
                pres.setString(1, username);
                ResultSet res = pres.executeQuery();

                if (res.next()) {
                    String psw = res.getString("password");
                    if (md5Psw.equals(psw)) {
                        //store user information
                        info.storeInfo(getUserInfo(username));
                        getTeamInfo(info.getTeamId());
                        getTaskInfo();
                        findMembers(info.getTeamId());
                        updateDeviceInfo(username,info.getdevice_sn());
                        pres.close();
                        res.close();
                        conn.close();
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }

            } catch (SQLException e) {
                return false;
            }
        }
    }

    /**
     * logout: update the lastLoginTime to Null
     * @param username
     */
    public void logout(String username) {
        if (conn == null) {
            Log.i(TAG, "login:conn is null");
        } else {
            try {
                String sql = "update t_user set lastLogin_time = ? where username = ?";
                PreparedStatement pres = conn.prepareStatement(sql);
                pres.setNull(1, Types.DATE);
                pres.setString(2, username);
                pres.executeUpdate();
                pres.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * update the new device sn and lastLoginTime
     * @param username
     * @param sn the device serial number
     */
    public void updateDeviceInfo(String username, String sn){
        if (conn == null) {
            Log.i(TAG, "conn is null_");
        } else {
            Log.i(TAG, "connection good");
            try{
                String sql = "update t_user set device_sn = ?, lastLogin_time = ? where username = ?";
                PreparedStatement pres = conn.prepareStatement(sql);
                pres.setString(1, sn);
                pres.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
                pres.setString(3, username);
                pres.executeUpdate();
                pres.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * checks the number of people that already in the task
     * @param task_id
     */
    public int checkTaskPlace(String task_id){
        if (conn == null) {
            Log.i(TAG, "conn is null_");
        } else {
            Log.i(TAG, "connection good");
            try {
                String sql = "select * from t_user where task_id = ?";
                PreparedStatement pres = conn.prepareStatement(sql);
                pres.setString(1, task_id);
                ResultSet res = pres.executeQuery();
                int num = 0;
                while(res.next()) {
                    num++;
                }
                return num;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * find user id according to the username
     * @param username
     */
    public String findUserId(String username) {
        if (conn == null) {
            Log.i(TAG, "conn is null_");
        } else {
            Log.i(TAG, "connection good");
            try {
                String sql = "select * from t_user where username = ?";
                PreparedStatement pres = conn.prepareStatement(sql);
                pres.setString(1, username);
                ResultSet res = pres.executeQuery();
                if (res.next()) {
                    String userid = res.getString("user_id");
                    return userid;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "unknown";
    }

    /**
     * get the position that the drone needs to pass by
     */
    public ArrayList<Position> getPosition(String userId, String username) {
        if (conn == null) {
            Log.i(TAG, "conn is null_");
        } else {
            Log.i(TAG, "connection good");
            try {
                String sql = "select * from t_point where task_id = ? order by point_order";
                PreparedStatement pres = conn.prepareStatement(sql);
                pres.setString(1, info.getTask_id());
                ResultSet res = pres.executeQuery();
                while (res.next()){
                    if(res.getString("user_id") == null && res.getString("created_user").equals(username)){
                        double lat = res.getDouble("latitude");
                        double longt = res.getDouble("longitude");
                        position.add(new Position(lat, longt));
                    }else if(res.getString("user_id") == null && !res.getString("created_user").equals(username)){
                        continue;
                    }
                    else if(res.getString("user_id").equals(userId)) {
                        double lat = res.getDouble("latitude");
                        double longt = res.getDouble("longitude");
                        position.add(new Position(lat, longt));
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return position;
    }

    /**
     * get the detailed task information
     */
    public void getTaskInfo(){
        if (conn == null) {
            Log.i(TAG, "conn is null_");
        } else {
            Log.i(TAG, "connection good");
            try {
                String sql = "select * from t_task where task_id = ?";
                PreparedStatement pres = conn.prepareStatement(sql);
                pres.setString(1, info.getTask_id());
                ResultSet res = pres.executeQuery();
                if (res.next()) {

                    String captain = res.getString("captain");
                    String uav_amount = res.getString("uav_amount");
                    String distance = res.getString("uav_distance");
                    String velocity = res.getString("flight_velocity");
                    String height = res.getString("flight_height");
                    String height_restriction = res.getString("height_limit");
                    task = new Task(team.getName(), captain, uav_amount, distance, velocity, height, height_restriction);
                    Task.setInstance(task);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * get the detailed team information
     * @param teamId
     */
    public Team getTeamInfo(String teamId) {
        if (conn == null) {
            Log.i(TAG, "conn is null_");
        } else {
            Log.i(TAG, "connection good");
            try {
                String sql = "select * from t_team where team_id = ?";
                PreparedStatement pres = conn.prepareStatement(sql);
                pres.setString(1, teamId);
                ResultSet res = pres.executeQuery();
                if (res.next()) {
                    String TeamId = res.getString("team_id");
                    String TeamName = res.getString("team_name");
                    String Owner = res.getString("owner");
                    String owner_id = findUserId(Owner);
                    String Creator = res.getString("created_user");
                    String UAV_ID = res.getString("uav_id");
                    Date c_time = res.getDate("created_time");
                    String Created_time = getDateStr(c_time,"yyyy-MM-dd");
                    team = new Team(TeamId,TeamName, Owner,Creator,UAV_ID,Created_time,owner_id);
                    Team.setInstance(team);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return team;
    }

    /**
     * find the members in the same team
     * @param teamId
     */
    public void findMembers(String teamId) {
        String member[] = new String[5];
        for(int i =0; i < 5;i++){
            member[i] = "0";
        }
        if (conn == null) {
            Log.i(TAG, "conn is null_");
        } else {
            Log.i(TAG, "connection good");
            try {
                String sql = "select * from t_user where team_id = ?";
                int count = 0;
                PreparedStatement pres = conn.prepareStatement(sql);
                pres.setString(1, teamId);
                ResultSet res = pres.executeQuery();
                while (res.next()) {
                    member[count] = res.getString("username");
                    count++;
                }
             Member.setInstance(new Member(member[0],member[1],member[2],member[3],member[4]));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * update the team id to null
     */
    public void quitTeam() {
        if (conn == null) {
            Log.i(TAG, "conn is null_");
        } else {
            Log.i(TAG, "connection good");
            try {
                String sql = "update t_user set team_id = ?, task_id = ? where username=?";
                PreparedStatement pres = conn.prepareStatement(sql);
                pres.setString(1, null);
                pres.setString(2, null);
                pres.setString(3,info.getUsername());
                pres.executeUpdate();
                pres.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * get the team list
     */
    public ArrayList<Team> getTeamInfo_demo() {
        if (conn == null) {
            Log.i(TAG, "conn is null_");
        } else {
            Log.i(TAG,"connection good");
            try {
                String sql = "select * from t_team where is_private = 0";
                PreparedStatement pres = conn.prepareStatement(sql);
                ResultSet res = pres.executeQuery();

                while (res.next()) {
                    String TeamId = res.getString("team_id");
                    String TeamName = res.getString("team_name");
                    String Owner = res.getString("owner");
                    String ownerid = findUserId(Owner);
                    String Creator = res.getString("created_user");
                    String UAV_ID = res.getString("uav_id");
                    Date c_time = res.getDate("created_time");
                    String Created_time = getDateStr(c_time,"yyyy-MM-dd");
                    teamList.add(new Team(TeamId, TeamName, Owner,Creator,UAV_ID,Created_time, ownerid));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return teamList;
    }


    /**
     * checks if there is a user was employed current device and haven't timed out
     * @param sn device serial number
     */
    public int cookie(String sn){
        info.setDevice_sn(sn);
        Log.d("the sn is %s", info.getdevice_sn());
        if(conn == null){
            Log.i(TAG, "conn is null");
            return -1;
        }
            try{
                    String sql = "select * from t_user WHERE device_sn=? order by lastLogin_time desc";
                    PreparedStatement pres = conn.prepareStatement(sql);
                    pres.setString(1, sn);
                    ResultSet res = pres.executeQuery();
                    Timestamp currentDate = new java.sql.Timestamp(System.currentTimeMillis());
                    if(res.next()) {
                        //device exists
                        String username = res.getString("username");
                        info.storeInfo(getUserInfo(username));
                        getTeamInfo(info.getTeamId());
                        getTaskInfo();
                        findMembers(info.getTeamId());
                        Timestamp date = res.getTimestamp("lastLogin_time");
                        res.close();
                        long diff;
                        if(date == null){
                            diff = currentDate.getTime();
                        }else{
                            diff = currentDate.getTime() - date.getTime();
                        }
                        float days = ((float)diff / (1000 * 60 * 60 * 24));
                        if(days > (float)0.5){
                            //> 12 hours time out
                            return 1;
                        }else{
                            return 0;
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            return 1;
            }


    /**
     * get the String format of date
     * @param date
     * @param format
     */
    public static String getDateStr(Date date, String format){
        if(format == null || format.isEmpty()){
            format = "yyyy-MM--dd HH:mm:ss";
        }
        SimpleDateFormat format1 = new SimpleDateFormat(format);
        return format1.format(date);
    }
    //get the information of the user
    public String[] getUserInfo(String username){
        String text[] = new String[10];
        if(conn == null){
            Log.i(TAG,"notification conn is null");
        }
        String sql = "SELECT * FROM t_user WHERE username=?";
        try{
            Log.d("TTTT", "this is text，content is：" + username );
            PreparedStatement pres = conn.prepareStatement(sql);
            pres.setString(1, username);
            ResultSet res = pres.executeQuery();
            if (res.next()) {
                text[0] = res.getString("username");
                text[1] = res.getString("phone");
                text[2] = res.getString("email");
                text[3] = res.getString("user_id");
                if(res.getString("team_id")==null){
                    text[4] = "no_team";
                }else{
                    text[4] = res.getString("team_id");
                }
                text[5] = res.getString("uav_id");
                Date c_time = res.getDate("created_time");
                //Date m_time = res.getDate("modified_time");
                text[6] = getDateStr(c_time,"yyyy-MM-dd");
                //text[7] = getDateStr(m_time,"yyyy-MM-dd");
                if(res.getString("gender")==null){
                    text[8] = "-1";
                }else{
                    text[8] = res.getString("gender");
                }
                if(res.getString("task_id") == null){
                    text[9] = "no_task";
                }else{
                    text[9] = res.getString("task_id");
                }
                Log.d("TTTT", "this is text，gender is ：" + text[8] );
                return text;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * update the new information of the user
     * @param email
     * @param phone
     * @param gender
     */
    public void changeInfo(String email, String phone, String gender) {
        if (conn == null) {
            Log.i(TAG, "login:conn is null");
        } else {

            //email
            if(!email.equals(info.getEmail())){
                String sql = "update t_user set email =? where username=?";
                try{
                    PreparedStatement pres = conn.prepareStatement(sql);
                    pres.setString(1, email);
                    pres.setString(2, info.getUsername());
                    pres.executeUpdate();
                    pres.close();
                    Log.i(TAG, "changeInfo: email changed");
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            //phone
            if(!phone.equals(info.getPhone())){
                String sql = "update t_user set phone =? where username=?";
                try{
                    PreparedStatement pres = conn.prepareStatement(sql);
                    pres.setString(1, phone);
                    pres.setString(2, info.getUsername());
                    pres.executeUpdate();
                    pres.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            //gender
            if(!gender.equals(info.getGender())){
                String sql = "update t_user set gender =? where username=?";
                try{
                    PreparedStatement pres = conn.prepareStatement(sql);
                    pres.setInt(1, Integer.valueOf(gender));
                    pres.setString(2, info.getUsername());
                    pres.executeUpdate();
                    pres.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            String time = "update t_user set modified_time=? where username=?";
            try{
                PreparedStatement pres = conn.prepareStatement(time);
                pres.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
                pres.setString(2,info.getUsername());
                pres.executeUpdate();
                pres.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void joinTask(String task_id){
        if(conn == null){
            Log.i(TAG, "login:conn is null");
        } else {
            String sql = "update t_user set task_id = ? where username = ?";
            try{
                PreparedStatement pres = conn.prepareStatement(sql);
                pres.setString(1, task_id);
                pres.setString(2, info.getUsername());
                pres.executeUpdate();
                pres.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * distribute the serial number in the task
     */
    public void set_uav_serial(){
        if(conn == null){
            Log.i(TAG, "login:conn is null");
        } else {
            String sql = "update t_point set user_id = ? where aircraft_id = ?";
            try{
                PreparedStatement pres = conn.prepareStatement(sql);
                pres.setString(1, info.getUserId());
                pres.setString(2, info.getAircraftId());
                pres.executeUpdate();
                pres.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void getAircraftId(){
        if(conn == null){
            Log.i(TAG, "login:conn is null");
        } else {
            String sql = "select * from t_point where user_id = ?";
            try {
                PreparedStatement pres = conn.prepareStatement(sql);
                pres.setString(1, info.getUserId());
                ResultSet res = pres.executeQuery();
                if(res.next()){
                    info.setAircraftId(res.getString("aircraft_id"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}

