package com.example.skripsicustomer1.helper;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.skripsicustomer1.customer.service_rutin_page.ServiceRutinPage3;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public final class Location {
    public FusedLocationProviderClient client;
    private String message;
    private Context contexts;
    private Activity activity;

    public Location(Context context,Activity activity){
        this.contexts = context;
        this.activity = activity;
    }
    public void requestPermission(Activity activity){
        if (ContextCompat.checkSelfPermission(contexts, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(contexts, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }
    }
}
