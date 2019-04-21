package com.example.skripsicustomer1.customer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skripsicustomer1.R;
import com.example.skripsicustomer1.customer.check_up_page.CheckUpPage3;
import com.example.skripsicustomer1.customer.service_rutin_page.ServiceRutinPage3;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText searchText;
    LocationManager locationManager;
    private float DEFAULT_ZOOM = 15.5f;
    private ImageView mobileGPS;
    private String flagActivity;

    private String transmisi;
    private String jenis;
    private String tipe;
    private Integer harga;
    private String mTime1;
    private String tanggal;
    private Boolean oliMesin;
    private Boolean oliGanda;
    private String platNomor;
    private String typeCheckUp;

    boolean hasLocation = false;

    boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.




        searchText = (EditText) findViewById(R.id.inputSearch);
        mobileGPS = (ImageView) findViewById(R.id.ic_gps);
        init();
        initMap();

        Intent getValue = getIntent();
        Bundle extra = getValue.getExtras();
        flagActivity = extra.getString("flagActivity");
            if (flagActivity.equals("Service Rutin")) {
                Intent intent = getIntent();
                Bundle extras = intent.getExtras();
                transmisi = extras.getString("EXTRA_TRANSMISI");
                jenis = extras.getString("EXTRA_JENIS");
                tipe = extras.getString("EXTRA_TIPE");
                harga = extras.getInt("EXTRA_HARGA");
                tanggal = extras.getString("EXTRA_TANGGAL");
                mTime1 = extras.getString("EXTRA_HOUR");
                oliMesin = extras.getBoolean("EXTRA_GANTI_OLI");
                oliGanda = extras.getBoolean("EXTRA_GANTI_GANDA");
                platNomor =  extras.getString("EXTRA_PLATNOMOR");

            } else if (flagActivity.equals("Check Up")) {
                Intent intent = getIntent();
                Bundle extras = intent.getExtras();
                transmisi = extras.getString("EXTRA_TRANSMISI");
                jenis = extras.getString("EXTRA_JENIS");
                tipe = extras.getString("EXTRA_TIPE");
                harga = extras.getInt("EXTRA_HARGA");
                tanggal = extras.getString("EXTRA_TANGGAL");
                mTime1 = extras.getString("EXTRA_HOUR");
                platNomor =  extras.getString("EXTRA_PLATNOMOR");
                typeCheckUp = extras.getString("EXTRA_TYPE_KERUSAKAN");
            }
    }

    private void init() {
        searchText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    geoLocation();
                }
                return false;
            }
        });
        mobileGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceLocation();
            }
        });
        hideSoftKeyBoard();
    }

    private void geoLocation() {
        String searchString = searchText.getText().toString();

        Geocoder geoCoderSearch = new Geocoder(MapsActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geoCoderSearch.getFromLocationName(searchString, 1);

        } catch (IOException e) {
            e.getMessage();
        }
        if (list.size() > 0) {
            Address address = list.get(0);
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));
        }
    }

    private void moveCamera(final LatLng latLng, float zoom, String title) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        mMap.clear();
        if (!title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions().position(latLng).title(title);
            mMap.addMarker(options);
        }
        Button getLocation = (Button)findViewById(R.id.setMapLocation);
        final String address = title;

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagActivity.equals("Service Rutin")) {
                    Intent intent = new Intent(getApplicationContext(), ServiceRutinPage3.class);
                    Bundle extra = new Bundle();
                    Log.e("longtitude dari map: ", String.valueOf(latLng.latitude));
                    extra.putString("EXTRA_ADDRESS",address);
                    extra.putDouble("EXTRA_LONGTITUDE",latLng.longitude);
                    extra.putDouble("EXTRA_LATITUDE",latLng.latitude);
                    extra.putString("EXTRA_TRANSMISI",transmisi);
                    extra.putString("EXTRA_JENIS",jenis);
                    extra.putString("EXTRA_TIPE",tipe);
                    extra.putInt("EXTRA_HARGA",harga);
                    extra.putString("EXTRA_TANGGAL",tanggal);
                    extra.putString("EXTRA_HOUR",mTime1);
                    extra.putBoolean("EXTRA_GANTI_OLI",oliMesin);
                    extra.putBoolean("EXTRA_GANTI_GANDA", oliGanda);
                    extra.putString("EXTRA_PLATNOMOR",platNomor);

                    intent.putExtras(extra);
                    startActivity(intent);
                } else if (flagActivity.equals("Check Up")) {
                    Intent intent = new Intent(getApplicationContext(), CheckUpPage3.class);
                    Bundle extra = new Bundle();
                    extra.putString("EXTRA_ADDRESS",address);
                    extra.putDouble("EXTRA_LONGTITUDE",latLng.longitude);
                    extra.putDouble("EXTRA_LATITUDE",latLng.latitude);
                    extra.putString("EXTRA_TRANSMISI",transmisi);
                    extra.putString("EXTRA_JENIS",jenis);
                    extra.putString("EXTRA_TIPE",tipe);
                    extra.putInt("EXTRA_HARGA",harga);
                    extra.putString("EXTRA_TANGGAL",tanggal);
                    extra.putString("EXTRA_HOUR",mTime1);
                    extra.putString("EXTRA_PLATNOMOR",platNomor);
                    extra.putString("EXTRA_TYPE_KERUSAKAN",typeCheckUp);

                    intent.putExtras(extra);
                    startActivity(intent);
                }
            }
        });
        hideSoftKeyBoard();
    }

    private void getDeviceLocation() {

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LatLng latLng = new LatLng(latitude, longitude);
                    List<Address> addressList = new ArrayList<>();
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        addressList = geocoder.getFromLocation(latitude, longitude, 1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addressList.size() > 0) {
                    Address address = addressList.get(0);

                    moveCamera(latLng, DEFAULT_ZOOM, address.getAddressLine(0));

                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LatLng latLng = new LatLng(latitude, longitude);
                    List<Address> addressList = new ArrayList<>();
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        addressList = geocoder.getFromLocation(latitude, longitude, 1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addressList.size() > 0) {
                        Address address = addressList.get(0);

                        moveCamera(latLng, DEFAULT_ZOOM, address.getAddressLine(0));

                    }
                }
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,13.2f));
    }


    public void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }
    private void hideSoftKeyBoard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
