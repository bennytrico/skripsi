package com.example.skripsicustomer1.customer.service_rutin_page;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skripsicustomer1.R;
import com.example.skripsicustomer1.customer.MapsActivity;
import com.example.skripsicustomer1.helper.Location;

import java.util.List;
import java.util.Locale;

public class ServiceRutinPage3 extends AppCompatActivity implements LocationListener {
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


    @Override
    public void onLocationChanged(android.location.Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(ServiceRutinPage3.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }
}
