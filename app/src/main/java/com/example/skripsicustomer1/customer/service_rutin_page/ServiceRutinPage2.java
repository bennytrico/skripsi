package com.example.skripsicustomer1.customer.service_rutin_page;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.skripsicustomer1.Customer;
import com.example.skripsicustomer1.R;
import com.example.skripsicustomer1.helper.FormatNumber;
import com.example.skripsicustomer1.helper.TimePickerFragment;
import com.example.skripsicustomer1.topup_wallet.TopUpWalletPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ServiceRutinPage2 extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {


    private int harga;
    private String transmisi;
    private String jenis;
    private String tipe;
    private String platNomor;
    private String mTime1;
    private String time;
    private Boolean oliMesin = false;
    private Boolean oliGanda = false;

    FormatNumber formatNumber = new FormatNumber();

    ImageButton btnInfoOliGanda;
    ImageButton btnInfoOliMesin;
    CheckBox checkButtonGantiOliMesin;
    CheckBox checkButtonGantiOliGanda;
    Spinner tanggalSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_rutin_page2);
        getCurrentDate();

        getIntentValue();


        final TextView textHarga = (TextView)findViewById(R.id.hargaServiceRutin);
        Button btnHours = (Button) findViewById(R.id.getHours);
        btnInfoOliMesin = (ImageButton) findViewById(R.id.infoPergantianOli);
        btnInfoOliGanda = (ImageButton) findViewById(R.id.infoPergantianOliGanda);
        checkButtonGantiOliMesin = (CheckBox) findViewById(R.id.checkboxOliMesin);
        checkButtonGantiOliGanda = (CheckBox) findViewById(R.id.checkboxOliGanda);
        Button btnServicePage3 = (Button) findViewById(R.id.btnNextServiceRutin2);

        btnServicePage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dbCustomer = FirebaseDatabase.getInstance().getReference("Customers");
                dbCustomer.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Customer c = dataSnapshot.getValue(Customer.class);
                        if (c.getWallet() >= harga) {
                            if (TextUtils.isEmpty(mTime1)) {
                                Toast.makeText(ServiceRutinPage2.this,"Harus mengisi jam servis", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(getApplicationContext(),ServiceRutinPage3.class);
                                Bundle extras = new Bundle();
                                extras.putString("EXTRA_TRANSMISI",transmisi);
                                extras.putString("EXTRA_JENIS",jenis);
                                extras.putString("EXTRA_TIPE",tipe);
                                extras.putInt("EXTRA_HARGA",harga);
                                extras.putString("EXTRA_TANGGAL",tanggalSpinner.getSelectedItem().toString());
                                extras.putString("EXTRA_HOUR",mTime1);
                                extras.putBoolean("EXTRA_GANTI_OLI",oliMesin);
                                extras.putString("EXTRA_PLATNOMOR",platNomor);
                                extras.putString("EXTRA_CLASS","ServiceRutin2");
                                if (oliGanda) {
                                    extras.putBoolean("EXTRA_GANTI_GANDA", oliGanda);
                                }
                                intent.putExtras(extras);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                startActivity(intent);
                            }
                        } else {
                            showDialogOpenTopWallet();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        textHarga.setText(formatNumber.formatNumber(harga));
        btnHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });

        btnInfoOliMesin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogOliMesin();
            }
        });
        btnInfoOliGanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogOliGanda();
            }
        });
        checkButtonGantiOliMesin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()){
                    harga+=45000;
                    oliMesin = true;
                    textHarga.setText(formatNumber.formatNumber(harga));
                }else if(!((CheckBox)v).isChecked()){
                    harga-=45000;
                    oliMesin = false;
                    textHarga.setText(formatNumber.formatNumber(harga));
                }
            }
        });
        checkButtonGantiOliGanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()){
                    harga+=25000;
                    oliGanda = true;
                    textHarga.setText(formatNumber.formatNumber(harga));
                }else if(!((CheckBox)v).isChecked()){
                    harga-=25000;
                    oliGanda = false;
                    textHarga.setText(formatNumber.formatNumber(harga));
                }
            }
        });
    }
    private void showDialogOpenTopWallet() {
        final Dialog dialog = new Dialog(ServiceRutinPage2.this);
        dialog.setContentView(R.layout.dialog_open_top_wallet_page);
        dialog.setTitle("Info");

        Button yesButton = (Button) dialog.findViewById(R.id.okButtonDialogWallet);
        Button noButton = (Button) dialog.findViewById(R.id.cancelButtonDialogWallet);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TopUpWalletPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void showDialogOliGanda(){
        final Dialog dialog = new Dialog(ServiceRutinPage2.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Info");

        TextView text = (TextView)dialog.findViewById(R.id.textTitleDialog);
        text.setText("Oli menggunakan standart Oli dari merek dengan penambahan harga 20.000");
        text.setTypeface(null,Typeface.BOLD);
        ImageView image = (ImageView)dialog.findViewById(R.id.imgIconInfo);
        image.setImageResource(R.drawable.info_dialog_fragment);


        Button btn = (Button)dialog.findViewById(R.id.okButtonDialog);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void showDialogOliMesin(){
        final Dialog dialog = new Dialog(ServiceRutinPage2.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Info");

        TextView text = (TextView)dialog.findViewById(R.id.textTitleDialog);
        text.setText("Oli menggunakan standart Oli dari merek kendaraan");
        text.setTypeface(null,Typeface.BOLD);
        ImageView image = (ImageView)dialog.findViewById(R.id.imgIconInfo);
        image.setImageResource(R.drawable.info_dialog_fragment);


        Button btn = (Button)dialog.findViewById(R.id.okButtonDialog);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void getIntentValue(){
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        transmisi = extra.getString("EXTRA_TRANSMISI");
        jenis = extra.getString("EXTRA_JENIS");
        tipe = extra.getString("EXTRA_TIPE");
        platNomor = extra.getString("EXTRA_PLATNOMOR");

        harga += 60000;
        LinearLayout layoutOliganda = (LinearLayout) findViewById(R.id.oliGandaOption);
        if(transmisi.equals("Matic")){
            layoutOliganda.setVisibility(View.VISIBLE);
        }else{
            layoutOliganda.setVisibility(View.GONE);

        }
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView jam = (TextView) findViewById(R.id.jamServiceRutin);
        if (minute < 10) {
            mTime1 = hourOfDay + ":" + "0" + minute;
        } else {
            mTime1 = hourOfDay + ":" + minute;

        }

        DialogFragment timePicker = new TimePickerFragment();
        if (hourOfDay < 8 ){
            Toast.makeText(this,"harus lebih dari jam 8",Toast.LENGTH_LONG).show();
            timePicker.show(getSupportFragmentManager(),"time picker");
        } else if(hourOfDay > 17 && minute >= 0){
            Toast.makeText(this,"harus kurang dari jam 5 sore",Toast.LENGTH_LONG).show();
            timePicker.show(getSupportFragmentManager(),"time picker");
        } else{
            jam.setTypeface(null, Typeface.BOLD);
            jam.setText(mTime1);
        }
        jam.setText(mTime1);

    }

    public void getCurrentDate(){
        Date dt = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        calendar.add(Calendar.DATE,1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

        List<String> dates = new ArrayList<String>();

        for(int i = 0 ; i < 7 ; i++){
            calendar.add(Calendar.DATE,1);
            dt = calendar.getTime();
            dates.add(dateFormat.format(dt));
        }




        tanggalSpinner = (Spinner) findViewById(R.id.spinnerTanggal);


        ArrayAdapter<String> datesSpinner = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,dates);

        datesSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tanggalSpinner.setAdapter(datesSpinner);
    }
}
