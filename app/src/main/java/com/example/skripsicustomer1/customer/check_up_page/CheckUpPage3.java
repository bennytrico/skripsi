package com.example.skripsicustomer1.customer.check_up_page;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skripsicustomer1.CheckUpList;
import com.example.skripsicustomer1.Customer;
import com.example.skripsicustomer1.Montir;
import com.example.skripsicustomer1.Order;
import com.example.skripsicustomer1.PushNotif;
import com.example.skripsicustomer1.R;
import com.example.skripsicustomer1.adapter.MontirAdapter;
import com.example.skripsicustomer1.customer.HomePage;
import com.example.skripsicustomer1.customer.MapsActivity;
import com.example.skripsicustomer1.customer.service_rutin_page.ServiceRutinPage3;
import com.example.skripsicustomer1.helper.Location;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CheckUpPage3 extends AppCompatActivity {


    private String valueLocation;
    private Double longtitudeLocation1;
    private Double latitudeLocation1;
    private Montir montir;
    private String transmisi;
    private String jenis;
    private String tipe;
    private Integer harga;
    private String mTime1;
    private String tanggal;
    private String  typeCheckUp;
    private Boolean statusUserAgree = false;
    private Boolean statusMontirAgree = false;
    private String statusOrder = "wait";
    private String namaCustomer;
    private String noHpCustomer;
    private String platNomor;
    private String checkUpList = "";

    ListView listViewMontir ;
    Button order;
    Customer customer = new Customer();
    TextView namaMontirCheckUp;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_up_page3);
        Location location = new Location(this, this);
        location.requestPermission(CheckUpPage3.this);
        if (transmisi == null) {
            getIntentValue();
        }
        progressDialog = new ProgressDialog(CheckUpPage3.this);

        Button btnOpenMap = (Button) findViewById(R.id.getCurrentLocationCheckUp);
        TextView txtLocation = (TextView) findViewById(R.id.textLocationCheckUp);
        order = (Button) findViewById(R.id.orderCheckUp);


        DatabaseReference dbCustomer = FirebaseDatabase.getInstance().getReference("Customers");

        dbCustomer.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                customer = dataSnapshot.getValue(Customer.class);
                namaCustomer = customer.getUsername();
                noHpCustomer = customer.getNumber_handphone();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(valueLocation))
                    Toast.makeText(CheckUpPage3.this, "harus menentukan alamat",Toast.LENGTH_SHORT).show();
                else
                    getListMontir();
            }
        });

        btnOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                Bundle extra = new Bundle();
                extra.putString("flagActivity","Check Up");
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
        });
        getLocationFromMap();
        txtLocation.setText(valueLocation);
    }
    private void getLocationFromMap () {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            valueLocation = extras.getString("EXTRA_ADDRESS");
            longtitudeLocation1 = extras.getDouble("EXTRA_LONGTITUDE");
            latitudeLocation1 = extras.getDouble("EXTRA_LATITUDE");
        }
    }
    public void getIntentValue () {

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            transmisi = extras.getString("EXTRA_TRANSMISI");
            jenis = extras.getString("EXTRA_JENIS");
            tipe = extras.getString("EXTRA_TIPE");
            harga = extras.getInt("EXTRA_HARGA");
            tanggal = extras.getString("EXTRA_TANGGAL");
            mTime1 = extras.getString("EXTRA_HOUR");
            platNomor = extras.getString("EXTRA_PLATNOMOR");
            typeCheckUp = extras.getString("EXTRA_TYPE_KERUSAKAN");
        }
    }

    public void getListMontir () {
        final ArrayList<Montir> arrayList = new ArrayList<>();
        DatabaseReference dbMontir = FirebaseDatabase.getInstance().getReference("Montirs");
        final DatabaseReference dbOrders = FirebaseDatabase.getInstance().getReference("Orders");

        final ArrayList<String> idMontir = new ArrayList<>();
        progressDialog.setMessage("Mencari motor");

        dbOrders.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.show();
                Date dateBooking = new Date();
                Calendar calendar = Calendar.getInstance();
                Date dateMaxHours = new Date();
                Date dateMinHours = new Date();

                String dateAndTimeBooking = tanggal + " " + mTime1;

                SimpleDateFormat a = new SimpleDateFormat("dd MMM yyyy HH:mm");

                try {
                    dateBooking = a.parse(dateAndTimeBooking);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                calendar.setTime(dateBooking);
                calendar.add(Calendar.HOUR,2);
                dateMaxHours = calendar.getTime();
                calendar.setTime(dateBooking);
                calendar.add(Calendar.HOUR,-2);
                dateMinHours = calendar.getTime();


                for (DataSnapshot data:dataSnapshot.getChildren()) {
                    Order order = data.getValue(Order.class);
                    Date date = new Date();
                    String dateAndTime = order.getDate()+" "+order.getTime();
                    try {
                        date = a.parse(dateAndTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if(dateMinHours.compareTo(date) < 0
                            && dateMaxHours.compareTo(date) > 0 ) {
                        idMontir.add(order.getMontir().getId());
                        if (order.getStatus_order().equals("done")) {
                            idMontir.remove(order.getMontir().getId());
                        } else if (order.getStatus_order().equals("cancel")) {
                            idMontir.remove(order.getMontir().getId());
                        } else if (order.getStatus_order().equals("end")) {
                            idMontir.remove(order.getMontir().getId());
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dbMontir.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                android.location.Location customerLocation = new android.location.Location("a");
                LatLng latLng = new LatLng(latitudeLocation1,longtitudeLocation1);
                customerLocation.setLatitude(latLng.latitude);
                customerLocation.setLongitude(latLng.longitude);

                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    Montir m = data.getValue(Montir.class);
                    m.setId(data.getKey());

                    android.location.Location montirLocation = new android.location.Location("b");
                    LatLng latLngMontir = new LatLng(m.getLatitude(),m.getLongitude());
                    montirLocation.setLatitude(latLngMontir.latitude);
                    montirLocation.setLongitude(latLngMontir.longitude);

                    int distance = (int) customerLocation.distanceTo(montirLocation);
                    if (distance < 1000  && !idMontir.contains(m.getId())) {
                        arrayList.add(m);
                    }

                }

                montir = arrayList.get(0);

                createOrder();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void createOrder() {
        DatabaseReference  dbOrders = FirebaseDatabase.getInstance().getReference("Orders");
        final DatabaseReference dbCustomers = FirebaseDatabase.getInstance().getReference("Customers");

        dbCustomers.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Customer c = dataSnapshot.getValue(Customer.class);
                Map<String, Object> update = new HashMap<String, Object>();
                Integer calculatedWallet = c.getWallet() - harga;
                update.put("wallet",calculatedWallet);
                DatabaseReference dbCustomersUpdate = FirebaseDatabase.getInstance().getReference("Customers");
                dbCustomersUpdate.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(update);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        String customer = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String typeOrder = "Check Up";
        if (typeCheckUp.equals("Electrical")) {
            checkUpList += "Electrical , ";
        } else if (typeCheckUp.equals("Breaking system")) {
            checkUpList += "Breaking system , ";
        } else if (typeCheckUp.equals("Engine")) {
            checkUpList += "Engine , ";
        } else if (typeCheckUp.equals("Mechanical")) {
            checkUpList += "Mechanical , ";
        } else if (typeCheckUp.equals("All")) {
            checkUpList = "All";
        }
        Boolean flagRating = false;
        Order order = new Order();
        order.OrderCheckup(
                customer,
                valueLocation,
                typeOrder,
                transmisi,
                jenis,
                tipe,
                tanggal,
                mTime1,
                statusOrder,
                statusUserAgree,
                statusMontirAgree,
                harga,
                montir,
                checkUpList,
                namaCustomer,
                noHpCustomer,
                platNomor,
                flagRating,
                latitudeLocation1,
                longtitudeLocation1
        );

        dbOrders.push().setValue(order);
        DatabaseReference dbMontir = FirebaseDatabase.getInstance().getReference("Montirs");
        dbMontir.child(montir.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Montir m = dataSnapshot.getValue(Montir.class);
                PushNotif pushNotif = new PushNotif();
                if (m.getFcm_token() != null) {
                    try {
                        pushNotif.pushNotiftoMontir(getApplicationContext(),m.getFcm_token());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        progressDialog.dismiss();

        Toast.makeText(CheckUpPage3.this, "Pesanan berhasil, silahkan lihat di menu Pesanan",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(CheckUpPage3.this, HomePage.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }
}
