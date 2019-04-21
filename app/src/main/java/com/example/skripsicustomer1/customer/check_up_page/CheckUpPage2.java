package com.example.skripsicustomer1.customer.check_up_page;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.skripsicustomer1.R;
import com.example.skripsicustomer1.helper.TimePickerFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CheckUpPage2 extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private String transmisi;
    private String jenis;
    private String tipe;
    private String mTime1;
    private String platNomor;
    private Integer harga = 60000;
    Spinner typeCheckupSpinner;
    Spinner tanggalSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_up_page2);
        getCurrentDate();
        Button btnHours = (Button) findViewById(R.id.getHoursCheckUp);
        String[] getTypeCheckup = getResources().getStringArray(R.array.CheckUpListType);
        Button btnNextCheckUp = (Button) findViewById(R.id.btnNextCheckUp2);
        TextView textHarga = (TextView) findViewById(R.id.hargaCheckUp);


        textHarga.setText(""+harga);

        getIntentValue();

        btnNextCheckUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                startActivity(intent);
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
