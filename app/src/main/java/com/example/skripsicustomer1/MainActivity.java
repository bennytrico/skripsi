package com.example.skripsicustomer1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skripsicustomer1.customer.HomePage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    TextView btnRegister;
    private FirebaseAuth mAuth;
    private String email;
    private String password;
    private ProgressDialog progressDialog;


    EditText emailText;
    EditText passwordText;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()!=null) {
            Intent startIntent = new Intent(getApplicationContext(),HomePage.class);
            startActivity(startIntent);
        }
        emailText = (EditText) findViewById(R.id.txtEmailUser);
        passwordText = (EditText) findViewById(R.id.txtPassword);
        progressDialog = new ProgressDialog(this);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (TextView) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(),RegisterPage.class);
                startActivity(startIntent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
    }
    public void userLogin () {
        email = emailText.getText().toString().trim();
        password = passwordText.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.setMessage("Loading . .");
                    progressDialog.show();
                    final DatabaseReference db = FirebaseDatabase.getInstance().getReference("Customers");
                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            boolean flag = false;
                            for (DataSnapshot data: dataSnapshot.getChildren()) {
                                if (data.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                    flag = true;
                                }
                            }
                            if (!flag) {
                                progressDialog.dismiss();
                                mAuth.getInstance().signOut();
                                Toast.makeText(getApplicationContext(),"You are not customer",Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();
                                Intent startIntent = new Intent(getApplicationContext(), HomePage.class);
                                startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(startIntent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
