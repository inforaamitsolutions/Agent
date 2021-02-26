package com.codeclinic.agent.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.ActivityMainBinding;
import com.codeclinic.agent.fragment.CustomerFragment;
import com.codeclinic.agent.fragment.DefaultFragment;
import com.codeclinic.agent.fragment.HomeFragment;
import com.codeclinic.agent.fragment.LoanFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
ActivityMainBinding binding;
HomeFragment homeFragment;
LoanFragment loanFragment;
DefaultFragment defaultFragment;
CustomerFragment customerFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
         setSupportActionBar(binding.layoutHeader.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
            loadFragment(new HomeFragment());
         binding.bottomNavigation.setOnNavigationItemSelectedListener(this);

        binding.layoutHeader.toolbar.setNavigationIcon(new DrawerArrowDrawable(this));
        binding.layoutHeader.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     if (binding.drawerLayout.isDrawerOpen(binding.navView)) {
                                                         binding.drawerLayout.closeDrawer(binding.navView);
                                                     } else {
                                                         binding.drawerLayout.openDrawer(binding.navView);
                                                     }
                                                 }
                                             }
        );


        binding.drawerLayout.setScrimColor(Color.parseColor("#414141"));

        binding.drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                                     @Override
                                     public void onDrawerSlide(View drawer, float slideOffset) {

                                         binding.content.setX(binding.navView.getWidth() * slideOffset);
                                         RelativeLayout.LayoutParams lp =
                                                 (RelativeLayout.LayoutParams) binding.content.getLayoutParams();
                                         lp.height = drawer.getHeight() -
                                                 (int) (drawer.getHeight() * slideOffset * 0.3f);
                                         lp.topMargin = (drawer.getHeight() - lp.height) / 2;
                                         binding.content.setLayoutParams(lp);
                                     }

                                     @Override
                                     public void onDrawerClosed(View drawerView) {
                                     }
                                 }
        );

    }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.search_menu, menu);
            getMenuInflater().inflate(R.menu.notification_menu, menu);
            getMenuInflater().inflate(R.menu.profile_menu, menu);

            return true;
        }
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.home:
                    binding.layoutHeader.toolbar.setVisibility(View.VISIBLE);
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.loan:
                 binding.layoutHeader.toolbar.setVisibility(View.GONE);
                    fragment = new LoanFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.defaults:
                    binding.layoutHeader.toolbar.setVisibility(View.GONE);
                    fragment = new DefaultFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.customers:
                    binding.layoutHeader.toolbar.setVisibility(View.GONE);
                    fragment = new CustomerFragment();
                    loadFragment(fragment);
                    return true;
            }

            return false;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            switch (id){
                case R.id.profile:
                    startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                    return true;
                case R.id.notification:
                    startActivity(new Intent(MainActivity.this,NotificationActivity.class));
                    return true;
                case R.id.search:
                   startActivity(new Intent(MainActivity.this,SearchActivity.class));
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        private void loadFragment(Fragment fragment) {
            // load fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            finish();
        }
    }