package com.GRP.uav_android.FragmentOfHome.MyUAV;

/**
 * @author Mochuan
 * @version 1.0
 * @date 2020/3/27
 * @description: Store the Task information
 **/

public class Task {
    private String TeamName, captain, uav_amount,distance, velocity, height, height_restriction;

    private static Task instance;

    /**constructors*/
    private Task(){};


    public Task(String TeamName, String captain, String uav_amount, String distance, String velocity, String height, String height_restriction){
        this.TeamName = TeamName;
        this.captain = captain;
        this.uav_amount = uav_amount;
        this.distance = distance;
        this.velocity = velocity;
        this.height = height;
        this.height_restriction = height_restriction;
    }

    /**lazy man method to keep task info*/
    public static synchronized Task getInstance(){
        if (instance == null) {
            instance = new Task();
        }
        return instance;
    }

    /**getters and setters*/
    public static void setInstance(Task instance) {
        Task.instance = instance;
    }

    public String getTeamName() {
        return TeamName;
    }

    public String getCaptain() {
        return captain;
    }

    public String getUav_amount() {
        return uav_amount;
    }

    public String getDistance() {
        return distance;
    }

    public String getVelocity() {
        return velocity;
    }

    public String getHeight() {
        return height;
    }

    public String getHeight_restriction() {
        return height_restriction;
    }
}
