package com.GRP.uav_android.ExistTeamPage;

/**
 * @author Mochuan
 * @version 1.1
 * @date 2020/3/17
 * @description: Store the team information
 **/

public class Team {
    private String id;
    private String name;
    private String owner;
    private String owner_id;
    private String c_time;
    private String creator;
    private String UAV_code;

    //constructors
    private Team(){};
    public Team(String teamId, String teamName, String Owner, String creator, String UAV_code, String Created_time, String owner_id){
        id = teamId;
        name = teamName;
        owner = Owner;
        this.creator = creator;
        this.UAV_code = UAV_code;
        c_time = Created_time;
        this.owner_id = owner_id;
    }


    private static Team instance;

    /**
     * lazy man method to keep team info
     */
    public static synchronized Team getInstance(){
        if (instance == null) {
            instance = new Team();
        }
        return instance;
    }

    /**
     * getters and setters
     */
    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getOwner(){
        return owner;
    }

    public String getCreator(){ return creator;}

    public String getUAV_code(){return UAV_code;}

    public String getC_time(){return c_time;}

    public String getOwner_id(){return  owner_id;}

    public static void setInstance(Team instance) {
        Team.instance = instance;
    }
}
