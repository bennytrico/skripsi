package com.example.skripsicustomer1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ProfilePage extends AppCompatActivity {

    EditText oldPassword;
    EditText newPassword;
    EditText reNewPassword;

    private String formOldPassword;
    private String formNewPassword;
    private String formReNewPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        getSupportActionBar().setTitle("Profile");
        initVariable();

    }
    public void validateForm() {

    }
    public void initVariable() {
        oldPassword = (EditText) findViewById(R.id.oldPassword);
        newPassword = (EditText) findViewById(R.id.newPassword);
        reNewPassword = (EditText) findViewById(R.id.reNewPassword);
    }
}
