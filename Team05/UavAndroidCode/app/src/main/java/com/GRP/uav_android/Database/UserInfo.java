package com.GRP.uav_android.Database;

/**
 * @author Mochuan
 * @version 1.3
 * @date 2020/3/10
 * @description: Store the user information
 **/

public class UserInfo {
    private String username;
    private String userId;
    private String teamId;
    private String uavId;
    private String email;
    private String phone;
    private String gender;
    private String cTime;
    private String mTime;
    private String aircraftId = "1";

    public String getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(String aircraftId) {
        this.aircraftId = aircraftId;
    }

    private String device_sn;
    private String task_id;

    //constructor
    private UserInfo() {
    }

    /**
     * store the info into the instance
     * @param txt An array of user info
     */
    public void storeInfo(String[] txt){
        username = txt[0];
        phone = txt[1];
        email = txt[2];
        userId = txt[3];
        teamId = txt[4];
        uavId = txt[5];
        cTime = txt[6];
        mTime = txt[7];
        gender = txt[8];
        task_id =  txt[9];
    }

    private static UserInfo instance;

    /**
     * lazy man method to keep user info
     */
    public static synchronized UserInfo getInstance(){
        if (instance == null) {
            instance = new UserInfo();
        }
        return instance;
    }

    /**
     * setters and getters
     */
    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getUavId() {
        return uavId;
    }

    public void setUavId(String uavId) {
        this.uavId = uavId;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDevice_sn(String device_sn){
        this.device_sn = device_sn;
    }


    public String getUsername() {
        return username;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public String getcTime() {
        return cTime;
    }

    public String getdevice_sn(){return device_sn;}

    public String getUserId(){return userId;}
}
