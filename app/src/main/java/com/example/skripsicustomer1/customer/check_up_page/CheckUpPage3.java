package com.example.skripsicustomer1.customer.check_up_page;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.skripsicustomer1.Montir;
import com.example.skripsicustomer1.R;
import com.example.skripsicustomer1.customer.MapsActivity;
import com.example.skripsicustomer1.customer.service_rutin_page.ServiceRutinPage3;
import com.example.skripsicustomer1.helper.Location;

public class CheckUpPage3 extends AppCompatActivity {

    private String valueLocation;
    private Double longtitudeLocation;
    private Double latitudeLocation;
//    private Montir montir;
//    private String transmisi;
//    private String jenis;
//    private String tipe;
//    private Integer harga;
//    private String mTime1;
//    private String tanggal;
//    private Boolean oliMesin;
//    private Boolean oliGanda;
//    private Boolean statusUserAgree = false;
//    private Boolean statusMontirAgree = false;
//    private String statusOrder = "pending";
//    private String namaCustomer;
//    private String noHpCustomer;
//    private String platNomor;

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
//    public void getIntentValue () {
//
//        Intent intent = getIntent();
//        Bundle extras = intent.getExtras();
//        if (extras != null) {
//
//            transmisi = extras.getString("EXTRA_TRANSMISI");
//            jenis = extras.getString("EXTRA_JENIS");
//            tipe = extras.getString("EXTRA_TIPE");
//            harga = extras.getInt("EXTRA_HARGA");
//            tanggal = extras.getString("EXTRA_TANGGAL");
//            mTime1 = extras.getString("EXTRA_HOUR");
//            oliMesin = extras.getBoolean("EXTRA_GANTI_OLI");
//            oliGanda = extras.getBoolean("EXTRA_GANTI_GANDA");
//            platNomor = extras.getString("EXTRA_PLATNOMOR");
//        }
//    }

}
