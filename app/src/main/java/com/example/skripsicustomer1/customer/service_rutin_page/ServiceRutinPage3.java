package com.example.skripsicustomer1.customer.service_rutin_page;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.skripsicustomer1.R;
import com.example.skripsicustomer1.customer.MapsActivity;
import com.example.skripsicustomer1.helper.Location;


public class ServiceRutinPage3 extends AppCompatActivity{
    public static Context context;
    private Intent intent;
    LocationManager locationManager;
    TextView locationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_rutin_page3);

        Location location = new Location(this, this);
        location.requestPermission(ServiceRutinPage3.this);
        locationText = (TextView) findViewById(R.id.location);

        Button btnGetLocation = (Button) findViewById(R.id.getCurrentLocation);
        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               intent = new Intent(getApplicationContext(), MapsActivity.class);
               startActivity(intent);
            }
        });
    }
}
