package com.example.skripsicustomer1.order_page;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.skripsicustomer1.Order;
import com.example.skripsicustomer1.R;
import com.example.skripsicustomer1.customer.HomePage;
import com.example.skripsicustomer1.helper.ConvertBase64;
import com.example.skripsicustomer1.helper.FormatNumber;
import com.google.gson.Gson;

public class OrderPage2 extends AppCompatActivity {

    Order order  = new Order();
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
    Button batalOrder;
    Button changeStatusOrder;
    LinearLayout oliMesinLayoutOrder;
    LinearLayout oliGandaLayoutOrder;
    LinearLayout tipeKerusakanLayoutOrder;

    FormatNumber formatNumber = new FormatNumber();
    ConvertBase64 convertBase64 = new ConvertBase64();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_order_page2);
        getIntentValue();

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
        batalOrder = (Button) findViewById(R.id.batalOrder);
        changeStatusOrder = (Button) findViewById(R.id.changeStatusOrderPage);
        oliGandaLayoutOrder = (LinearLayout) findViewById(R.id.layoutOliGandaOrderPage);
        oliMesinLayoutOrder = (LinearLayout) findViewById(R.id.layoutOliMesinOrderPage);
        tipeKerusakanLayoutOrder = (LinearLayout) findViewById(R.id.layoutTypeServiceCheckup);

        setDataOrderPage();



    }
    public void setDataOrderPage () {

        fotoMontir.setImageBitmap(convertBase64.convertBase64toBitmap(order.getMontir().getImage()));
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), OrderPage.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
