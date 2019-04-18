package com.example.skripsicustomer1.customer.service_rutin_page;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skripsicustomer1.R;
import com.example.skripsicustomer1.customer.MapsActivity;
import com.example.skripsicustomer1.helper.Location;


public class ServiceRutinPage3 extends AppCompatActivity{
    public static Context context;
    private Intent intent;
    LocationManager locationManager;
    TextView locationText;
    private String valueLocation = "";
    private Double latitudeLocation;
    private Double longtitudeLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_rutin_page3);
        Location location = new Location(this, this);
        location.requestPermission(ServiceRutinPage3.this);
        Button btnGetLocation = (Button) findViewById(R.id.getCurrentLocation);
        TextView textLocation = (TextView) findViewById(R.id.textLocation);
        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               intent = new Intent(getApplicationContext(), MapsActivity.class);
               Bundle extra = new Bundle();
               extra.putString("flagActivity","Service Rutin");
               intent.putExtras(extra);
               startActivity(intent);
            }
        });

        getLocationFromMap();
        textLocation.setText(valueLocation);
    }
    private void getLocationFromMap () {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            valueLocation = extras.getString("EXTRA_ADDRESS");
            longtitudeLocation = extras.getDouble("EXTRA_LONGTITUDE");
            latitudeLocation = extras.getDouble("KEY_LATITUDE");
        }
    }
}
