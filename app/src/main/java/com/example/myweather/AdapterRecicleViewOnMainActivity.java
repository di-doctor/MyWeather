package com.example.myweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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
        public ImageView imageView;

        public MyViewHolder(View v) {
            super(v);
            cityTextView = v.findViewById(R.id.city_name);
            temperatureTextView = v.findViewById(R.id.city_temp);
            imageView = v.findViewById(R.id.imageView);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)

    // Create new views (invoked by the layout manager)
    @NotNull
    @Override
    public AdapterRecicleViewOnMainActivity.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.cityTextView.setText(mDataset.get(position).getCity());
        int temp = Math.round(mDataset.get(position).getTemperature());
        String str = String.valueOf(temp);
        if (temp > 0) {
            str = "+" + str;
        }

        holder.temperatureTextView.setText(str);

        Glide.with(holder.imageView.getContext()).load("https://weather.rambler.ru/favicons/browser_push_icon_256x256.png").into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
