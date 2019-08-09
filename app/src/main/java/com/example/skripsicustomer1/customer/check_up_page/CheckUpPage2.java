package com.example.skripsicustomer1.customer.check_up_page;

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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.skripsicustomer1.Customer;
import com.example.skripsicustomer1.R;
import com.example.skripsicustomer1.customer.service_rutin_page.ServiceRutinPage2;
import com.example.skripsicustomer1.helper.FormatNumber;
import com.example.skripsicustomer1.helper.TimePickerFragment;
import com.example.skripsicustomer1.topup_wallet.TopUpWalletPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CheckUpPage2 extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private String transmisi;
    private String jenis;
    private String tipe;
    private String mTime1;
    private String platNomor;
    private Integer harga = 60000;
    Spinner typeCheckupSpinner;
    Spinner tanggalSpinner;

    FormatNumber formatNumber = new FormatNumber();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_up_page2);
        getCurrentDate();
        Button btnHours = (Button) findViewById(R.id.getHoursCheckUp);
        String[] getTypeCheckup = getResources().getStringArray(R.array.CheckUpListType);
        Button btnNextCheckUp = (Button) findViewById(R.id.btnNextCheckUp2);
        final TextView textHarga = (TextView) findViewById(R.id.hargaCheckUp);


        DatabaseReference dbPrices = FirebaseDatabase.getInstance().getReference("Prices");
        dbPrices.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Long> data = (Map<String, Long>) dataSnapshot.getValue();
                harga = (int) (long) (data.get("price"));
                textHarga.setText(formatNumber.formatNumber(harga));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        getIntentValue();

        btnNextCheckUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dbCustomer = FirebaseDatabase.getInstance().getReference("Customers");
                dbCustomer.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Customer c = dataSnapshot.getValue(Customer.class);
                        if (c.getWallet() >= harga) {
                            if (TextUtils.isEmpty(mTime1)) {
                                Toast.makeText(CheckUpPage2.this,"Harus mengisi jam servis", Toast.LENGTH_SHORT).show();
                            } else if (typeCheckupSpinner.getSelectedItem().toString().contains("-")) {
                                Toast.makeText(CheckUpPage2.this,"Harus mengisi tipe kerusakan",Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(getApplicationContext(),CheckUpPage3.class);
                                Bundle extras = new Bundle();
                                extras.putString("EXTRA_TRANSMISI",transmisi);
                                extras.putString("EXTRA_JENIS",jenis);
                                extras.putString("EXTRA_TIPE",tipe);
                                extras.putString("EXTRA_TANGGAL",tanggalSpinner.getSelectedItem().toString());
                                extras.putString("EXTRA_TYPE_KERUSAKAN",typeCheckupSpinner.getSelectedItem().toString());
                                extras.putInt("EXTRA_HARGA",harga);
                                extras.putString("EXTRA_HOUR",mTime1);
                                extras.putString("EXTRA_PLATNOMOR",platNomor);
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

        btnHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });

        typeCheckupSpinner = (Spinner) findViewById(R.id.jenisCheckUp);
        setSpinnerTypeCheckup(getTypeCheckup);

    }
    private void showDialogOpenTopWallet() {
        final Dialog dialog = new Dialog(CheckUpPage2.this);
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

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView jam = (TextView) findViewById(R.id.jamCheckUp);
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
    public void setSpinnerTypeCheckup(String typeCheckup[]) {
        ArrayAdapter<String> typeCheckUpAdapter = new ArrayAdapter<String>(CheckUpPage2.this,android.R.layout.simple_spinner_dropdown_item,typeCheckup);
        typeCheckUpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeCheckupSpinner.setAdapter(typeCheckUpAdapter);
    }
    public void getIntentValue(){
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        transmisi = extra.getString("EXTRA_TRANSMISI");
        jenis = extra.getString("EXTRA_JENIS");
        tipe = extra.getString("EXTRA_TIPE");
        platNomor = extra.getString("EXTRA_PLATNOMOR");
    }
}
