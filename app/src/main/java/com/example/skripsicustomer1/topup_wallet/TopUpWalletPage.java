package com.example.skripsicustomer1.topup_wallet;

import android.app.Dialog;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skripsicustomer1.R;
import com.example.skripsicustomer1.customer.service_rutin_page.ServiceRutinPage2;
import com.example.skripsicustomer1.helper.FormatNumber;

import static com.example.skripsicustomer1.CurrentUser.currentUserWallet;

public class TopUpWalletPage extends AppCompatActivity {

    TextView currentWallet;
    EditText bankAccountNameText;
    EditText bankAccountNumberText;
    EditText amountTopUpText;
    Button nextStepTopUp;

    private String bankAccountName;
    private String bankAccountNumber;
    private Integer amountTopUp;

    FormatNumber formatNumber = new FormatNumber();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up_wallet_page);
        getSupportActionBar().setTitle("Isi Ulang Dompet");

        currentWallet = (TextView) findViewById(R.id.currentWallet);
        bankAccountNameText = (EditText) findViewById(R.id.bankAccountNameTopUp);
        bankAccountNumberText = (EditText) findViewById(R.id.bankAccountNumberTopUp);
        amountTopUpText = (EditText) findViewById(R.id.amountTopUp);
        nextStepTopUp = (Button) findViewById(R.id.submitTopUp);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        currentWallet.setText(formatNumber.formatNumber(currentUserWallet));
        nextStepTopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateTopUpWallet();
            }
        });

    }
    public void validateTopUpWallet () {
        boolean flag = false;
        bankAccountName = bankAccountNameText.getText().toString().trim();
        bankAccountNumber = bankAccountNumberText.getText().toString().trim();
        try {
            amountTopUp = Integer.parseInt(amountTopUpText.getText().toString());
        } catch (NumberFormatException e) {
            e.fillInStackTrace();
        }

        if (TextUtils.isEmpty(bankAccountName)) {
            Toast.makeText(this,"Nama akun bank harus di isi",Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(bankAccountNumber)) {
            Toast.makeText(this,"Nomor bank harus di isi",Toast.LENGTH_SHORT).show();
        } else if (amountTopUp == null) {
            Toast.makeText(this,"Jumlah uang harus di isi",Toast.LENGTH_SHORT).show();
        } else {
            flag = true;
        }
        if (flag) {
            showDialogConfirmation();
        }
    }
    public void showDialogConfirmation () {
        final Dialog dialog = new Dialog(TopUpWalletPage.this);
        dialog.setContentView(R.layout.dialog_confirmation);
        dialog.setTitle("Info");

        Button agreeTopUp = (Button) dialog.findViewById(R.id.agreeTopUp);
        Button disAgreeTopUp = (Button) dialog.findViewById(R.id.disagreeTopUp);

        agreeTopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        disAgreeTopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
