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

    public WeatherObject getItemByPosition(int position){
        return mDataset.get(position);
    }

    public void setNewData(List<WeatherObject> list) {
        mDataset.clear();
        mDataset.addAll(list);
        notifyDataSetChanged();
    }

//    public void clearData() {
//        mDataset.clear();
//        notifyDataSetChanged();
//    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView cityTextView;
        public TextView temperatureTextView;
        public ImageView imageView;

        public MyViewHolder(View v) {
            super(v);
            cityTextView = (TextView) v.findViewById(R.id.city_name);
            temperatureTextView = (TextView) v.findViewById(R.id.city_temp);
            imageView = (ImageView) v.findViewById(R.id.imageView);
        }
        void foo(){

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
        ImageView im = holder.imageView;
        String startAddress = "https://openweathermap.org/img/wn/";
        String iconId = mDataset.get(position).getIconId();
        if(iconId == null) {
            holder.imageView.setImageDrawable(null);
            return;
        }
        String stopAddress="@2x.png";
        String finalAddress =  startAddress+iconId+stopAddress;


        Glide.with(holder.imageView.getContext()).load(finalAddress).into(im);
        //Glide.with(holder.imageView.getContext()).load(test).into(im);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
