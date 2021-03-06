package com.example.myweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CityAdapterOnTwoActivity extends RecyclerView.Adapter<CityAdapterOnTwoActivity.CityViewHolder> {

    private final OnItemClickListener onItemClickListener;
    private  List<CityObject> cityObjectList;

    public CityAdapterOnTwoActivity(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.cityObjectList = new ArrayList<>();
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_text_view_two_activity, parent, false);
        return new CityViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        CityObject cityObject = cityObjectList.get(position);
        ((TextView) holder.itemView).setText(cityObject.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(cityObject);
            }
        });
    }
    @Override
    public int getItemCount() {
        return cityObjectList.size();
    }

    public List<CityObject> getCityObjectList() {
        return cityObjectList;
    }

    public static class CityViewHolder extends RecyclerView.ViewHolder {
        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public void setNewData(List<CityObject> newList){
        cityObjectList = newList;
        notifyDataSetChanged();
    }
}
