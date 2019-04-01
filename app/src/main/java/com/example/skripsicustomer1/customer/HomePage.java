package com.example.skripsicustomer1.customer;

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

import com.example.skripsicustomer1.R;

public class HomePage extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        navigation();
        loadFragment(new ServiceRutinPage());
        navigationBottom();


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

                if(id == R.id.notification){
                    Toast.makeText(HomePage.this, "Notification", Toast.LENGTH_SHORT).show();
                }else if(id == R.id.order){
                    Toast.makeText(HomePage.this, "Order", Toast.LENGTH_SHORT).show();
                }else if(id == R.id.logout){
                    Toast.makeText(HomePage.this, "Logout", Toast.LENGTH_SHORT).show();
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


