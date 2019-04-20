package com.example.skripsicustomer1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.skripsicustomer1.customer.HomePage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterPage extends AppCompatActivity {

    private EditText regisEmail;
    private EditText regisUsername;
    private EditText regisPassword;
    private EditText regisRePassword;
    private EditText regisNumberHandphone;
    private Button buttonRegister;
    private ProgressDialog progressDialog;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

//        db = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(this);

        regisEmail = (EditText) findViewById(R.id.registerEmail);
        regisUsername = (EditText) findViewById(R.id.registerUsername);
        regisNumberHandphone = (EditText) findViewById(R.id.registerNumberHandphone);
        regisPassword = (EditText) findViewById(R.id.registerPassword);
        regisRePassword = (EditText) findViewById(R.id.registerRePassword);
        buttonRegister = (Button) findViewById(R.id.btnSubmit);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {
                    Intent startIntent = new Intent(getApplicationContext(), HomePage.class);
                    startActivity(startIntent);
                }
            }
        };
    }

    private void register() {
        final String email = regisEmail.getText().toString().trim();
        final String username = regisUsername.getText().toString().trim();
        final String handphone = regisNumberHandphone.getText().toString().trim();
//        final String address = "KFC - Kemanggisan Jakarta, fast_food, Jakarta Special Capital Region, Indonesia";
//        final Double latitude = -6.2003319;
//        final Double longitude = 106.7825777;
//        final String bankAccount = "1564987526";
        final Integer wallet = 0;
        final String password = regisPassword.getText().toString().trim();
        String rePassword = regisRePassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this,"Please enter your email",Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Please enter your password",Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(rePassword)) {
            Toast.makeText(this,"Please enter Repassword",Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this,"Please enter Username",Toast.LENGTH_LONG).show();
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Password minimal 6 character",Toast.LENGTH_LONG).show();
        }
        if (!password.equals(rePassword)) {
            Toast.makeText(this,"RePassword and Password must be same",Toast.LENGTH_LONG).show();
        }
        final String role = "customer";

        progressDialog.setMessage("Registering User. . .");
        progressDialog.show();


//        Map<String, Object> user = new HashMap<>();
//        user.put("username",username);
//        user.put("email",email);
//        user.put("password",password);
//        Customer customer = new Customer(
//                username,
//                email,
//                password
//        );
//        db.collection("users")
//                .add(customer)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        progressDialog.dismiss();
//                        Toast.makeText(RegisterPage.this,"Registered successfully",Toast.LENGTH_LONG).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(RegisterPage.this,"Registered failed" + e,Toast.LENGTH_LONG).show();
//                        progressDialog.dismiss();
//                        Toast.makeText(RegisterPage.this,"Registered failed",Toast.LENGTH_LONG).show();
//                    }
//                });
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Customer customer = new Customer(
                                    username,
                                    email,
                                    role,
                                    password,
                                    handphone,
                                    wallet
                            );
//                            Montir montir = new Montir(
//                                    username,
//                                    address,
//                                    email,
//                                    password,
//                                    role,
//                                    bankAccount,
//                                    latitude,
//                                    longitude,
//                                    wallet
//                            );
                            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            FirebaseDatabase.getInstance().getReference("Customers")
                                    .child(userID)
                                    .setValue(customer).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterPage.this,"Registered successfully",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            Intent startActivity = new Intent(getApplicationContext(), HomePage.class);
                            startActivity(startActivity);
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(RegisterPage.this,"User already registered",Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(RegisterPage.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}
