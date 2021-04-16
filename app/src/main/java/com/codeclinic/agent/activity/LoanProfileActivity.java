package com.codeclinic.agent.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.ActivityLoanProfileBinding;
import com.codeclinic.agent.model.LoanAccountListModel;
import com.codeclinic.agent.model.LoanAccountsByNoModel;
import com.codeclinic.agent.retrofit.RestClass;
import com.codeclinic.agent.utils.SessionManager;
import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.codeclinic.agent.utils.Constants.LoanNumber;
import static com.codeclinic.agent.utils.SessionManager.sessionManager;

public class LoanProfileActivity extends AppCompatActivity {

    private final CompositeDisposable disposable = new CompositeDisposable();
    ActivityLoanProfileBinding binding;
    String loanNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_loan_profile);

        loanNumber = getIntent().getStringExtra(LoanNumber);

        binding.recyclerViewInstallments.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.recyclerViewLoanStatus.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.recyclerViewTransactions.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.recyclerViewTimeLineStatus.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        binding.recyclerViewInstallments.setHasFixedSize(true);
        binding.recyclerViewLoanStatus.setHasFixedSize(true);
        binding.recyclerViewTransactions.setHasFixedSize(true);
        binding.recyclerViewTimeLineStatus.setHasFixedSize(true);


        fetchLoanAccountsByLoanNoAPI();


    }

    public void fetchLoanAccountsByLoanNoAPI() {
        binding.loadingView.loader.setVisibility(View.VISIBLE);
        disposable.add(RestClass.getClient().GET_LOAN_ACCOUNT_BY_NUMBER_CALL(
                sessionManager.getTokenDetails().get(SessionManager.AccessToken),
                loanNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<LoanAccountsByNoModel>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull LoanAccountsByNoModel response) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                        Log.i("responseLoan", new Gson().toJson(response) + " ");
                        if (response.getLoanAccountsByNoDetail() != null) {
                            LoanAccountListModel loanAccount = response.getLoanAccountsByNoDetail().getAccount();
                            binding.tvName.setText(loanAccount.getCustomerName() + "");
                            binding.tvCustomerID.setText(loanAccount.getCustomerId() + "");
                            binding.tvProductName.setText(loanAccount.getProductName() + "");
                            binding.tvSupplierName.setText(loanAccount.getPartnerId() + "");
                            binding.tvCustomerStatus.setText("Customer Status - " + loanAccount.getStatus());
                            binding.tvCallNumber.setText("" + loanAccount.getCustomerPhoneNumber());
                            //binding.recyclerViewInstallments.setAdapter();
                        } else {
                            Toast.makeText(LoanProfileActivity.this, " " + response.getHttpStatus(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                        Log.i("loanAccountsByNo", " " + e.getMessage());
                        Toast.makeText(LoanProfileActivity.this, " Server Error", Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}