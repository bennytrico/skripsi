package com.example.skripsicustomer1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.skripsicustomer1.Customer;
import com.example.skripsicustomer1.Montir;
import com.example.skripsicustomer1.R;

import java.util.ArrayList;
import java.util.List;

public class MontirAdapter extends ArrayAdapter<Montir> {

    private Context mContext;
    private List<Montir> montirList = new ArrayList<>();

    public MontirAdapter(@NonNull Context context, int resource,@NonNull List<Montir> objects) {
        super(context, resource, objects);
        mContext = context;
        montirList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_view,parent,false);

        Montir currentCustomer = montirList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.listNama);
        name.setText(currentCustomer.getName());

        return listItem;
    }

    @Nullable
    @Override
    public Montir getItem(int position) {
        return super.getItem(position);
    }
}
