package com.example.myweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecicleViewOnMainActivity extends RecyclerView.Adapter<AdapterRecicleViewOnMainActivity.MyViewHolder> {
    private final ArrayList<WeatherObject> mDataset = new ArrayList<>();

    public void addData(WeatherObject weatherObject) {
        mDataset.add(weatherObject);
        notifyItemInserted(mDataset.size() - 1);
    }
    public void setNewData(List<WeatherObject> list) {
        mDataset.clear();
        mDataset.addAll(list);
        notifyDataSetChanged();
    }
    public void clearData() {
        mDataset.clear();
        notifyDataSetChanged();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView cityTextView;
        public TextView temperatureTextView;

        public MyViewHolder(View v) {
            super(v);
            cityTextView = v.findViewById(R.id.textView1);
            temperatureTextView = v.findViewById(R.id.textView2);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)

    // Create new views (invoked by the layout manager)
    @NotNull
    @Override
    public AdapterRecicleViewOnMainActivity.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.cityTextView.setText(mDataset.get(position).getCity());
        holder.temperatureTextView.setText(mDataset.get(position).getTemperature().toString());
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
