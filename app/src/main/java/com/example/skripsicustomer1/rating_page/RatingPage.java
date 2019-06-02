package com.example.skripsicustomer1.rating_page;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.skripsicustomer1.Montir;
import com.example.skripsicustomer1.Order;
import com.example.skripsicustomer1.R;
import com.example.skripsicustomer1.Rating;
import com.example.skripsicustomer1.customer.HomePage;
import com.example.skripsicustomer1.helper.Convertor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class RatingPage extends AppCompatActivity {

    RatingBar ratingBar;
    ImageView photoMontir;
    TextView typeOrder;
    TextView detailMotor;
    Button submitButton;

    Double tempRating;

    Order order = new Order();
    Convertor convertor = new Convertor();
    DatabaseReference dbOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_page);

        getIntentValue();
        dbOrder = FirebaseDatabase.getInstance().getReference("Orders");

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        photoMontir = (ImageView) findViewById(R.id.fotoMontirRatingPage);
        typeOrder = (TextView) findViewById(R.id.orderTypeRatingPage);
        detailMotor = (TextView) findViewById(R.id.motorRatingPage);
        submitButton = (Button) findViewById(R.id.submitRating);

        final DatabaseReference dbMontir = FirebaseDatabase.getInstance().getReference("Montirs");

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tempRating = Double.valueOf(rating);
            }
        });

        photoMontir.setImageBitmap(convertor.convertBase64toBitmap(order.getMontir().getImage()));
        typeOrder.setText(order.getType_order());
        String temp =  order.getNumber_plate() + " " + order.getType_motor();
        detailMotor.setText(temp);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dbRating = FirebaseDatabase.getInstance().getReference("Ratings").child(order.getMontir().getId());
                dbRating.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            final Rating rtg = dataSnapshot.getValue(Rating.class);

                            dbMontir.child(order.getMontir().getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    DatabaseReference dbMontirUpdate = FirebaseDatabase.getInstance().getReference("Montirs").child(order.getMontir().getId());
                                    DatabaseReference dbRatingUpdate = FirebaseDatabase.getInstance().getReference("Ratings").child(order.getMontir().getId());
                                    Montir m = dataSnapshot.getValue(Montir.class);

                                    Double calculateRating = m.getRating() + tempRating;
                                    Map<String, Object> updateMontir = new HashMap<String, Object>();
                                    updateMontir.put("rating",calculateRating);
                                    dbMontirUpdate.updateChildren(updateMontir);

                                    Map<String, Object> updateRating = new HashMap<String, Object>();
                                    updateRating.put("average_rating",calculateRating / rtg.getCount_order());
                                    updateRating.put("rating_montir",calculateRating);
                                    dbRatingUpdate.updateChildren(updateRating);
                                    dbOrder.child(order.getId()).child("flag_rating").setValue(false);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        } else {
                            DatabaseReference dbMontirUpdate = FirebaseDatabase.getInstance().getReference("Montirs").child(order.getMontir().getId());
                            Map<String, Object> updateMontir = new HashMap<String, Object>();
                            updateMontir.put("rating",tempRating);
                            dbMontirUpdate.updateChildren(updateMontir);
                            Double averageRating = tempRating / 1;
                            Rating rating = new Rating(
                                tempRating,
                        1,
                                averageRating
                            );
                            FirebaseDatabase.getInstance().getReference("Ratings")
                                    .child(order.getMontir().getId())
                                    .setValue(rating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    dbOrder.child(order.getId()).child("flag_rating").setValue(false);
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
    public void getIntentValue() {
        Gson gson = new Gson();
        Intent intent = getIntent();
        if (intent.getExtras() != null)
            order = gson.fromJson(intent.getExtras().getString("ORDER_DONE"), Order.class);
    }
}
