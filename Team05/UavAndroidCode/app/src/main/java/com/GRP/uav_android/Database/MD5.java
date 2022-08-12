package com.GRP.uav_android.Database;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mochuan
 * @version 1.0
 * @date 2020/3/8
 * @description: the class used for password encryption;
 **/
public class MD5 {

    /**
     * get the salt from the database
     * @param username
     * @param conn the connection to the database
     */
    public String getSalt(String username, Connection conn) {
        String sql = "SELECT * FROM t_user WHERE username =?";

        try {
            PreparedStatement pres = conn.prepareStatement(sql);
            pres.setString(1, username);
            ResultSet res = pres.executeQuery();
            if (res.next()) {
                String salt = res.getString("salt");
                return salt;
            } else {
                return "0";
            }
        } catch (SQLException e) {
            return "0";
        }
    }

    /**
     * get the password after the encryption
     * @param username
     * @param password user's password
     * @param conn connection
     */
    public String GetMD5Password(String username, String password, Connection conn) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            String salt = getSalt(username, conn);
            String passwordWithSalt = salt + password + salt;
            byte[] bytes = md5.digest((passwordWithSalt).getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}

