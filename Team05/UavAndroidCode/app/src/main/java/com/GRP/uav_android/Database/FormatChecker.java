package com.GRP.uav_android.Database;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mochuan
 * @version 1.0
 * @date 2020/3/7
 * @description: checks the format of the input email and phone number;
 **/

public class FormatChecker {

    /**
     * checks the format of the phone number
     * @param mobile A String of mobile phone number
     */
    public boolean isPhone(String mobile){
    if (mobile.length() != 11)
    {
        return false;
    }else{

         // China Mobile
        String pat1 = "^((13[4-9])|(147)|(15[0-2,7-9])|(178)|(18[2-4,7-8]))\\d{8}|(1705)\\d{7}$";
        //China Unicom
        String pat2  = "^((13[0-2])|(145)|(15[5-6])|(176)|(18[5,6]))\\d{8}|(1709)\\d{7}$";
        //China Telecom
        String pat3  = "^((133)|(153)|(177)|(18[0,1,9])|(149))\\d{8}$";
        //Virtual
        String pat4 = "^((170))\\d{8}|(1718)|(1719)\\d{7}$";

            Pattern pattern1 = Pattern.compile(pat1);
            Matcher match1 = pattern1.matcher(mobile);
            boolean isMatch1 = match1.matches();
            if(isMatch1){
                return true;
            }
            Pattern pattern2 = Pattern.compile(pat2);
            Matcher match2 = pattern2.matcher(mobile);
            boolean isMatch2 = match2.matches();
            if(isMatch2){
                 return true;
            }
            Pattern pattern3 = Pattern.compile(pat3);
            Matcher match3 = pattern3.matcher(mobile);
            boolean isMatch3 = match3.matches();
            if(isMatch3){
                return true;
            }
            Pattern pattern4 = Pattern.compile(pat4);
             Matcher match4 = pattern4.matcher(mobile);
            boolean isMatch4 = match4.matches();
            if(isMatch4){
                return true;
            }
            return false;
        }
    }


    /**
     * checks the format of the email
     * @param email A String of email address
     */
    public boolean isEmail(String email){
        if (email == null)
            return false;
        String rule = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(rule);
        matcher = pattern.matcher(email);
        if (matcher.matches())
            return true;
        else
            return false;
    }

    /**
     * checks if there is a word in the String
     * @param c A character
     * @param word  A string
     */
    private boolean containsOneWord(char c , String word){
        char[] array = word.toCharArray();
        int count = 0 ;
        for(Character ch : array){
            if(c == ch) {
                count++;
            }
        }
        return count==1 ;
    }

    /**
     * checks if the prefix is all words
     * @param prefix A string
     */
    private boolean isAllWords(String prefix){
        char[] array = prefix.toCharArray();
        for(Character ch : array){
            if(ch<'A' || ch>'z' || (ch<'a' && ch>'Z')) return false ;
        }
        return true;
    }

    /**
     * checks if the prefix is all words and numbers
     * @param middle A string
     */
    private boolean isAllWordsAndNo(String middle){
        char[] array = middle.toCharArray();
        for(Character ch : array){
            if(ch<'0' || ch > 'z') return false ;
            else if(ch >'9' && ch <'A') return false ;
            else if(ch >'Z' && ch <'a') return false ;
        }
        return true ;
    }
}
