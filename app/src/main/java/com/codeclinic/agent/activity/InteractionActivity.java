package com.codeclinic.agent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.adapter.InteractionAdapter;
import com.codeclinic.agent.databinding.ActivityInteractionBinding;
import com.codeclinic.agent.model.leadInfo.LeadInteractionHistoryModel;
import com.codeclinic.agent.retrofit.RestClass;
import com.codeclinic.agent.utils.SessionManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.codeclinic.agent.utils.Constants.CustomerID;
import static com.codeclinic.agent.utils.SessionManager.sessionManager;

public class InteractionActivity extends AppCompatActivity {
    ActivityInteractionBinding binding;
    CompositeDisposable disposable = new CompositeDisposable();

    String customerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_interaction);
        setSupportActionBar(binding.headerLayout.toolbar);
        binding.headerLayout.txtHeading.setText("Interaction");

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);


        customerID = getIntent().getStringExtra(CustomerID);

        binding.headerLayout.imgBack.setVisibility(View.VISIBLE);
        binding.headerLayout.imgBack.setOnClickListener(v -> {
            finish();
        });

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));


        binding.btnAddInteraction.setOnClickListener(view -> {
            startActivity(new Intent(this, AddInteractionActivity.class));
            finish();
        });

        callInteractionsHistory();
    }

    private void callInteractionsHistory() {
        binding.loadingView.loader.setVisibility(View.VISIBLE);
        disposable.add(RestClass.getClient().LEAD_INTERACTION_HISTORY_MODEL_SINGLE(sessionManager.getTokenDetails().get(SessionManager.AccessToken),
                "MFS000125")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<LeadInteractionHistoryModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull LeadInteractionHistoryModel response) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                        if (response.getBody() != null) {
                            binding.recyclerView.setAdapter(new InteractionAdapter(response.getBody(), InteractionActivity.this));
                        } else {
                            Toast.makeText(InteractionActivity.this, " " + response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                        Toast.makeText(InteractionActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}