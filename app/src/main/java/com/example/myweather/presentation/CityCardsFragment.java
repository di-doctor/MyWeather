package com.example.myweather.presentation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myweather.AdapterRecicleViewOnMainActivity;
import com.example.myweather.CityObject;
import com.example.myweather.R;
import com.example.myweather.WeatherObject;
import com.example.myweather.databinding.FragmentMainBinding;
import com.example.myweather.presentation.utils.RecyclerItemClickListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

import static android.app.Activity.RESULT_OK;


public class CityCardsFragment extends  MvpAppCompatFragment
        implements MainActivityView, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, RecyclerItemClickListener.OnItemClickListener {
    @InjectPresenter
    MainActivityPresenter mainActivityPresenter;
    private static final int REQUEST_CODE = 1;
    static final String CITY_ID_EXTRA = "message";
    private AdapterRecicleViewOnMainActivity mAdapter;
    FragmentMainBinding binding;
    public CityCardsFragment() {
    }

    public static CityCardsFragment newInstance() {
      return new CityCardsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.swipeRefreshLayout.setOnRefreshListener(this);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(binding.toolbar);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAdapter = new AdapterRecicleViewOnMainActivity();
        binding.recyclerView.setAdapter(mAdapter);

        if (savedInstanceState == null) {
            Log.d("qwert","debug");
            mainActivityPresenter.weatherUpdateTempList();
        }
        binding.floatingActionButton.setOnClickListener(this);

        binding.recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),binding.recyclerView, this));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE == requestCode) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                CityObject cityObjectResponse = (CityObject) bundle.getSerializable(CITY_ID_EXTRA);
                if (cityObjectResponse == null) {
                    throw new RuntimeException("No data");
                }
                mainActivityPresenter.saveCityId(cityObjectResponse);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.activity_main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.refresh_weather_button) {
            mainActivityPresenter.weatherUpdateTempList();
            //reFreshList(false);
        }
        return true;
    }

    @Override
    public void onCityAdded(WeatherObject weatherObject) {
            mAdapter.addData(weatherObject);
    }

    @Override
    public void onCitiesLoaded(List<WeatherObject> list) {
        mAdapter.setNewData(list);
    }

    @Override
    public void errorLoad() {
        Snackbar snackbar= Snackbar.make(binding.getRoot(), "Update error", Snackbar.LENGTH_INDEFINITE)
                .setAction("Try update again..", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mainActivityPresenter.weatherUpdateTempList();
                    }
                })
                .setTextColor(Color.RED)
                .setBackgroundTint(Color.GREEN);
        snackbar.show();
    }

    @Override
    public void successLoad() {
         Toast.makeText(getContext(), "Update success", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void finishLoad() {
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        mainActivityPresenter.weatherUpdateTempList();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), SelectCityActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

        //отработка нажатия на элемент ресайклер вью.
    @Override
    public void onItemClick(View view, int position) {
        WeatherObject weatherObject = ((AdapterRecicleViewOnMainActivity)binding.recyclerView.getAdapter())
                .getItemByPosition(position);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootLayout, CityDetailsFragment.newInstance(weatherObject.getId()))
                .addToBackStack(null)
                .commit();
    }

    //долгое нажатие на элемент ресайклер вью
    @Override
    public void onLongItemClick(View view, int position) {

    }
}