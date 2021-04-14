package com.codeclinic.agent.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.adapter.LoanAccountsListAdapter;
import com.codeclinic.agent.databinding.ActivityCustomerLoanAccountsBinding;
import com.codeclinic.agent.model.LoanAccountsModel;
import com.codeclinic.agent.retrofit.RestClass;
import com.codeclinic.agent.utils.CommonMethods;
import com.codeclinic.agent.utils.SessionManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.text.TextUtils.isEmpty;
import static com.codeclinic.agent.utils.Constants.CustomerID;
import static com.codeclinic.agent.utils.SessionManager.sessionManager;

public class CustomerLoanAccountsActivity extends AppCompatActivity {

    private final CompositeDisposable disposable = new CompositeDisposable();
    String customerID;
    private ActivityCustomerLoanAccountsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_loan_accounts);

        customerID = getIntent().getStringExtra(CustomerID);

        binding.imgBack.setOnClickListener(v -> {
            finish();
        });

        binding.recyclerViewLoans.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        binding.tvFromDate.setOnClickListener(v -> {
            CommonMethods.datePicker(binding.tvFromDate, this);

        });

        binding.tvToDate.setOnClickListener(v -> {
            CommonMethods.datePicker(binding.tvToDate, this);

        });

        binding.btnFind.setOnClickListener(v -> {
            if (isEmpty(binding.tvFromDate.getText().toString())) {
                Toast.makeText(this, "Please select from date", Toast.LENGTH_SHORT).show();
            } else if (isEmpty(binding.tvToDate.getText().toString())) {
                Toast.makeText(this, "Please select from date", Toast.LENGTH_SHORT).show();
            } else {
                fetchLoanAccountsAPI();
            }
        });
    }

    private void fetchLoanAccountsAPI() {
        binding.loadingView.loader.setVisibility(View.VISIBLE);
        String fromDate = binding.tvFromDate.getText().toString();
        //fromDate = fromDate.replaceAll("-", "");

        String toDate = binding.tvToDate.getText().toString();
        //toDate = toDate.replaceAll("-", "");

        Log.i("customerId", customerID + "");
        disposable.add(RestClass.getClient().FETCH_CUSTOMER_LOAN_ACCOUNTS_MODEL_SINGLE(
                sessionManager.getTokenDetails().get(SessionManager.AccessToken),
                "MFS000317",
                fromDate,
                toDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<LoanAccountsModel>() {
                    @Override
                    public void onSuccess(@NonNull LoanAccountsModel response) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                        if (response.getLoanAccountList() != null) {
                            binding.recyclerViewLoans.setAdapter(new LoanAccountsListAdapter(response.getLoanAccountList(), CustomerLoanAccountsActivity.this, binding.recyclerViewLoans));
                        } else {
                            Toast.makeText(CustomerLoanAccountsActivity.this, " " + response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                        Toast.makeText(CustomerLoanAccountsActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}