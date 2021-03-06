package com.example.myweather.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myweather.CityAdapterOnTwoActivity;
import com.example.myweather.CityObject;
import com.example.myweather.OnItemClickListener;
import com.example.myweather.databinding.ActivityAnotherBinding;

import java.util.ArrayList;
import java.util.List;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class SelectCityActivity extends MvpAppCompatActivity implements SelectCityActivityView, OnItemClickListener {
    @InjectPresenter
    SelectCityActivityPresenter selectCityActivityPresenter;

    ActivityAnotherBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnotherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.recyclerViewTwoActivity.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerViewTwoActivity.setLayoutManager(layoutManager);
        binding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                selectCityActivityPresenter.queryCities(s.toString());
            }
        });

        CityAdapterOnTwoActivity cityAdapterOnTwoActivity = new CityAdapterOnTwoActivity(this);
        binding.recyclerViewTwoActivity.setAdapter(cityAdapterOnTwoActivity);


        if (!selectCityActivityPresenter.isInRestoreState(this)) {
            selectCityActivityPresenter.loadCities(this.getApplicationContext());
            binding.flProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoadSuccess(List<CityObject> listCityObject) {
        runOnUiThread(() -> {
            CityAdapterOnTwoActivity adapter = (CityAdapterOnTwoActivity) binding.recyclerViewTwoActivity.getAdapter();
            if (adapter == null) {
                return;
            }
            adapter.setNewData(listCityObject);
            binding.flProgressBar.setVisibility(View.GONE);
        });
    }

    @Override
    public void onLoadFailure(String str) {
        runOnUiThread(() -> {
            Toast.makeText(this, "Failure load", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onItemClick(CityObject cityObject) {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.CITY_ID_EXTRA, cityObject);
        setResult(RESULT_OK, intent);
        finish();
    }
}