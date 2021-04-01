package com.codeclinic.agent.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.codeclinic.agent.MainViewModel;
import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.ActivityMainBinding;
import com.codeclinic.agent.fragment.CustomerFragment;
import com.codeclinic.agent.fragment.HomeFragment;
import com.codeclinic.agent.fragment.LeadFragment;
import com.codeclinic.agent.fragment.LoanFragment;
import com.codeclinic.agent.utils.Connection_Detector;
import com.codeclinic.agent.utils.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.codeclinic.agent.utils.SessionManager.sessionManager;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    ActivityMainBinding binding;
    HomeFragment homeFragment;
    LoanFragment loanFragment;
    LeadFragment leadFragment;
    CustomerFragment customerFragment;
    MainViewModel viewModel;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.layoutHeader.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        loadFragment(new HomeFragment());
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        if (Connection_Detector.isInternetAvailable(this)) {
            viewModel.callCustomerForm();

            viewModel.callLeadForm();

            //viewModel.callUserDetailsAPI();
        }


        binding.navigationLayout.tvUserName.setText(
                sessionManager.getUserDetails().get(SessionManager.FirstName)
                        + sessionManager.getUserDetails().get(SessionManager.LastName));

        binding.navigationLayout.tvEmail.setText(
                sessionManager.getUserDetails().get(SessionManager.UserEmail));


        binding.layoutHeader.toolbar.setNavigationIcon(new DrawerArrowDrawable(this));
        binding.layoutHeader.toolbar.setNavigationOnClickListener(v -> {
                    if (binding.drawerLayout.isDrawerOpen(binding.navView)) {
                        binding.drawerLayout.closeDrawer(binding.navView);
                    } else {
                        binding.drawerLayout.openDrawer(binding.navView);
                    }
                }
        );


        //binding.drawerLayout.setScrimColor(Color.parseColor("#414141"));
        binding.drawerLayout.setBackgroundColor(Color.parseColor("#414141"));

        binding.navigationLayout.homeLinear.setOnClickListener(view -> {
            binding.drawerLayout.closeDrawers();
            binding.bottomNavigation.setSelectedItemId(R.id.home);
            loadFragment(new HomeFragment());
            binding.navigationLayout.aboutLinear.setBackground(null);
            binding.navigationLayout.loanLinear.setBackground(null);
            binding.navigationLayout.defaultsLinear.setBackground(null);
            binding.navigationLayout.leadExpandLayout.setBackground(null);
            binding.navigationLayout.customerExpandLayout.setBackground(null);
            binding.navigationLayout.homeLinear.setBackground(ContextCompat.getDrawable(this, R.drawable.button_bg));

            binding.navigationLayout.leadExpandLayout.collapse();
            binding.navigationLayout.customerExpandLayout.collapse();
        });
        binding.navigationLayout.loanLinear.setOnClickListener(view -> {
            binding.drawerLayout.closeDrawers();
            binding.bottomNavigation.setSelectedItemId(R.id.loan);
            loadFragment(new LoanFragment());
            binding.navigationLayout.aboutLinear.setBackground(null);
            binding.navigationLayout.homeLinear.setBackground(null);
            binding.navigationLayout.defaultsLinear.setBackground(null);
            binding.navigationLayout.leadExpandLayout.setBackground(null);
            binding.navigationLayout.customerExpandLayout.setBackground(null);
            binding.navigationLayout.loanLinear.setBackground(ContextCompat.getDrawable(this, R.drawable.button_bg));


            binding.navigationLayout.leadExpandLayout.collapse();
            binding.navigationLayout.customerExpandLayout.collapse();
        });
        binding.navigationLayout.defaultsLinear.setOnClickListener(view -> {
            binding.drawerLayout.closeDrawers();
            binding.bottomNavigation.setSelectedItemId(R.id.defaults);
            loadFragment(new LeadFragment());
            binding.navigationLayout.aboutLinear.setBackground(null);
            binding.navigationLayout.homeLinear.setBackground(null);
            binding.navigationLayout.loanLinear.setBackground(null);
            binding.navigationLayout.leadExpandLayout.setBackground(null);
            binding.navigationLayout.customerExpandLayout.setBackground(null);
            binding.navigationLayout.defaultsLinear.setBackground(ContextCompat.getDrawable(this, R.drawable.button_bg));

            binding.navigationLayout.leadExpandLayout.collapse();
            binding.navigationLayout.customerExpandLayout.collapse();

        });

        binding.navigationLayout.leadExpandLayout.setOnClickListener(view -> {
            //binding.drawerLayout.closeDrawers();
            binding.navigationLayout.aboutLinear.setBackground(null);
            binding.navigationLayout.homeLinear.setBackground(null);
            binding.navigationLayout.loanLinear.setBackground(null);
            binding.navigationLayout.defaultsLinear.setBackground(null);
            binding.navigationLayout.customerExpandLayout.setBackground(null);
            binding.navigationLayout.leadExpandLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.button_bg));
            // Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();

            binding.navigationLayout.customerExpandLayout.collapse();

            if (binding.navigationLayout.leadExpandLayout.isExpanded()) {
                binding.navigationLayout.leadExpandLayout.collapse();
            } else {
                binding.navigationLayout.leadExpandLayout.expand();
            }
        });

        binding.navigationLayout.leadExpandLayout.secondLayout.findViewById(R.id.llLeadList)
                .setOnClickListener(v -> {
                    binding.drawerLayout.closeDrawers();
                    binding.navigationLayout.leadExpandLayout.collapse();
                    loadFragment(new LeadFragment());
                });
        binding.navigationLayout.leadExpandLayout.secondLayout.findViewById(R.id.llLeadRegister)
                .setOnClickListener(v -> {
                    startActivity(new Intent(this, CreateLeadActivity.class));
                });


        binding.navigationLayout.customerExpandLayout.setOnClickListener(view -> {
            //binding.drawerLayout.closeDrawers();
            binding.bottomNavigation.setSelectedItemId(R.id.customers);
            binding.navigationLayout.aboutLinear.setBackground(null);
            binding.navigationLayout.homeLinear.setBackground(null);
            binding.navigationLayout.loanLinear.setBackground(null);
            binding.navigationLayout.defaultsLinear.setBackground(null);
            binding.navigationLayout.leadExpandLayout.setBackground(null);
            binding.navigationLayout.customerExpandLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.button_bg));

            binding.navigationLayout.leadExpandLayout.collapse();
            if (binding.navigationLayout.customerExpandLayout.isExpanded()) {
                binding.navigationLayout.customerExpandLayout.collapse();
            } else {
                binding.navigationLayout.customerExpandLayout.expand();
            }
        });

        binding.navigationLayout.customerExpandLayout.secondLayout.findViewById(R.id.llCustomerList)
                .setOnClickListener(v -> {
                    binding.drawerLayout.closeDrawers();
                    binding.navigationLayout.customerExpandLayout.collapse();
                    loadFragment(new CustomerFragment());
                });
        binding.navigationLayout.customerExpandLayout.secondLayout.findViewById(R.id.llCustomerRegister)
                .setOnClickListener(v -> {
                    binding.drawerLayout.closeDrawers();
                    startActivity(new Intent(this, CreateCustomerActivity.class));
                });


        binding.navigationLayout.aboutLinear.setOnClickListener(view -> {
            binding.drawerLayout.closeDrawers();
            binding.navigationLayout.homeLinear.setBackground(null);
            binding.navigationLayout.loanLinear.setBackground(null);
            binding.navigationLayout.defaultsLinear.setBackground(null);
            binding.navigationLayout.leadExpandLayout.setBackground(null);
            binding.navigationLayout.customerExpandLayout.setBackground(null);
            binding.navigationLayout.aboutLinear.setBackground(ContextCompat.getDrawable(this, R.drawable.button_bg));
            binding.navigationLayout.leadExpandLayout.collapse();
        });

        binding.navigationLayout.linearLogout.setOnClickListener(v -> {
            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        dialog.dismiss();
                        sessionManager.logoutUser();
                        finishAffinity();
                        startActivity(new Intent(this, LoginActivity.class));
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            };
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setMessage("Do you really want to logout?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        });

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
        });

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
                    binding.navigationLayout.aboutLinear.setBackground(null);
                    binding.navigationLayout.loanLinear.setBackground(null);
                    binding.navigationLayout.defaultsLinear.setBackground(null);
                    binding.navigationLayout.leadExpandLayout.setBackground(null);
                    binding.navigationLayout.customerExpandLayout.setBackground(null);
                    binding.navigationLayout.homeLinear.setBackground(ContextCompat.getDrawable(this, R.drawable.button_bg));
                    binding.layoutHeader.toolbar.setVisibility(View.VISIBLE);
                    fragment = new HomeFragment();
                    loadFragment(fragment);

                    return true;
                case R.id.loan:
                    binding.navigationLayout.aboutLinear.setBackground(null);
                    binding.navigationLayout.homeLinear.setBackground(null);
                    binding.navigationLayout.defaultsLinear.setBackground(null);
                    binding.navigationLayout.leadExpandLayout.setBackground(null);
                    binding.navigationLayout.customerExpandLayout.setBackground(null);
                    binding.navigationLayout.loanLinear.setBackground(ContextCompat.getDrawable(this, R.drawable.button_bg));
                    binding.layoutHeader.toolbar.setVisibility(View.GONE);
                    fragment = new LoanFragment();
                    loadFragment(fragment);

                    return true;
                case R.id.defaults:
                    binding.navigationLayout.aboutLinear.setBackground(null);
                    binding.navigationLayout.homeLinear.setBackground(null);
                    binding.navigationLayout.loanLinear.setBackground(null);
                    binding.navigationLayout.leadExpandLayout.setBackground(null);
                    binding.navigationLayout.customerExpandLayout.setBackground(null);
                    binding.navigationLayout.defaultsLinear.setBackground(ContextCompat.getDrawable(this, R.drawable.button_bg));
                    binding.layoutHeader.toolbar.setVisibility(View.GONE);
                    fragment = new LeadFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.customers:
                    binding.navigationLayout.aboutLinear.setBackground(null);
                    binding.navigationLayout.homeLinear.setBackground(null);
                    binding.navigationLayout.loanLinear.setBackground(null);
                    binding.navigationLayout.defaultsLinear.setBackground(null);
                    binding.navigationLayout.leadExpandLayout.setBackground(null);
                    binding.navigationLayout.customerExpandLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.button_bg));
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