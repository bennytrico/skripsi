package com.example.skripsicustomer1.customer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import com.example.skripsicustomer1.CurrentUser;
import com.example.skripsicustomer1.Order;
import com.example.skripsicustomer1.R;
import com.example.skripsicustomer1.customer.check_up_page.CheckUpPage2;
import com.example.skripsicustomer1.customer.service_rutin_page.ServiceRutinPage2;
import com.example.skripsicustomer1.rating_page.RatingPage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import static com.example.skripsicustomer1.CurrentUser.currentUserID;


public class ServiceRutinPage extends Fragment {

    private String temp = "";
    Spinner tipeMotorSpinner ;
    Spinner merekSpinner;
    EditText platNomorServiceRutin;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_service_rutin_page,container,false);

        final ImageButton btnMatic = (ImageButton) view.findViewById(R.id.motorMatic);
        final ImageButton btnKopling = (ImageButton) view.findViewById(R.id.motorKopling);
        final ImageButton btnManual = (ImageButton) view.findViewById(R.id.motorManual);
        platNomorServiceRutin = (EditText) view.findViewById(R.id.platNomorServiceRutin);
        final Button btnNextServiceRutin = (Button) view.findViewById(R.id.btnNextServiceRutin);
        merekSpinner = (Spinner) view.findViewById(R.id.listMerekMotor);
        tipeMotorSpinner = (Spinner) view.findViewById(R.id.tipeMotor);
        platNomorServiceRutin.setVisibility(View.INVISIBLE);

        btnMatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                temp = "Matic";
                merekSpinner.setVisibility(View.VISIBLE);
                merekSpinner.setSelection(0);
                btnMatic.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btnKopling.setBackgroundResource(R.drawable.customborder);
                btnManual.setBackgroundResource(R.drawable.customborder);
                tipeMotorSpinner.setVisibility(View.INVISIBLE);
                btnNextServiceRutin.setVisibility(View.INVISIBLE);
            }
        });
        btnKopling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = "Kopling";
                merekSpinner.setVisibility(View.VISIBLE);
                btnKopling.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btnManual.setBackgroundResource(R.drawable.customborder);
                btnMatic.setBackgroundResource(R.drawable.customborder);
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
                btnManual.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btnKopling.setBackgroundResource(R.drawable.customborder);
                btnMatic.setBackgroundResource(R.drawable.customborder);
                merekSpinner.setSelection(0);
                tipeMotorSpinner.setVisibility(View.INVISIBLE);
                btnNextServiceRutin.setVisibility(View.INVISIBLE);
            }
        });
        btnNextServiceRutin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValidationFlagRatingSystem();
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
    public void getValidationFlagRatingSystem () {
        DatabaseReference dbOrder = FirebaseDatabase.getInstance().getReference("Orders");
        dbOrder.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    Order order = data.getValue(Order.class);
                    if (order.getCustomer_id().equals(currentUserID)) {
                        if (order.getFlag_rating()) {
                            Gson gson = new Gson();
                            order.setId(data.getKey());

                            Order orderSelected = order;

                            String orderJson = gson.toJson(orderSelected);
                            Intent intent = new Intent(getActivity(), RatingPage.class);
                            intent.putExtra("ORDER_DONE",orderJson);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
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
                                startActivityServiceRutin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                startActivity(startActivityServiceRutin);
                            }
                        }
                    }else {
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
                            startActivityServiceRutin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            startActivity(startActivityServiceRutin);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
