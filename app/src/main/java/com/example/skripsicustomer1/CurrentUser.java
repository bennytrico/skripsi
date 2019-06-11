package com.example.skripsicustomer1;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CurrentUser {


    static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static String currentUserID = mAuth.getCurrentUser().getUid();
    public static String currentEmailUser;
    public static String currentNumberPhone;
    public static Integer currentUserWallet;
    public static String currentUserName;

    public static void getCurrentCustomerData() {
        DatabaseReference dbCustomer = FirebaseDatabase.getInstance().getReference("Customers");
        dbCustomer.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Customer customer = dataSnapshot.getValue(Customer.class);
                currentEmailUser = customer.getEmail();
                currentUserName = customer.getUsername();
                currentNumberPhone = customer.getNumber_handphone();
                currentUserWallet = customer.getWallet();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
