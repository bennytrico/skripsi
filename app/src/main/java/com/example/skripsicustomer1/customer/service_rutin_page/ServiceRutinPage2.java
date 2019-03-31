package com.example.skripsicustomer1.customer.service_rutin_page;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Typeface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.skripsicustomer1.R;
import com.example.skripsicustomer1.helper.TimePickerFragment;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ServiceRutinPage2 extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_rutin_page2);
        getCurrentDate();

        Button btnHours = (Button) findViewById(R.id.getHours);
        ImageButton btnInfoOli = (ImageButton) findViewById(R.id.infoPergantianOli);

        btnHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });

        btnInfoOli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

    }
    private void showDialog(){
        final Dialog dialog = new Dialog(ServiceRutinPage2.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Info");

        TextView text = (TextView)dialog.findViewById(R.id.textTitleDialog);
        text.setText("Oli menggunakan standart Oli dari merek");
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
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String mTime1;
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



        Spinner tanggalSpinner = (Spinner) findViewById(R.id.spinnerTanggal);


        ArrayAdapter<String> datesSpinner = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,dates);

        datesSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tanggalSpinner.setAdapter(datesSpinner);



    }
}
