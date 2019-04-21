package com.example.skripsicustomer1.customer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.example.skripsicustomer1.Customer;
import com.example.skripsicustomer1.MainActivity;
import com.example.skripsicustomer1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle abdt;
    private FirebaseAuth mAuth;
    private Boolean flag = true;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        navigation();
        loadFragment(new ServiceRutinPage());
        navigationBottom();
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("loading . .");
        progressDialog.show();

    }

    @Override
    public void onBackPressed() {
        if (flag){
            Toast.makeText(getApplicationContext(), "press again to exit", Toast.LENGTH_LONG).show();
            flag = false;
        } else{
            mAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
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
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    Toast.makeText(getApplicationContext(),"You are not customer",Toast.LENGTH_SHORT).show();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void navigationBottom(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadFragment(Fragment fragment) {
        if(fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container,fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }


    public void navigation(){
        DrawerLayout dl;
        dl = (DrawerLayout) findViewById(R.id.draw_layout);
        abdt = new ActionBarDrawerToggle(this,dl,R.string.Open,R.string.Close);

        abdt.setDrawerIndicatorEnabled(true);

        dl.addDrawerListener(abdt);
        abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NavigationView navView = (NavigationView)findViewById(R.id.nav_view);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if(id == R.id.order){
                    Toast.makeText(HomePage.this, "Order", Toast.LENGTH_SHORT).show();
                }else if(id == R.id.logout){
                    mAuth.getInstance().signOut();
                    Toast.makeText(HomePage.this, "Logout", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(intent);
                }

                return true;
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()){
            case R.id.servisRutin:
                fragment = new ServiceRutinPage();
                break;
            case R.id.checkUp:
                fragment = new CheckUpPage();
                break;
        }
        return loadFragment(fragment);
    }
}


