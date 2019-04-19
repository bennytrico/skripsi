package com.example.skripsicustomer1.customer.check_up_page;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.skripsicustomer1.R;
import com.example.skripsicustomer1.customer.MapsActivity;
import com.example.skripsicustomer1.customer.service_rutin_page.ServiceRutinPage3;
import com.example.skripsicustomer1.helper.Location;

public class CheckUpPage3 extends AppCompatActivity {

    private String valueLocation;
    private Double longtitudeLocation;
    private Double latitudeLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_up_page3);
        Location location = new Location(this, this);
        location.requestPermission(CheckUpPage3.this);

        getLocationFromMap();

        Button btnOpenMap = (Button) findViewById(R.id.getCurrentLocationCheckUp);
        TextView txtLocation = (TextView) findViewById(R.id.textLocationCheckUp);

        btnOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                Bundle extra = new Bundle();
                extra.putString("flagActivity","Check Up");
                intent.putExtras(extra);
                startActivity(intent);
            }
        });
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
