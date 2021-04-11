
package com.example.myweather.presentation;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweather.AdapterRecicleViewOnMainActivity;
import com.example.myweather.R;

import moxy.MvpAppCompatActivity;

public class MainActivity extends MvpAppCompatActivity  {

    private static final String SAVED_ID = "Saved_id";
    private static final String SP_NAME = "sp_name";
    static final String CITY_ID_EXTRA = "message";
    private AdapterRecicleViewOnMainActivity mAdapter;
    private RecyclerView.LayoutManager GridLayoutManager;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootLayout,CityCardsFragment.newInstance())
                .commit();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
//        return true;
//    }

    @Override
    public void onActivityResult(int rq, int resultCode, Intent data) {
        super.onActivityResult(rq, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(rq, resultCode, data);
        }
    }
}

