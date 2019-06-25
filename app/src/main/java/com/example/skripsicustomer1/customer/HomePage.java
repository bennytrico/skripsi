package com.example.skripsicustomer1.customer;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.example.skripsicustomer1.Customer;
import com.example.skripsicustomer1.FirebaseIDService;
import com.example.skripsicustomer1.MainActivity;
import com.example.skripsicustomer1.Order;
import com.example.skripsicustomer1.ProfilePage;
import com.example.skripsicustomer1.R;
import com.example.skripsicustomer1.helper.FormatNumber;
import com.example.skripsicustomer1.order_page.OrderPage;
import com.example.skripsicustomer1.rating_page.RatingPage;
import com.example.skripsicustomer1.topup_wallet.TopUpWalletPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import static com.example.skripsicustomer1.CurrentUser.currentUserID;
import static com.example.skripsicustomer1.CurrentUser.getCurrentCustomerData;

public class HomePage extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle abdt;
    private FirebaseAuth mAuth;
    private Boolean flag = true;
    FormatNumber formatNumber = new FormatNumber();


    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        navigation();
        loadFragment(new ServiceRutinPage());
        getCurrentCustomerData();
        navigationBottom();
        FirebaseIDService service = new FirebaseIDService();
        service.onTokenRefresh();

        new android.os.Handler().postDelayed(
            new Runnable() {
                public void run() {
                    getValidationFlagRatingSystem();

                }
            },
            3000);
        DatabaseReference dbCustomer = FirebaseDatabase.getInstance().getReference("Customers");
        dbCustomer.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Customer customer = dataSnapshot.getValue(Customer.class);
                getSupportActionBar().setSubtitle(formatNumber.formatNumber(customer.getWallet()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (flag){
            Toast.makeText(getApplicationContext(), "press again to exit", Toast.LENGTH_SHORT).show();
            flag = false;
            final Handler handler = new Handler();

            final Runnable r = new Runnable() {
                public void run() {
                    handler.postDelayed(this, 2000);
                    flag = true;
                }
            };

            handler.postDelayed(r, 1000);
        } else{
            mAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            super.onBackPressed();
        }
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
                    startActivity(new Intent(getApplicationContext(), OrderPage.class));
                }else if(id == R.id.logout){
                    mAuth.getInstance().signOut();
                    Toast.makeText(HomePage.this, "Keluar", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(intent);
                } else if (id == R.id.topUpwallet) {
                    Intent intent = new Intent(getApplicationContext(), TopUpWalletPage.class);
                    getCurrentCustomerData();
                    startActivity(intent);
                } else if (id == R.id.profile) {
                    Intent intent = new Intent(getApplicationContext(), ProfilePage.class);
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
    public void getValidationFlagRatingSystem () {
        DatabaseReference dbOrder = FirebaseDatabase.getInstance().getReference("Orders");
        dbOrder.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    Order order = data.getValue(Order.class);
                    if (order.getCustomer_id().equals(currentUserID)) {
                        if (order.getFlag_rating()) {
                            Gson gson = new Gson();
                            order.setId(data.getKey());

                            Order orderSelected = order;

                            String orderJson = gson.toJson(orderSelected);
                            Intent intent = new Intent(getApplicationContext(), RatingPage.class);
                            intent.putExtra("ORDER_DONE",orderJson);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}