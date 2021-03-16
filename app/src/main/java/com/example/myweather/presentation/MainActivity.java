package com.example.myweather.presentation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweather.AdapterRecicleViewOnMainActivity;
import com.example.myweather.CityObject;
import com.example.myweather.R;
import com.example.myweather.WeatherObject;
import com.example.myweather.databinding.ActivityMainBinding;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class MainActivity extends MvpAppCompatActivity implements MainActivityView, View.OnClickListener {
    @InjectPresenter
    MainActivityPresenter mainActivityPresenter;

    private static final String SAVED_ID = "Saved_id";
    private static final String SP_NAME = "sp_name";
    static final String CITY_ID_EXTRA = "message";
    private final int requestCode = 1;
    ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    long timeRunRefresh = 0;
    Toolbar toolbar;
    int idCity = 0;
    SharedPreferences sPref;
    private AdapterRecicleViewOnMainActivity mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ActivityMainBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        binding.anotherActivityButton.setOnClickListener(this);     // назначение кнопке слушателя this
        binding.recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        mAdapter = new AdapterRecicleViewOnMainActivity();
        binding.recyclerView.setAdapter(mAdapter);
        sPref = getSharedPreferences(SP_NAME, MODE_PRIVATE);
        if (!mainActivityPresenter.isInRestoreState(this)) {
            mainActivityPresenter.weatherUpdateTempList();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.refresh_weather_button:
                mainActivityPresenter.weatherUpdateTempList();
                //reFreshList(false);
                break;
            case R.id.id_about:
                Toast.makeText(this, "Коллектив  на Милашенкова 15", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    public void OnAbout(MenuItem menu) {

        // Toast.makeText(this, "Коллектив  на Милашенкова 15", Toast.LENGTH_SHORT).show();
    }
//    private void reFreshList(boolean force) {
//
//        if (System.currentTimeMillis() - timeRunRefresh > 5000 || force) {
//            if (!force) timeRunRefresh = System.currentTimeMillis(); // если по форсу выполняем то таймер не обнуляем.
//
//            Set<String> setS = sPref.getStringSet(SAVED_ID, null);
//            mAdapter.clearData();
//
//            for (String idString : setS) {
//
//                WeatherAPI.getClient().getTempCall(idString, "metric", WeatherAPI.KEY).enqueue(new Callback<WeatherResponse>() {
//                    @Override
//                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
//                        WeatherResponse weatherResponse = response.body();
//                        Log.d("weather", Float.toString(weatherResponse.getMain().getTemp()));
//                        mAdapter.addData(WeatherObject.convertFromResponse(weatherResponse));
//                    }
//
//                    @Override
//                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
//                        Log.e("weather", "er", t);
//                    }
//                });
//            }
//        }
//    }

    @Override
    protected void onResume() {
        //BaseQuickAdapter
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        // Создаем объект Intent для вызова новой Activity
        Intent intent = new Intent(this, SelectCityActivity.class);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int rq, int resultCode, Intent data) {
        if (rq == requestCode) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                CityObject cityObjectResponse = (CityObject) bundle.getSerializable(CITY_ID_EXTRA);
                if (cityObjectResponse == null) {
                    throw new RuntimeException("No data");
                }

                mainActivityPresenter.saveCityId(cityObjectResponse);
            }
        } else {
            super.onActivityResult(rq, resultCode, data);
        }
    }

    @Override
    public void onCityAdded(WeatherObject weatherObject) {
        runOnUiThread(() -> {
            mAdapter.addData(weatherObject);
        });
    }

    @Override
    public void onCitiesLoaded(List<WeatherObject> list) {
        runOnUiThread(()-> mAdapter.setNewData(list));
    }

    @Override
    public void errorLoad() {
        runOnUiThread(() -> {
            Toast.makeText(this, "Update error", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void successLoad() {
        runOnUiThread(() -> {
            Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();
        });
    }
}

