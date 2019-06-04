package com.example.skripsicustomer1.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skripsicustomer1.Customer;
import com.example.skripsicustomer1.Montir;
import com.example.skripsicustomer1.R;
import com.example.skripsicustomer1.Rating;
import com.example.skripsicustomer1.helper.Convertor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MontirAdapter extends ArrayAdapter<Montir> {

    private Context mContext;
    private List<Montir> montirList = new ArrayList<>();
    Convertor convertor = new Convertor();

    public MontirAdapter(@NonNull Context context, int resource,@NonNull List<Montir> objects) {
        super(context, resource, objects);
        mContext = context;
        montirList = objects;
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_view,parent,false);


        Montir currentMontir = montirList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.listNama);
        name.setText(currentMontir.getName());

        ImageView fotoMontir = (ImageView) listItem.findViewById(R.id.fotoMontirListMontir);
        fotoMontir.setImageBitmap(convertor.convertBase64toBitmap(currentMontir.getImage()));

        TextView averageRating = (TextView) listItem.findViewById(R.id.ratingListMontir);
        averageRating.setText(String.format("%.2f",currentMontir.getRating()));

        return listItem;
    }

    @Nullable
    @Override
    public Montir getItem(int position) {
        return super.getItem(position);
    }
}
