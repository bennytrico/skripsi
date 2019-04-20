package com.example.skripsicustomer1.customer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skripsicustomer1.R;
import com.example.skripsicustomer1.customer.service_rutin_page.ServiceRutinPage2;

import org.w3c.dom.Text;


public class ServiceRutinPage extends Fragment {

    private String temp = "";
    Spinner tipeMotorSpinner ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_service_rutin_page,container,false);

        ImageButton btnMatic = (ImageButton) view.findViewById(R.id.motorMatic);
        ImageButton btnKopling = (ImageButton) view.findViewById(R.id.motorKopling);
        ImageButton btnManual = (ImageButton) view.findViewById(R.id.motorManual);
        final EditText platNomorServiceRutin = (EditText) view.findViewById(R.id.platNomorServiceRutin);
        final Button btnNextServiceRutin = (Button) view.findViewById(R.id.btnNextServiceRutin);
        final Spinner merekSpinner = (Spinner) view.findViewById(R.id.listMerekMotor);
        tipeMotorSpinner = (Spinner) view.findViewById(R.id.tipeMotor);
        platNomorServiceRutin.setVisibility(View.INVISIBLE);

        btnMatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                temp = "Matic";
                merekSpinner.setVisibility(View.VISIBLE);
                merekSpinner.setSelection(0);
                tipeMotorSpinner.setVisibility(View.INVISIBLE);
                btnNextServiceRutin.setVisibility(View.INVISIBLE);
            }
        });
        btnKopling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = "Kopling";
                merekSpinner.setVisibility(View.VISIBLE);
                merekSpinner.setSelection(0);
                tipeMotorSpinner.setVisibility(View.INVISIBLE);
                btnNextServiceRutin.setVisibility(View.INVISIBLE);
            }
        });
        btnManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = "Manual";
                merekSpinner.setVisibility(View.VISIBLE);
                merekSpinner.setSelection(0);
                tipeMotorSpinner.setVisibility(View.INVISIBLE);
                btnNextServiceRutin.setVisibility(View.INVISIBLE);
            }
        });
        btnNextServiceRutin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = true;
                if (TextUtils.isEmpty(merekSpinner.getSelectedItem().toString())) {
                    Toast.makeText(getActivity(),"pilih jenis motor terlebih dahulu",Toast.LENGTH_LONG).show();
                    flag = false;
                }
                if (TextUtils.isEmpty(tipeMotorSpinner.getSelectedItem().toString())) {
                    Toast.makeText(getActivity(),"pilih tipe motor terlebih dahulu",Toast.LENGTH_LONG).show();
                    flag = false;
                }
                if (TextUtils.isEmpty(platNomorServiceRutin.getText().toString())) {
                    Toast.makeText(getActivity(),"isi plat nomor",Toast.LENGTH_LONG).show();
                    flag = false;
                }
                if (flag) {
                    Intent startActivityServiceRutin = new Intent(getActivity(), ServiceRutinPage2.class);
                    Bundle extras = new Bundle();
                    extras.putString("EXTRA_TRANSMISI", temp);
                    extras.putString("EXTRA_JENIS", merekSpinner.getSelectedItem().toString());
                    extras.putString("EXTRA_TIPE", tipeMotorSpinner.getSelectedItem().toString());
                    extras.putString("EXTRA_PLATNOMOR", platNomorServiceRutin.getText().toString());
                    startActivityServiceRutin.putExtras(extras);

                    startActivity(startActivityServiceRutin);
                }
            }
        });

        merekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString().equals("-")){
                    tipeMotorSpinner.setVisibility(View.INVISIBLE);
                    btnNextServiceRutin.setVisibility(View.INVISIBLE);
                    platNomorServiceRutin.setVisibility(View.VISIBLE);


                }else if(parent.getItemAtPosition(position).toString().equals("Honda") && temp.equals("Matic")) {
                    String tipeMotors[] = getResources().getStringArray(R.array.HondaMatic);

                    setSpinnerTypeMotor(tipeMotors);
                    tipeMotorSpinner.setVisibility(View.VISIBLE);
                    btnNextServiceRutin.setVisibility(View.VISIBLE);
                    platNomorServiceRutin.setVisibility(View.VISIBLE);


                }else if(parent.getItemAtPosition(position).toString().equals("Honda") && temp.equals("Manual")){
                    String tipeMotors[] = getResources().getStringArray(R.array.HondaManual);

                    setSpinnerTypeMotor(tipeMotors);
                    tipeMotorSpinner.setVisibility(View.VISIBLE);
                    btnNextServiceRutin.setVisibility(View.VISIBLE);
                    platNomorServiceRutin.setVisibility(View.VISIBLE);


                }else if(parent.getItemAtPosition(position).toString().equals("Honda") && temp.equals("Kopling")){
                    String tipeMotors[] = getResources().getStringArray(R.array.HondaKopling);

                    setSpinnerTypeMotor(tipeMotors);
                    tipeMotorSpinner.setVisibility(View.VISIBLE);
                    btnNextServiceRutin.setVisibility(View.VISIBLE);
                    platNomorServiceRutin.setVisibility(View.VISIBLE);


                }else if(parent.getItemAtPosition(position).toString().equals("Yamaha") && temp.equals("Matic")){
                    String tipeMotors[] = getResources().getStringArray(R.array.YamahaMatic);

                    setSpinnerTypeMotor(tipeMotors);
                    tipeMotorSpinner.setVisibility(View.VISIBLE);
                    btnNextServiceRutin.setVisibility(View.VISIBLE);
                    platNomorServiceRutin.setVisibility(View.VISIBLE);

                }else if(parent.getItemAtPosition(position).toString().equals("Yamaha") && temp.equals("Manual")){
                    String tipeMotors[] = getResources().getStringArray(R.array.YamahaManual);

                    setSpinnerTypeMotor(tipeMotors);
                    tipeMotorSpinner.setVisibility(View.VISIBLE);
                    btnNextServiceRutin.setVisibility(View.VISIBLE);
                    platNomorServiceRutin.setVisibility(View.VISIBLE);



                }else if(parent.getItemAtPosition(position).toString().equals("Yamaha")&& temp.equals("Kopling")){
                    String tipeMotors[] = getResources().getStringArray(R.array.YamahaKopling);

                    setSpinnerTypeMotor(tipeMotors);
                    tipeMotorSpinner.setVisibility(View.VISIBLE);
                    btnNextServiceRutin.setVisibility(View.VISIBLE);
                    platNomorServiceRutin.setVisibility(View.VISIBLE);


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                return;
            }
        });

        return view;
    }
    public void setSpinnerTypeMotor(String tipeMotors[]){

        ArrayAdapter<String> typeMotor = new ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_spinner_dropdown_item,tipeMotors);
        typeMotor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tipeMotorSpinner.setAdapter(typeMotor);
    }


}
