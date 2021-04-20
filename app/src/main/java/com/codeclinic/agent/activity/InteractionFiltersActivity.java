package com.codeclinic.agent.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.ActivityInteractionFiltersBinding;
import com.codeclinic.agent.model.InteractionModel;
import com.codeclinic.agent.retrofit.RestClass;
import com.codeclinic.agent.utils.SessionManager;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.text.TextUtils.isEmpty;
import static com.codeclinic.agent.utils.Constants.AllInteraction;
import static com.codeclinic.agent.utils.Constants.DueFollowUp;
import static com.codeclinic.agent.utils.Constants.PromiseToPays;
import static com.codeclinic.agent.utils.SessionManager.sessionManager;

public class InteractionFiltersActivity extends AppCompatActivity {

    ActivityInteractionFiltersBinding binding;
    boolean isFilterOption, isDueFollowUp, isPromiseToPay;
    CompositeDisposable disposable = new CompositeDisposable();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_interaction_filters);
        setSupportActionBar(binding.headerLayout.toolbar);

        isFilterOption = getIntent().hasExtra(AllInteraction);
        isDueFollowUp = getIntent().hasExtra(DueFollowUp);
        isPromiseToPay = getIntent().hasExtra(PromiseToPays);

        if (isFilterOption) {
            binding.headerLayout.txtHeading.setText("All Interactions");
            binding.llFilters.setVisibility(View.VISIBLE);
        } else if (isDueFollowUp) {
            binding.headerLayout.txtHeading.setText("Due Followup");
            binding.llDate.setVisibility(View.VISIBLE);
        } else {
            binding.headerLayout.txtHeading.setText("Promise to pay");
        }


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.recyclerView.setHasFixedSize(true);


        binding.btnFind.setOnClickListener(v -> {
            if (isEmpty(binding.tvDate.getText().toString())) {
                Toast.makeText(this, "Please select date", Toast.LENGTH_SHORT).show();
            } else {
                callInteractions();
            }
        });


    }

    private void callInteractions() {
        binding.loadingView.loader.setVisibility(View.VISIBLE);
        String token = sessionManager.getTokenDetails().get(SessionManager.AccessToken);
        String date = binding.tvDate.getText().toString();
        Single<InteractionModel> interactionAPI = isDueFollowUp ? RestClass.getClient().FETCH_DUE_FOLLOWUP_MODEL_SINGLE(token, date)
                : RestClass.getClient().FETCH_PROMISE_TO_PAY_MODEL_SINGLE(token, date);

        disposable.add(interactionAPI
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<InteractionModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull InteractionModel response) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                        if (response.getInteractionList() != null) {
                            //binding.recyclerView.setAdapter(new InteractionAdapter(response.getInteractionList(), InteractionFiltersActivity.this));
                        } else {
                            Toast.makeText(InteractionFiltersActivity.this, " " + response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                        Toast.makeText(InteractionFiltersActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}