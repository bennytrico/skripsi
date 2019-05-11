package com.example.skripsicustomer1.order_page;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skripsicustomer1.Order;
import com.example.skripsicustomer1.R;
import com.example.skripsicustomer1.helper.Convertor;
import com.example.skripsicustomer1.helper.FormatNumber;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderPage2 extends AppCompatActivity {

    Order order  = new Order();
    private Boolean customerAgree;
    private Boolean montirAgree;

    ImageView fotoMontir;
    TextView hargaOrder;
    TextView alamatOrder;
    TextView namaMontirOrder;
    TextView transmisiMotorOrder;
    TextView jenisMotorOrder;
    TextView merekMotorOrder;
    TextView tanggalOrder;
    TextView oliMesinOrder;
    TextView oliGandaOrder;
    TextView tipeKerusakanOrder;
    TextView statusOrderPage;
    Button batalOrder;
    Button changeStatusOrder;
    LinearLayout oliMesinLayoutOrder;
    LinearLayout oliGandaLayoutOrder;
    LinearLayout tipeKerusakanLayoutOrder;
    Date dateOrder;
    Date dateNow;

    FormatNumber formatNumber = new FormatNumber();
    Convertor convertor = new Convertor();
    DatabaseReference dbOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_order_page2);
        getIntentValue();

        dbOrder = FirebaseDatabase.getInstance().getReference("Orders");

        fotoMontir = (ImageView) findViewById(R.id.fotoMontir);
        hargaOrder = (TextView) findViewById(R.id.hargaOrderPage);
        alamatOrder = (TextView) findViewById(R.id.alamatOrderPage);
        namaMontirOrder = (TextView) findViewById(R.id.namaMontirOrderPage);
        transmisiMotorOrder = (TextView) findViewById(R.id.transmisiMotorOrderPage);
        jenisMotorOrder = (TextView) findViewById(R.id.jenisMotorOrderPage);
        merekMotorOrder = (TextView) findViewById(R.id.merekMotorOrderPage);
        tanggalOrder = (TextView) findViewById(R.id.tanggalOrderPage);
        oliGandaOrder = (TextView) findViewById(R.id.gantiOliGandaOrderPage);
        oliMesinOrder = (TextView) findViewById(R.id.gantiOliMesinOrderPage);
        tipeKerusakanOrder = (TextView) findViewById(R.id.kerusakanCheckupOrderPage);
        statusOrderPage = (TextView) findViewById(R.id.statusOrderPage);
        batalOrder = (Button) findViewById(R.id.batalOrder);
        changeStatusOrder = (Button) findViewById(R.id.changeStatusOrderPage);
        oliGandaLayoutOrder = (LinearLayout) findViewById(R.id.layoutOliGandaOrderPage);
        oliMesinLayoutOrder = (LinearLayout) findViewById(R.id.layoutOliMesinOrderPage);
        tipeKerusakanLayoutOrder = (LinearLayout) findViewById(R.id.layoutTypeServiceCheckup);

        batalOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbOrder.child(order.getId()).child("status_order").setValue("cancel");
                finish();
            }
        });

        setDataOrderPage();
        if (order.getStatus_order().equals("wait")) {
            statusOrderPage.setText(R.string.waitConfirmFromMontir);
            changeStatusOrder.setVisibility(View.GONE);
        }else if (order.getStatus_order().equals("accept")) {
            statusOrderPage.setText(R.string.confirmFromMontir);
            acceptedOrder();
        }else if (order.getStatus_order().equals("cancel")) {
            statusOrderPage.setText(R.string.canceledOrder);
            changeStatusOrder.setVisibility(View.GONE);
            batalOrder.setVisibility(View.GONE);
        }else if (order.getStatus_order().equals("process")) {
            statusOrderPage.setText(R.string.processService);
            processOrder();
        }else if (order.getStatus_order().equals("done")) {
            statusOrderPage.setText(R.string.serviceDone);
            changeStatusOrder.setVisibility(View.GONE);
            batalOrder.setVisibility(View.GONE);
        }

    }
    public void setDataOrderPage () {

        fotoMontir.setImageBitmap(convertor.convertBase64toBitmap(order.getMontir().getImage()));
        hargaOrder.setText(formatNumber.formatNumber(order.getAmount()));
        alamatOrder.setText(order.getAddress());
        namaMontirOrder.setText(order.getMontir().getName());
        transmisiMotorOrder.setText(order.getTransmition());
        jenisMotorOrder.setText(order.getBrand());
        merekMotorOrder.setText(order.getType_motor());
        tanggalOrder.setText(order.getDate());
        if (order.getType_order().equals("Service Rutin")) {
            if (order.getOli_mesin())
                oliMesinOrder.setText("Ganti");
            else
                oliMesinOrder.setText("Tidak");
            if (order.getOli_ganda())
                oliGandaOrder.setText("Ganti");
            else
                oliGandaOrder.setText("Tidak");
            tipeKerusakanLayoutOrder.setVisibility(View.GONE);
        } else if (order.getType_order().equals("Check Up")) {
            tipeKerusakanOrder.setText(order.getType_checkup());
            oliMesinLayoutOrder.setVisibility(View.GONE);
            oliGandaLayoutOrder.setVisibility(View.GONE);
        }

    }
    public void getIntentValue () {
        Gson gson = new Gson();
        Intent intent = getIntent();
        order = gson.fromJson(intent.getExtras().getString("ORDER_SELECTED"),Order.class);
    }
    public void acceptedOrder () {
        changeStatusOrder.setText(R.string.statusOnProgress);
        dateNow = new Date();
        dateOrder = new Date();
        String dateOrderString = order.getDate() + " " + order.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
        try {
            dateOrder = dateFormat.parse(dateOrderString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        getValueAgreement();

        if (dateNow.compareTo(dateOrder) < 0) {
            changeStatusOrder.setText("Tombol akan aktif ketika " + dateOrderString);
            changeStatusOrder.setTextColor(getResources().getColor(R.color.black));
            changeStatusOrder.setEnabled(false);
            changeStatusOrder.setBackgroundColor(getResources().getColor(R.color.grey));
        }
        changeStatusOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbOrder.child(order.getId()).child("flag_customer_agree").setValue(true);
                finish();
            }
        });
    }
    public void processOrder () {
        changeStatusOrder.setText(R.string.statusDone);
        getValueAgreement();

        changeStatusOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbOrder.child(order.getId()).child("flag_customer_agree").setValue(true);
                finish();
            }
        });
    }
    public void getValueAgreement() {
        dbOrder.child(order.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Order r = dataSnapshot.getValue(Order.class);
                customerAgree = r.getFlag_customer_agree();
                montirAgree = r.getFlag_montir_agree();
                checkValueAgreement();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void checkValueAgreement () {
        if (order.getStatus_order().equals("accept") && customerAgree && montirAgree) {
            dbOrder.child(order.getId()).child("status_order").setValue("process");
            dbOrder.child(order.getId()).child("flag_customer_agree").setValue(false);
            dbOrder.child(order.getId()).child("flag_montir_agree").setValue(false);
        } else if (order.getStatus_order().equals("process") && customerAgree && montirAgree) {
            dbOrder.child(order.getId()).child("status_order").setValue("done");
            dbOrder.child(order.getId()).child("flag_customer_agree").setValue(false);
            dbOrder.child(order.getId()).child("flag_montir_agree").setValue(false);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), OrderPage.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
