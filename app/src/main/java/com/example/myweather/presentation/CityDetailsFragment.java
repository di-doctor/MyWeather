package com.example.myweather.presentation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myweather.AdapterRecicleViewOnMainActivity;
import com.example.myweather.data.WeatherDetails;
import com.example.myweather.databinding.FragmentCityDetailsBinding;
import com.example.myweather.databinding.FragmentMainBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CityDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CityDetailsFragment extends MvpAppCompatFragment implements CityDetailsView, OnMapReadyCallback {
    @InjectPresenter
    CityDetailsFragmentPresenter cityDetailsFragmentPresenter;
    private AdapterRecyclerViewCityDatails adapterRecyclerViewCityDatails;
    GoogleMap map;

    private static final String ARG_CITY_ID = "cityId";
    private AdapterRecicleViewOnMainActivity mAdapter;
    private int mCityId;
    float coordLon;
    float coordLat;
    FragmentCityDetailsBinding binding;


    public CityDetailsFragment() {
    }

    public static CityDetailsFragment newInstance(int cityId) {
        CityDetailsFragment fragment = new CityDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CITY_ID, cityId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCityId = getArguments().getInt(ARG_CITY_ID);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_city_details, container, false);
        setHasOptionsMenu(true);

        binding = FragmentCityDetailsBinding.inflate(inflater, container, false);   //Класс создается на основании xml представления
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(binding.toolbarItem);
        cityDetailsFragmentPresenter.loadDataOboutCity(mCityId);
        binding.mapView.onCreate(savedInstanceState);
        binding.mapView.getMapAsync(this);
        binding.recyclerViewOfTemp.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerViewOfTemp.setLayoutManager(layoutManager);
        adapterRecyclerViewCityDatails = new AdapterRecyclerViewCityDatails();
        binding.recyclerViewOfTemp.setAdapter(adapterRecyclerViewCityDatails);
    }

    @Override
    public void onLoadStarted() {
        binding.flProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadFinished() {
        binding.flProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoadSuccess(WeatherDetails weatherDetails) {
        binding.flError.setVisibility(View.GONE);
        binding.toolbarItem.setTitle(weatherDetails.getNameCity());
        if (map != null) {
            map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(weatherDetails.getCoord()
                    .getLat(), weatherDetails.getCoord().getLon())));
        }
        adapterRecyclerViewCityDatails.setNewData(weatherDetails.getForeCastListOfItemForWeatherDetails());
    }

    @Override
    public void onLoadFailed(String message) {
        binding.flError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        if (cityDetailsFragmentPresenter.getCoord() != null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(cityDetailsFragmentPresenter
                    .getCoord().getLat(), cityDetailsFragmentPresenter.getCoord().getLon()), 10));

        }

    }


    @Override
    public void onResume() {
        binding.mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapView.onLowMemory();
    }
}