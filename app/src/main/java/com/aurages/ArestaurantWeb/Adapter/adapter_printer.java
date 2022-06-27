package com.aurages.ArestaurantWeb.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aurages.ArestaurantWeb.Model.ModelPPrinter;
import com.aurages.ArestaurantWeb.R;

import java.util.ArrayList;
import java.util.List;

public class adapter_printer extends ArrayAdapter<String> {
    Context context;
    int resoure;
    private final LayoutInflater mInflater;

    private ArrayList<String> list;

    public adapter_printer(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);
        this.context = context;
        this.resoure = resource;
        this.list = objects;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        final View view = mInflater.inflate(resoure, parent, false);
        TextView txt_name=view.findViewById(R.id.offer_type_txt);

        txt_name.setText(list.get(position));
return view;
    }
}
