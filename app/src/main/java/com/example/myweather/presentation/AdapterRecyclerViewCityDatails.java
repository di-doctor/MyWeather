package com.example.myweather.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.myweather.R;
import com.example.myweather.data.ForecastListOfItemForWeatherDetails;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterRecyclerViewCityDatails extends RecyclerView.Adapter<AdapterRecyclerViewCityDatails.DetailsViewHolder> {
    private List<ForecastListOfItemForWeatherDetails> dataDetails = new ArrayList<>();

    public void setNewData(List<ForecastListOfItemForWeatherDetails> list) {
        dataDetails.clear();
        dataDetails.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_of_item_details, parent, false);
        return new DetailsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder holder, int position) {
        long timeMilisecond = Long.valueOf(dataDetails.get(position).getDt()) * 1000;      // its need to be in milisecond
        Date df = new java.util.Date(timeMilisecond);
        String finalTime = new SimpleDateFormat("MM dd, yyyy hh:mma").format(df);

        holder.dateText.setText(finalTime);
        holder.tempDay.setText((int) dataDetails.get(position).getTempForecastDay());
        holder.tempMorning.setText((int) dataDetails.get(position).getTempForecastDay());
        holder.tempEve.setText((int) dataDetails.get(position).getTempForecastDay());
        holder.tempNight.setText((int) dataDetails.get(position).getTempForecastDay());

        ImageView image = holder.imageIcon;
        String startAddress = "https://openweathermap.org/img/wn/";
        String iconId = dataDetails.get(position).getIcon();
        if (iconId == null) {
            holder.imageIcon.setImageDrawable(null);
            return;
        }
        String stopAddress = "@2x.png";
        String finalAddress = startAddress + iconId + stopAddress;
        Glide.with(holder.imageIcon.getContext()).load(finalAddress).into(image);
    }

    @Override
    public int getItemCount() {
        return dataDetails.size();
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        public TextView dateText;
        public ImageView imageIcon;
        public TextView tempMorning;
        public TextView tempDay;
        public TextView tempEve;
        public TextView tempNight;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = (TextView) itemView.findViewById(R.id.textRv1);
            tempMorning = (TextView) itemView.findViewById(R.id.textRv2);
            tempDay = (TextView) itemView.findViewById(R.id.textRv3);
            tempEve = (TextView) itemView.findViewById(R.id.textRv4);
            tempNight = (TextView) itemView.findViewById(R.id.textRv5);
            imageIcon = (ImageView) itemView.findViewById(R.id.imageViewRv);
        }
    }
}
