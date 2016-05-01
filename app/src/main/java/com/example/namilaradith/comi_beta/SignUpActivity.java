package com.example.namilaradith.comi_beta;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    private EditText userName;
    private EditText fname;
    private EditText lname;
    private EditText email;
    private EditText mobile;
    private EditText password;
    private EditText cnPassword;
    private EditText birthDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setElements();




    }

    public void onStart(){
        super.onStart();
        birthDate = (EditText) findViewById(R.id.txtBdate);
        birthDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DialogFragment newFragment = new DatePickerFragment(v);
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
            }
        });

    }

    public void showDatePickerDialog(View v) {
//        DialogFragment newFragment = new DatePickerFragment(v);
//        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void setElements(){
        userName = (EditText) findViewById(R.id.txtUserName);
        fname = (EditText) findViewById(R.id.txtFname);
        lname = (EditText) findViewById(R.id.txtLname);
        email = (EditText) findViewById(R.id.txtEmail);
        password = (EditText) findViewById(R.id.txtPassword);
        cnPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        mobile = (EditText) findViewById(R.id.txtMobile);
    }

    public void submitForm(View v){

           userName.setError("username must be filled");


    }

    public String getValueAsString(EditText t){
        return t.getText().toString();
    }

}
