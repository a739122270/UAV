package com.GRP.uav_android.FragmentOfHome.Team;

/**
 * @author Mochuan
 * @version 1.3
 * @date 2020/4/2
 * @description: Store the member information
 **/
public class Member {
    private String name1;
    private String name2;
    private String name3;
    private String name4;
    private String name5;

    /**constructors*/
    private Member(){};

    public Member(String n1, String n2, String n3, String n4, String n5){
        name1 = n1;
        name2 = n2;
        name3 = n3;
        name4 = n4;
        name5 = n5;
    };

    private static Member instance;

    public static synchronized Member getInstance(){
        if (instance == null) {
            instance = new Member();
        }
        return instance;
    }

    /**getters and setter*/
    public static void setInstance(Member instance) {
        Member.instance = instance;
    }

    public String getName1() {
        return name1;
    }

    public String getName2() {
        return name2;
    }

    public String getName3() {
        return name3;
    }

    public String getName4() {
        return name4;
    }

    public String getName5() {
        return name5;
    }
}
