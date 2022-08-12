package com.GRP.uav_android.Database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Mochuan
 * @version 1.0
 * @date 2020/3/7
 * @description: the class that sets up the connection with the database using JDBC
 **/

public class JdbcUtil {

    private static JdbcUtil instance;

    public static JdbcUtil getInstance(){
        if(instance == null){
            instance = new JdbcUtil();
        }
        return instance;
    }

    /**
     * set up the connection with database
     */
    public Connection getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");

            return DriverManager.getConnection("jdbc:mysql://120.78.198.16:3306/uav?useSSL=false","root","grp05");
        } catch (Exception e){
            return null;
        }
    }

}