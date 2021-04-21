package com.codeclinic.agent.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.adapter.AllInteractionsListAdapter;
import com.codeclinic.agent.databinding.ActivityInteractionFiltersBinding;
import com.codeclinic.agent.model.InteractionCategoryListModel;
import com.codeclinic.agent.model.InteractionCategoryModel;
import com.codeclinic.agent.model.InteractionModel;
import com.codeclinic.agent.retrofit.RestClass;
import com.codeclinic.agent.utils.CommonMethods;
import com.codeclinic.agent.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    String[] type = {"CALL", "VISIT", "SMS"};

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_interaction_filters);
        setSupportActionBar(binding.headerLayout.toolbar);
        binding.headerLayout.imgBack.setVisibility(View.VISIBLE);

        isFilterOption = getIntent().hasExtra(AllInteraction);
        isDueFollowUp = getIntent().hasExtra(DueFollowUp);
        isPromiseToPay = getIntent().hasExtra(PromiseToPays);

        binding.headerLayout.imgBack.setOnClickListener(v -> {
            finish();
        });

        if (isFilterOption) {
            binding.headerLayout.txtHeading.setText("All Interactions");
            binding.llFilters.setVisibility(View.VISIBLE);
            binding.llDate.setVisibility(View.GONE);
        } else if (isDueFollowUp) {
            binding.headerLayout.txtHeading.setText("Due Followup");
        } else {
            binding.headerLayout.txtHeading.setText("Promise to pay");
        }

        binding.searchParentView.llSearchParent.setOnClickListener(view -> {
            if (binding.searchChildView.llSearchChild.getVisibility() == View.VISIBLE) {
                binding.searchChildView.llSearchChild.setVisibility(View.GONE);
            } else {
                binding.searchChildView.llSearchChild.setVisibility(View.VISIBLE);
            }
        });

        List<String> defaultSearches = new ArrayList<String>();
        defaultSearches.add("--Select--");
        defaultSearches.add("Created By");
        defaultSearches.add("Customer ID");
        defaultSearches.add("Interaction Id");
        defaultSearches.add("Interaction Category");
        defaultSearches.add("Interaction Type");

        ArrayAdapter<String> defaultAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_view, defaultSearches);
        binding.searchChildView.spDefaultSearch.setAdapter(defaultAdapter);
        binding.searchChildView.spDefaultSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 4) {
                    binding.searchChildView.llInteractionCategory.setVisibility(View.VISIBLE);
                    binding.searchChildView.llInteractionType.setVisibility(View.GONE);
                    binding.searchChildView.llDefaultSearchInputs.setVisibility(View.GONE);
                } else if (i == 5) {
                    binding.searchChildView.llInteractionCategory.setVisibility(View.GONE);
                    binding.searchChildView.llInteractionType.setVisibility(View.VISIBLE);
                    binding.searchChildView.llDefaultSearchInputs.setVisibility(View.GONE);
                } else {
                    binding.searchChildView.llInteractionCategory.setVisibility(View.GONE);
                    binding.searchChildView.llInteractionType.setVisibility(View.GONE);
                    binding.searchChildView.llDefaultSearchInputs.setVisibility(View.VISIBLE);
                    if (i == 1) {
                        binding.searchChildView.tvDefaultSearchTitle.setText("Created By");
                    } else if (i == 2) {
                        binding.searchChildView.tvDefaultSearchTitle.setText("Customer ID");
                    } else if (i == 3) {
                        binding.searchChildView.tvDefaultSearchTitle.setText("Interaction ID");
                    } else {
                        binding.searchChildView.llDefaultSearchInputs.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, type);
        binding.searchChildView.spInteractionType.setAdapter(ad);


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.recyclerView.setHasFixedSize(true);


        binding.tvDate.setOnClickListener(v -> {
            CommonMethods.datePicker(binding.tvDate, this);
        });

        binding.btnFind.setOnClickListener(v -> {
            if (isEmpty(binding.tvDate.getText().toString())) {
                Toast.makeText(this, "Please select date", Toast.LENGTH_SHORT).show();
            } else {
                callInteractions();
            }
        });

        binding.searchChildView.btnSearch.setOnClickListener(v ->
        {
            if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "Please select option", Toast.LENGTH_SHORT).show();
            } else if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "Please select filter option", Toast.LENGTH_SHORT).show();
            } else if (binding.searchChildView.spDefaultSearch.getSelectedItemPosition() < 4
                    && isEmpty(binding.searchChildView.edtSearch.getText().toString())) {
                Toast.makeText(this, "Please enter search details", Toast.LENGTH_SHORT).show();
            } else {
                callInteractions();
            }

        });


        getInteractionCategoriesAPI();


    }

    private void getInteractionCategoriesAPI() {
        binding.loadingView.loader.setVisibility(View.VISIBLE);
        disposable.add(RestClass.getClient().INTERACTION_CATEGORY_MODEL_SINGLE(sessionManager.getTokenDetails().get(SessionManager.AccessToken))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<InteractionCategoryModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull InteractionCategoryModel response) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                        if (response.getInteractionCategoryList() != null) {
                            ArrayAdapter<InteractionCategoryListModel> categoryAdapter = new ArrayAdapter<>(InteractionFiltersActivity.this, R.layout.spinner_item_view, response.getInteractionCategoryList());
                            binding.searchChildView.spInteractionCategory.setAdapter(categoryAdapter);
                        } else {
                            Toast.makeText(InteractionFiltersActivity.this, " " + response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                    }
                }));
    }

    private void callInteractions() {
        binding.loadingView.loader.setVisibility(View.VISIBLE);
        String token = sessionManager.getTokenDetails().get(SessionManager.AccessToken);
        String date = binding.tvDate.getText().toString();
        date = date.replace("-", "/");

        Single<InteractionModel> interactionAPI;

        if (isDueFollowUp) {
            interactionAPI = RestClass.getClient().FETCH_DUE_FOLLOWUP_MODEL_SINGLE(token, date);
        } else if (isPromiseToPay) {
            interactionAPI = RestClass.getClient().FETCH_PROMISE_TO_PAY_MODEL_SINGLE(token, date);
        } else {
            String data = binding.searchChildView.edtSearch.getText().toString();
            int pos = binding.searchChildView.spDefaultSearch.getSelectedItemPosition();
            Map<String, String> paramsMap = new HashMap<>();
            if (pos == 1) {
                paramsMap.put("createdBy", data);
            } else if (pos == 2) {
                paramsMap.put("customerId", data);
            } else if (pos == 3) {
                paramsMap.put("interactionId", data);
            } else if (pos == 4) {
                paramsMap.put("interactionCategory", binding.searchChildView.spInteractionCategory.getSelectedItem().toString());
            } else {
                paramsMap.put("interactionType", binding.searchChildView.spInteractionType.getSelectedItem().toString());
            }
            interactionAPI = RestClass.getClient().FETCH_FILTERS_INTERACTIONS_MODEL_SINGLE(token, paramsMap);
        }


        disposable.add(interactionAPI
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<InteractionModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull InteractionModel response) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                        if (response.getInteractionList() != null) {
                            binding.recyclerView.setAdapter(new AllInteractionsListAdapter(response.getInteractionList(), InteractionFiltersActivity.this));
                            if (isFilterOption) {
                                if (binding.searchChildView.llSearchChild.getVisibility() == View.VISIBLE) {
                                    binding.searchChildView.llSearchChild.setVisibility(View.GONE);
                                } else {
                                    binding.searchChildView.llSearchChild.setVisibility(View.VISIBLE);
                                }
                            }
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