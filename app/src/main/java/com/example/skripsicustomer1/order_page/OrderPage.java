package com.example.skripsicustomer1.order_page;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.skripsicustomer1.Order;
import com.example.skripsicustomer1.R;
import com.example.skripsicustomer1.adapter.OrderAdapter;
import com.example.skripsicustomer1.customer.HomePage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderPage extends AppCompatActivity {
    ArrayList<Order> orderArrayList = new ArrayList<>();
    ListView listViewOrder;
    OrderAdapter listViewOrderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);
        getSupportActionBar().setTitle("Pesanan");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listViewOrder = (ListView)findViewById(R.id.listOrder);
        DatabaseReference dbOrder = FirebaseDatabase.getInstance().getReference("Orders");
        dbOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderArrayList.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    Order r = data.getValue(Order.class);
                    r.setId(data.getKey());

                    if (r.getCustomer_id().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        orderArrayList.add(r);
                    }
                }
                listViewOrderAdapter = new OrderAdapter(
                        getApplicationContext(),
                        0,
                        orderArrayList
                );
                listViewOrder.setAdapter(listViewOrderAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listViewOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Gson gson = new Gson();
                Order orderSelected = listViewOrderAdapter.getItem(position);
                String orderJson = gson.toJson(orderSelected);
                Intent intent = new Intent(getApplicationContext(),OrderPage2.class);
                intent.putExtra("ORDER_SELECTED",orderJson);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean ongOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), HomePage.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
