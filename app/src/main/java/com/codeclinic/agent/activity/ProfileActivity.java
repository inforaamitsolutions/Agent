package com.codeclinic.agent.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.ActivityProfileBinding;
import com.codeclinic.agent.model.customerInfo.CustomerBioDataModel;
import com.codeclinic.agent.model.customerInfo.CustomerBusinessDataModel;
import com.codeclinic.agent.model.customerInfo.CustomerModel;
import com.codeclinic.agent.retrofit.RestClass;
import com.codeclinic.agent.utils.SessionManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.codeclinic.agent.utils.Constants.CustomerID;
import static com.codeclinic.agent.utils.SessionManager.sessionManager;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    CompositeDisposable disposable = new CompositeDisposable();
    String customerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        customerID = getIntent().getStringExtra(CustomerID);


        binding.imgBack.setOnClickListener(view -> {
            onBackPressed();
        });
        binding.layoutInteraction.setOnClickListener(view -> {
            startActivity(new Intent(this, InteractionActivity.class));
        });


        binding.cardBusiness.setOnClickListener(v -> {
            if (binding.expandedBusiness.isExpanded()) {
                binding.expandedBusiness.collapse();
            } else {
                binding.expandedBusiness.expand();
            }
        });

        binding.cardInteraction.setOnClickListener(v -> {
            startActivity(new Intent(this, InteractionActivity.class)
                    .putExtra(CustomerID, customerID));
        });

        binding.cardLoanAccounts.setOnClickListener(v -> {
            startActivity(new Intent(this, CustomerLoanAccountsActivity.class)
                    .putExtra(CustomerID, customerID));
        });

        binding.cardBusinessDataUpdate.setOnClickListener(v -> {
            startActivity(new Intent(this, BusinessDataUpdateActivity.class));
        });


        callCustomerInfoAPI();
    }


    private void callCustomerInfoAPI() {
        binding.loadingView.loader.setVisibility(View.VISIBLE);
        Log.i("customerID", customerID + " ");
        disposable.add(RestClass.getClient().FETCH_CUSTOMER_INFO_MODEL_SINGLE(
                sessionManager.getTokenDetails().get(SessionManager.AccessToken), customerID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CustomerModel>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(@NonNull CustomerModel response) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                        if (response.getCustomerInfo().getCustomerInfoPages().get(0).getCustomerBioData() != null) {
                            CustomerBioDataModel customerBioData = response.getCustomerInfo().getCustomerInfoPages().get(0).getCustomerBioData();
                            binding.tvName.setText(customerBioData.getCustomerName() + "");
                            binding.tvPhone.setText(customerBioData.getMobileNumber() + "");
                            binding.tvCallNumber.setText(customerBioData.getMobileNumber() + "");
                            binding.tvIdNumber.setText(customerBioData.getIdNumber() + "");
                            binding.tvAge.setText(customerBioData.getCustomerAge() + "");
                            binding.tvGender.setText(customerBioData.getGender() + "");
                            binding.tvDOB.setText(customerBioData.getDateOfBirth() + "");
                        } else {
                            Toast.makeText(ProfileActivity.this, "No Customer Bio Data Available", Toast.LENGTH_SHORT).show();
                        }
                        if (response.getCustomerInfo().getCustomerInfoPages().get(0).getCustomerBusinessData() != null) {
                            CustomerBusinessDataModel customerBusinessData = response.getCustomerInfo().getCustomerInfoPages().get(0).getCustomerBusinessData();
                            binding.tvBusinessName.setText(customerBusinessData.getBusinessName() + "");
                            binding.tvBusinessSegment.setText(customerBusinessData.getBusinessSegment() + "");
                            binding.tvCategory.setText(customerBusinessData.getBusinessCategory() + "");
                            binding.tvBusinessType.setText(customerBusinessData.getBusinessType() + "");
                            binding.tvProducts.setText(customerBusinessData.getBusinessProducts() + "");
                            binding.tvZone.setText(customerBusinessData.getBusinessZone() + "");
                            binding.tvMarket.setText(customerBusinessData.getBusinessMarket() + "");
                            binding.tvDirection.setText(customerBusinessData.getBusinessDirection() + "");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                        Toast.makeText(ProfileActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}