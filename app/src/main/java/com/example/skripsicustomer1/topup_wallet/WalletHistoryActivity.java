package com.example.skripsicustomer1.topup_wallet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.skripsicustomer1.R;
import com.example.skripsicustomer1.WalletConfirmations;
import com.example.skripsicustomer1.adapter.WalletAdapter;
import com.example.skripsicustomer1.customer.HomePage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class WalletHistoryActivity extends AppCompatActivity {

    ArrayList<WalletConfirmations> walletHistoryArrayList = new ArrayList<>();
    WalletAdapter walletAdapter;
    ListView listViewWalletHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_history);
        getSupportActionBar().setTitle("Riwayat isi wallet");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listViewWalletHistory = (ListView) findViewById(R.id.listWalletHistory);

        DatabaseReference dbWalletHistory = FirebaseDatabase.getInstance().getReference("WalletConfirmationHistory")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        dbWalletHistory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                walletHistoryArrayList.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    WalletConfirmations walletHistory = data.getValue(WalletConfirmations.class);

                    walletHistoryArrayList.add(walletHistory);
                }
                walletAdapter = new WalletAdapter(
                        getApplicationContext(),
                        0,
                        walletHistoryArrayList
                );
                Collections.reverse(walletHistoryArrayList);
                listViewWalletHistory.setAdapter(walletAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), HomePage.class);
        startActivityForResult(intent, 0);
        return true;
    }
}
