package com.example.skripsicustomer1;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfilePage extends AppCompatActivity {

    TextView nameProfile;
    TextView emailProfile;
    TextView handPhoneProfile;
    EditText oldPassword;
    EditText newPassword;
    EditText reNewPassword;
    Button submitChangePassword;

    private String formOldPassword;
    private String formNewPassword;
    private String formReNewPassword;
    private Boolean flag = false;

    Customer customer = new Customer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Profile");
        getUserData();
        initVariable();
        submitChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (! validateForm()) {
                    final Dialog dialog = new Dialog(ProfilePage.this);
                    dialog.setContentView(R.layout.custom_dialog_confirmation_with_password);
                    dialog.setTitle("Info");

                    Button agree = (Button) dialog.findViewById(R.id.agreeTopUpWithPassword);
                    Button disAgree = (Button) dialog.findViewById(R.id.disagreeTopUpWithPassword);
                    final EditText password = (EditText) dialog.findViewById(R.id.customDialogPassword);

                    disAgree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    agree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String passwordConfirmation = password.getText().toString().trim();
                            if (customer.getPassword().equals(passwordConfirmation)) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.updatePassword(formNewPassword)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    DatabaseReference dbCustomer = FirebaseDatabase.getInstance().getReference("Customers").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                    Map<String, Object> update = new HashMap<String, Object>();
                                                    update.put("password",formNewPassword);
                                                    dbCustomer.updateChildren(update);
                                                    Toast.makeText(ProfilePage.this,"Berhasil ganti kata sandi",Toast.LENGTH_LONG).show();
                                                    finish();
                                                    dialog.dismiss();
                                                } else {
                                                    Toast.makeText(ProfilePage.this, "Gagal ganti kata sandi", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }
                    });
                    dialog.show();
                }
            }
        });
    }
    public boolean validateForm() {
        formOldPassword = oldPassword.getText().toString().trim();
        formNewPassword = newPassword.getText().toString().trim();
        formReNewPassword = reNewPassword.getText().toString().trim();
        flag = false;
        if (TextUtils.isEmpty(formOldPassword)) {
            flag = true;
            Toast.makeText(this,"Password lama harus diisi", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(formNewPassword)) {
            flag = true;
            Toast.makeText(this,"Password baru harus diisi", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(formReNewPassword)) {
            flag = true;
            Toast.makeText(this,"Password ulang baru harus diisi", Toast.LENGTH_SHORT).show();
        } else if (!formNewPassword.equals(formReNewPassword)) {
            flag = true;
            Toast.makeText(this,"Password baru harus sama dengan password ulang baru",Toast.LENGTH_SHORT).show();
        } else if (!customer.getPassword().equals(formOldPassword)) {
            flag = true;
            Toast.makeText(this,"Password lama salah",Toast.LENGTH_SHORT).show();
        }
        return flag;
    }
    public void initVariable() {
        oldPassword = (EditText) findViewById(R.id.oldPassword);
        newPassword = (EditText) findViewById(R.id.newPassword);
        reNewPassword = (EditText) findViewById(R.id.reNewPassword);
        nameProfile = (TextView) findViewById(R.id.nameProfile);
        emailProfile = (TextView) findViewById(R.id.emailProfile);
        handPhoneProfile = (TextView) findViewById(R.id.handPhoneProfile);
        submitChangePassword = (Button) findViewById(R.id.submitChangePassword);
    }
    public void getUserData () {
        DatabaseReference dbCustomer = FirebaseDatabase.getInstance().getReference("Customers").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        dbCustomer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                customer = dataSnapshot.getValue(Customer.class);
                nameProfile.setText(customer.getUsername());
                emailProfile.setText(customer.getEmail());
                handPhoneProfile.setText(customer.getNumber_handphone());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
