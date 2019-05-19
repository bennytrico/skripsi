package com.example.skripsicustomer1.topup_wallet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.skripsicustomer1.R;
import com.example.skripsicustomer1.helper.FormatNumber;

import static com.example.skripsicustomer1.CurrentUser.currentUserWallet;

public class TopUpWalletPage2 extends AppCompatActivity {

    TextView invoiceWallet;
    FormatNumber format = new FormatNumber();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up_wallet_page2);
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        Integer amount = extra.getInt("EXTRA_AMOUNT");
        invoiceWallet = (TextView) findViewById(R.id.invoiceWalletTopUp);
        invoiceWallet.setText(format.formatNumber(amount + 28));
    }
}
