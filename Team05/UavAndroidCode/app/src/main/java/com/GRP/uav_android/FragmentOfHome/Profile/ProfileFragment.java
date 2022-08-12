package com.GRP.uav_android.FragmentOfHome.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.GRP.uav_android.Database.FormatChecker;
import com.GRP.uav_android.Database.UserAction;
import com.GRP.uav_android.Database.UserInfo;
import com.GRP.uav_android.MainPage.HomeActivity;
import com.GRP.uav_android.R;
import com.GRP.uav_android.loginPage.MainActivity;


/**
 * @author Mochuan Weichen
 * @version 2.3
 * @date 2020/4/20 update
 * @description: profile page
 **/


public class ProfileFragment extends Fragment {

    private Button edit;
    private Button e2;
    private Button e3;
    private Button e4;
    private Button logout;
    private String EMAIL;
    private String PHONE;
    private String GENDER;
    TextView t_name;
    EditText email;
    EditText phone;
    TextView c_time;
    RadioButton male;
    RadioButton female;

    UserInfo info = UserInfo.getInstance();


    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,
                             @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        final FormatChecker checker = new FormatChecker();
        //get all the elements
        t_name = (TextView) view.findViewById(R.id.t_name);
        email = (EditText) view.findViewById(R.id.email);
        phone = (EditText) view.findViewById(R.id.phone);
        c_time = (TextView) view.findViewById(R.id.c_time);
        male = (RadioButton) view.findViewById(R.id.male);
        female = (RadioButton) view.findViewById(R.id.female);
        edit = (Button) view.findViewById(R.id.edit);
        e2 = (Button) view.findViewById(R.id.button);
        e3 = (Button) view.findViewById(R.id.button1);
        e4 = (Button) view.findViewById(R.id.button2);
        logout = (Button) view.findViewById(R.id.logout);
        updateInfo();

        //radiobutton onclick
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(view.getId()){
                    case R.id.male:
                        male.setChecked(true);
                        female.setChecked(false);
                        break;
                }
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(view.getId()){
                    case R.id.female:
                        female.setChecked(true);
                        male.setChecked(false);
                        break;
                }
            }
        });

       /**edit button listener
        * if click, the info would be editable
        * and this button will become "save"
        * the log out button will become "cancle"
        **/
        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(e2.getVisibility() == View.INVISIBLE){
                    /**if this button is currently "edit"*/
                    showEdit();
                }else if(edit.getText().toString() == "SAVE"){
                    /**if this button is currently "save"*/
                    /**checkers to check whether the input format is correct*/
                    if(!checker.isPhone(phone.getText().toString())){
                        phone.setText(info.getPhone());
                        Toast toast=Toast.makeText(getActivity(),"Invalid Phone number format!",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }else if(!checker.isEmail(email.getText().toString())){
                        email.setText(info.getEmail());
                        Toast toast=Toast.makeText(getActivity(),"Invalid Email format!",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }else{
                        Toast toast=Toast.makeText(getActivity(),"Modified Successfully",Toast.LENGTH_SHORT    );
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    //save
                    EMAIL = email.getText().toString();
                    PHONE = phone.getText().toString();
                    if(male.isChecked()){
                        GENDER = "1";
                    }else if(female.isChecked()){
                        GENDER = "0";
                    }

                    /**change the info in the database*/
                    try {
                        changeUserInfo();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    /**disable all the editable elements*/
                    hideEdit();

                }
            }
        });


        /**logout button listener
         * if click logout, the user will logout
         * if the button is cancel, the info won't be changed
         **/
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("LOG OUT".equals(logout.getText())) {
                    /**if the button is "LOG OUT"*/
                    /**close the web-socket*/
                    HomeActivity.webSocketClient.close();
                    /**log out function*/
                    try {
                        logout();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    /**show login page*/
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                }else{
                    updateInfo();
                    hideEdit();
                }
            }
        });

        /**editable text filed listener*/
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(!checker.isEmail(email.getText().toString())){
                        Toast toast=Toast.makeText(getActivity(),"Invalid Email format!",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        email.setText(info.getEmail());
                    }
                }
            }
        });

        phone.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) {
                    if(!checker.isPhone(phone.getText().toString())){
                        Toast toast=Toast.makeText(getActivity(),"Invalid Phone format!",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        phone.setText(info.getPhone());
                    }
                }
            }
        });

        return view;

    }

    /**thread1*/
    class Runner implements Runnable{
        @Override
        public void run() {
            UserAction act = new UserAction();
            act.logout(info.getUsername());
        }
    }

    /**logout*/
    private void logout() throws InterruptedException{
        Runner r1 = new Runner();
        Thread t1 = new Thread(r1, "Thread-logout");
        t1.start(); //start thread
        t1.join(); //wait thread to finish and block main thread
    }

    /**change user info*/
    private void changeUserInfo() throws InterruptedException {

        Runner2 r1 = new Runner2();
        Thread t1 = new Thread(r1, "Thread-changeInfo");
        t1.start(); //start thread
        t1.join(); //wait thread to finish and block main thread

    }

    /**thread2*/
    class Runner2 implements Runnable{
        @Override
        public void run() {
            UserAction act = new UserAction();
            act.changeInfo(EMAIL, PHONE, GENDER);
            info.setEmail(EMAIL);
            info.setPhone(PHONE);
            info.setGender(GENDER);
        }
    }
    /**update user info*/
    public void updateInfo(){

        t_name.setText(info.getUsername());
        email.setText(info.getEmail());
        phone.setText(info.getPhone());
        c_time.setText(info.getcTime());
        if(info.getGender().equals("1")) {
            male.setChecked(true);
            female.setChecked(false);
        }else if(info.getGender().equals("0")){
            male.setChecked(false);
            female.setChecked(true);
        }else{
            male.setChecked(false);
            female.setChecked(false);
        }
        e2.setVisibility(View.INVISIBLE);
        e3.setVisibility(View.INVISIBLE);
        e4.setVisibility(View.INVISIBLE);
    }

    /**hide all the editable view*/
    public void hideEdit(){
        e2.setVisibility(View.INVISIBLE);
        e3.setVisibility(View.INVISIBLE);
        e4.setVisibility(View.INVISIBLE);
        t_name.setEnabled(false);
        email.setEnabled(false);
        phone.setEnabled(false);
        male.setEnabled(false);
        female.setEnabled(false);
        edit.setText("EDIT");
        logout.setText("LOG OUT");
    }

    /**show all the editable view*/
    public void showEdit(){
        e2.setVisibility(View.VISIBLE);
        e3.setVisibility(View.VISIBLE);
        e4.setVisibility(View.VISIBLE);
        t_name.setEnabled(true);
        email.setEnabled(true);
        phone.setEnabled(true);
        male.setEnabled(true);
        female.setEnabled(true);
        edit.setText("SAVE");
        logout.setText("CANCLE");
    }

}
