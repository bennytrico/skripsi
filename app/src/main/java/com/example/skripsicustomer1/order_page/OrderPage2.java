package com.example.skripsicustomer1.order_page;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.skripsicustomer1.Order;
import com.example.skripsicustomer1.R;
import com.google.gson.Gson;

public class OrderPage2 extends AppCompatActivity {

    Order order  = new Order();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page2);
        getIntentValue();

        Log.e("test",order.getName_customer());
    }
    public void getIntentValue () {
        Gson gson = new Gson();
        Intent intent = getIntent();
        order = gson.fromJson(intent.getExtras().getString("ORDER_SELECTED"),Order.class);
    }
}
