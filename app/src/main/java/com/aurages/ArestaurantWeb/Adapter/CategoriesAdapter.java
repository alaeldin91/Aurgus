package com.aurages.ArestaurantWeb.Adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Model.Pivot;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Pivot> pivotsList = new ArrayList<>();
    Context context;
    public CategoriesAdapter(ArrayList<Pivot> pivotsList, Context context){
        this.pivotsList = pivotsList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return pivotsList.size();
    }
}
