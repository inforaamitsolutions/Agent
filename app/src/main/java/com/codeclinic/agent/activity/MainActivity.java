package com.codeclinic.agent.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.codeclinic.agent.MainViewModel;
import com.codeclinic.agent.R;
import com.codeclinic.agent.adapter.ViewPagerAdapter;
import com.codeclinic.agent.databinding.ActivityMainBinding;
import com.codeclinic.agent.utils.Connection_Detector;
import com.codeclinic.agent.utils.LoadingDialog;
import com.codeclinic.agent.utils.SessionManager;
import com.google.android.material.tabs.TabLayout;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.codeclinic.agent.database.LocalDatabase.localDatabase;
import static com.codeclinic.agent.utils.Constants.AllInteraction;
import static com.codeclinic.agent.utils.Constants.CUSTOMER_FRAGMENT;
import static com.codeclinic.agent.utils.Constants.DueFollowUp;
import static com.codeclinic.agent.utils.Constants.HOME_FRAGMENT;
import static com.codeclinic.agent.utils.Constants.LEAD_FRAGMENT;
import static com.codeclinic.agent.utils.Constants.LOAN_FRAGMENT;
import static com.codeclinic.agent.utils.Constants.PromiseToPays;
import static com.codeclinic.agent.utils.SessionManager.sessionManager;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    ActivityMainBinding binding;

    MainViewModel viewModel;

    private final String[] tabTitles = new String[]{"Home", "Loan", "Lead", "Customer"};
    private final int[] tabImageResId = {R.drawable.ic_home, R.drawable.ic_loan, R.drawable.ic_lead, R.drawable.ic_customer};
    private ViewPagerAdapter adapter;
    LoadingDialog loadingDialog;

    CompositeDisposable disposable = new CompositeDisposable();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.layoutHeader.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        loadingDialog = new LoadingDialog(this);


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
            binding.navigationLayout.aboutLinear.setBackground(null);
            binding.navigationLayout.loanLinear.setBackground(null);
            binding.navigationLayout.leadExpandLayout.setBackground(null);
            binding.navigationLayout.customerExpandLayout.setBackground(null);
            binding.navigationLayout.interactionExpandLayout.setBackground(null);
            binding.navigationLayout.homeLinear.setBackground(ContextCompat.getDrawable(this, R.drawable.button_bg));

            binding.navigationLayout.leadExpandLayout.collapse();
            binding.navigationLayout.customerExpandLayout.collapse();
            binding.navigationLayout.interactionExpandLayout.collapse();
            binding.viewpager.setCurrentItem(0);
        });
        binding.navigationLayout.loanLinear.setOnClickListener(view -> {
            binding.drawerLayout.closeDrawers();
            binding.bottomNavigation.setSelectedItemId(R.id.loan);
            binding.navigationLayout.aboutLinear.setBackground(null);
            binding.navigationLayout.homeLinear.setBackground(null);
            binding.navigationLayout.leadExpandLayout.setBackground(null);
            binding.navigationLayout.customerExpandLayout.setBackground(null);
            binding.navigationLayout.interactionExpandLayout.setBackground(null);
            binding.navigationLayout.loanLinear.setBackground(ContextCompat.getDrawable(this, R.drawable.button_bg));
            binding.navigationLayout.leadExpandLayout.collapse();
            binding.navigationLayout.customerExpandLayout.collapse();
            binding.navigationLayout.interactionExpandLayout.collapse();

            binding.viewpager.setCurrentItem(1);
        });


        binding.navigationLayout.leadExpandLayout.setOnClickListener(view -> {
            //binding.drawerLayout.closeDrawers();
            binding.navigationLayout.aboutLinear.setBackground(null);
            binding.navigationLayout.homeLinear.setBackground(null);
            binding.navigationLayout.loanLinear.setBackground(null);
            binding.navigationLayout.customerExpandLayout.setBackground(null);
            binding.navigationLayout.interactionExpandLayout.setBackground(null);
            binding.navigationLayout.leadExpandLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.button_bg));
            // Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();

            binding.navigationLayout.customerExpandLayout.collapse();
            binding.navigationLayout.interactionExpandLayout.collapse();

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
                    binding.viewpager.setCurrentItem(2);
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
            binding.navigationLayout.leadExpandLayout.setBackground(null);
            binding.navigationLayout.interactionExpandLayout.setBackground(null);
            binding.navigationLayout.customerExpandLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.button_bg));

            binding.navigationLayout.leadExpandLayout.collapse();
            binding.navigationLayout.interactionExpandLayout.collapse();

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
                    binding.viewpager.setCurrentItem(3);
                });
        binding.navigationLayout.customerExpandLayout.secondLayout.findViewById(R.id.llCustomerRegister)
                .setOnClickListener(v -> {
                    binding.drawerLayout.closeDrawers();
                    startActivity(new Intent(this, CreateCustomerActivity.class));
                });


        binding.navigationLayout.interactionExpandLayout.setOnClickListener(view -> {
            //binding.drawerLayout.closeDrawers();
            binding.bottomNavigation.setSelectedItemId(R.id.customers);
            binding.navigationLayout.aboutLinear.setBackground(null);
            binding.navigationLayout.homeLinear.setBackground(null);
            binding.navigationLayout.loanLinear.setBackground(null);
            binding.navigationLayout.leadExpandLayout.setBackground(null);
            binding.navigationLayout.customerExpandLayout.setBackground(null);
            binding.navigationLayout.interactionExpandLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.button_bg));

            binding.navigationLayout.leadExpandLayout.collapse();
            binding.navigationLayout.customerExpandLayout.collapse();

            if (binding.navigationLayout.interactionExpandLayout.isExpanded()) {
                binding.navigationLayout.interactionExpandLayout.collapse();
            } else {
                binding.navigationLayout.interactionExpandLayout.expand();
            }
        });

        binding.navigationLayout.interactionExpandLayout.secondLayout.findViewById(R.id.llAllInteraction)
                .setOnClickListener(v -> {
                    binding.drawerLayout.closeDrawers();
                    binding.navigationLayout.interactionExpandLayout.collapse();
                    startActivity(new Intent(this, InteractionFiltersActivity.class)
                            .putExtra(AllInteraction, "1"));
                });
        binding.navigationLayout.interactionExpandLayout.secondLayout.findViewById(R.id.llFollowupDue)
                .setOnClickListener(v -> {
                    binding.drawerLayout.closeDrawers();
                    binding.navigationLayout.interactionExpandLayout.collapse();
                    startActivity(new Intent(this, InteractionFiltersActivity.class)
                            .putExtra(DueFollowUp, "1"));
                });

        binding.navigationLayout.interactionExpandLayout.secondLayout.findViewById(R.id.llPromisePay)
                .setOnClickListener(v -> {
                    binding.drawerLayout.closeDrawers();
                    binding.navigationLayout.interactionExpandLayout.collapse();
                    startActivity(new Intent(this, InteractionFiltersActivity.class)
                            .putExtra(PromiseToPays, "1"));
                });


        binding.navigationLayout.aboutLinear.setOnClickListener(view -> {
            binding.drawerLayout.closeDrawers();
            binding.navigationLayout.homeLinear.setBackground(null);
            binding.navigationLayout.loanLinear.setBackground(null);
            binding.navigationLayout.leadExpandLayout.setBackground(null);
            binding.navigationLayout.customerExpandLayout.setBackground(null);
            binding.navigationLayout.interactionExpandLayout.setBackground(null);
            binding.navigationLayout.aboutLinear.setBackground(ContextCompat.getDrawable(this, R.drawable.button_bg));
            binding.navigationLayout.leadExpandLayout.collapse();
            binding.navigationLayout.customerExpandLayout.collapse();
            binding.navigationLayout.interactionExpandLayout.collapse();
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


        viewModel.isSessionClear.observe(this, isClear -> {
            if (isClear != null) {
                Toast.makeText(this, "Please wait Session is refreshing", Toast.LENGTH_LONG).show();
                loadingDialog.showProgressDialog("");
                //viewModel.callRefreshToken();
            }
        });

        /*viewModel.refreshToken.observe(this, refresh -> {
            loadingDialog.hideProgressDialog();
            if (refresh != null) {
                if (refresh) {
                  *//*  finishAffinity();
                    startActivity(new Intent(this, MainActivity.class));*//*
                    sessionManager.logoutUser();
                    finishAffinity();
                    startActivity(new Intent(this, LoginActivity.class));
                }
            } *//*else {
                sessionManager.logoutUser();
                finishAffinity();
                startActivity(new Intent(this, LoginActivity.class));
            }*//*
        });*/

        viewModel.formFetchingComplete.observe(this, isComplete -> {
            loadingDialog.hideProgressDialog();
            if (isComplete != null) {
                Toast.makeText(this, " " + isComplete.getMessage(), Toast.LENGTH_LONG).show();
            }
            if (!viewModel.isViewPagerInitialized) {
                setupViewPager();
            }
        });


        disposable.add(Single.fromCallable(this::checkFormSavedInDB)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((isExist) -> {
                    if (!isExist) {
                        fetchFormsOnline();
                    } else {
                        setupViewPager();
                    }
                }));

    }

    private boolean checkFormSavedInDB() {
        return localDatabase.getDAO().isFormExists();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.submitForms:
                startActivity(new Intent(MainActivity.this, SubmitFormsActivity.class));
                return true;
            case R.id.syncData:
                fetchFormsOnline();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fetchFormsOnline() {
        if (Connection_Detector.isInternetAvailable(this)) {
            loadingDialog.showProgressDialog("Please wait we are fetching new forms");
            Toast.makeText(this, "Please wait we are fetching new forms", Toast.LENGTH_LONG).show();
            viewModel.callCustomerForm();
        } else {
            Toast.makeText(this, "please check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupViewPager() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new ViewPagerAdapter(fragmentManager);
        adapter.addFrag(viewModel.getFragment(HOME_FRAGMENT));
        adapter.addFrag(viewModel.getFragment(LOAN_FRAGMENT));
        adapter.addFrag(viewModel.getFragment(LEAD_FRAGMENT));
        adapter.addFrag(viewModel.getFragment(CUSTOMER_FRAGMENT));
        binding.viewpager.setAdapter(adapter);
        binding.viewpager.setOffscreenPageLimit(4);
        binding.tabLayout.setupWithViewPager(binding.viewpager);
        for (int i = 0; i < 4; i++) {
            TabLayout.Tab tab = binding.tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(getTabView(i));
            }
            if (i == 0) {
                if (tab != null) {
                    setOnSelectView(tab);
                }
            } else {
                if (tab != null) {
                    setUnSelectView(tab);
                }
            }
        }
        binding.tabLayout.addOnTabSelectedListener(this);
        viewModel.isViewPagerInitialized = true;
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(this).inflate(R.layout.custom_tab_view, null);
        ImageView img = v.findViewById(R.id.icon);
        TextView tv = v.findViewById(R.id.tv_item);
        img.setImageResource(tabImageResId[position]);
        tv.setText(tabTitles[position]);
        return v;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        setOnSelectView(tab);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        setUnSelectView(tab);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    public void setOnSelectView(TabLayout.Tab tab) {
        View selected = tab.getCustomView();
        ImageView icon = selected.findViewById(R.id.icon);
        TextView tv = selected.findViewById(R.id.tv_item);
        icon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        tv.setTextColor(getResources().getColor(R.color.colorPrimary));
        if (tab.getPosition() == 0) {
            binding.layoutHeader.toolbar.setVisibility(View.VISIBLE);
        } else {
            binding.layoutHeader.toolbar.setVisibility(View.GONE);
        }
    }

    public void setUnSelectView(TabLayout.Tab tab) {
        View selected = tab.getCustomView();
        ImageView icon = selected.findViewById(R.id.icon);
        TextView tv = selected.findViewById(R.id.tv_item);
        icon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.grayColor)));
        tv.setTextColor(getResources().getColor(R.color.grayColor));
    }

    @Override
    public void onBackPressed() {
        if (binding.viewpager.getCurrentItem() == 0) {
            super.onBackPressed();
            finish();
        } else {
            binding.viewpager.setCurrentItem(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
        disposable.dispose();
    }
}