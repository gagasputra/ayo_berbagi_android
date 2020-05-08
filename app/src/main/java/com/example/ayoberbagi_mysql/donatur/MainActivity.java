package com.example.ayoberbagi_mysql.donatur;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ayoberbagi_mysql.R;
import com.example.ayoberbagi_mysql.config.BottomNavigationBehavior;
import com.example.ayoberbagi_mysql.donatur.fragment.BencanaFragment;
import com.example.ayoberbagi_mysql.donatur.fragment.BeritaFragment;
import com.example.ayoberbagi_mysql.donatur.fragment.DProsesFragment;
import com.example.ayoberbagi_mysql.donatur.fragment.HistoryFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;
    boolean doubleBackToExitPressedOnce = false;
    Fragment dProsesFragment = new DProsesFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        toolbar.setTitle("Home");
        loadFragment(new BencanaFragment());


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle("Home");
                    fragment = new BencanaFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_berita:
                    toolbar.setTitle("Berita");
                    fragment = new BeritaFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_proses:
                    toolbar.setTitle("Donasi Proses");
                    fragment = new DProsesFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_history:
                    toolbar.setTitle("Riwayat Donasi");
                    fragment = new HistoryFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
//                    toolbar.setTitle("Profile");
                    Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(i);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
            // load fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        public void afterDonasi(Fragment dProsesFragment){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_container, dProsesFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//            }
//        }, 2000);
//    }



//        txt_id.setText("ID : " + id);
//        txt_username.setText("USERNAME : " + username);


        @Override
        public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.logo)
                .setTitle("Tutup Aplikasi")
                .setMessage("Apakah anda ingin keluar dari Ayo Berbagi?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("Tidak", null)
                .show();
    }
}