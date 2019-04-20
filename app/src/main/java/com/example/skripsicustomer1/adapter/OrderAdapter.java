package com.example.skripsicustomer1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.skripsicustomer1.Order;
import com.example.skripsicustomer1.R;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends ArrayAdapter<Order> {
    private Context mContext;
    private List<Order> orderList = new ArrayList<>();
    public OrderAdapter(@NonNull Context context, int resource, @NonNull List<Order> objects) {
        super(context, resource, objects);
        mContext = context;
        orderList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_view_order,parent,false);

        Order currentOrder = orderList.get(position);

        TextView typeOrder = (TextView)listItem.findViewById(R.id.listOrderType);
        typeOrder.setText(currentOrder.getType_order());

        return listItem;
    }
}
