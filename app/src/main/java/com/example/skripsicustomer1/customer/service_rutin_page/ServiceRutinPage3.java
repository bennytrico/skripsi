package com.example.skripsicustomer1.customer.service_rutin_page;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skripsicustomer1.Customer;
import com.example.skripsicustomer1.Montir;
import com.example.skripsicustomer1.R;
import com.example.skripsicustomer1.adapter.MontirAdapter;
import com.example.skripsicustomer1.customer.HomePage;
import com.example.skripsicustomer1.customer.MapsActivity;
import com.example.skripsicustomer1.helper.Location;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;


public class ServiceRutinPage3 extends AppCompatActivity{
    public static Context context;
    private Intent intent;
    LocationManager locationManager;
    TextView locationText;
    private String valueLocation = "";
    private Double latitudeLocation;
    private Double longtitudeLocation;
    private MontirAdapter mAdapter;
    private Location locationCustomer;
    private Location locationMontir;
    ListView listViewMontir ;
    Button order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_rutin_page3);
        Location location = new Location(this, this);
        location.requestPermission(ServiceRutinPage3.this);
        Button btnGetLocation = (Button) findViewById(R.id.getCurrentLocation);
        TextView textLocation = (TextView) findViewById(R.id.textLocation);
        order = (Button) findViewById(R.id.orderServiceRutin);
        final TextView namaMontirSelected = (TextView) findViewById(R.id.namaMontir);

        listViewMontir = (ListView) findViewById(R.id.listViewMontir);

        final ArrayList<Montir> arrayList = new ArrayList<>();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Montirs");

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("Count " ,""+dataSnapshot.getChildrenCount());
                for (DataSnapshot data:dataSnapshot.getChildren()) {
                    Montir c = data.getValue(Montir.class);
                    c.setId(data.getKey());

                    arrayList.add(c);
                }

                Log.e("Customer " ,""+arrayList.size());
                final MontirAdapter listViewMontirAdapater = new MontirAdapter(
                        getApplicationContext(),
                        0,
                        arrayList
                );


                listViewMontir.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Montir c = listViewMontirAdapater.getItem(position);
                        Log.e("SELECTED: ",c.getId());
                    }
                });

                listViewMontir.setAdapter(listViewMontirAdapater);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServiceRutinPage3.this, HomePage.class));
            }
        });

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
