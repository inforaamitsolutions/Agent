package com.codeclinic.agent.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.adapter.AttributeListAdapter;
import com.codeclinic.agent.databinding.ActivityLeadInfoBinding;
import com.codeclinic.agent.model.leadInfo.LeadInfoModel;
import com.codeclinic.agent.retrofit.RestClass;
import com.codeclinic.agent.utils.SessionManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.text.TextUtils.isEmpty;
import static com.codeclinic.agent.utils.Constants.CustomerID;
import static com.codeclinic.agent.utils.SessionManager.sessionManager;

public class LeadInfoActivity extends AppCompatActivity {
    CompositeDisposable disposable = new CompositeDisposable();
    String customerID, phoneNo;
    private ActivityLeadInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lead_info);

        binding.imgBack.setOnClickListener(v ->
        {
            finish();
        });

        customerID = getIntent().getStringExtra(CustomerID);

        binding.recyclerViewAttributes.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));


        binding.cardInteraction.setOnClickListener(v -> {
            startActivity(new Intent(this, InteractionActivity.class)
                    .putExtra(CustomerID, customerID));
        });

        binding.llCall.setOnClickListener(v -> {
            if (!isEmpty(phoneNo)) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNo));
                startActivity(intent);
            } else {
                Toast.makeText(this, "No phone number available", Toast.LENGTH_SHORT).show();
            }
        });

        callLeadInfoAPI();

    }

    private void callLeadInfoAPI() {
        binding.loadingView.loader.setVisibility(View.VISIBLE);
        disposable.add(RestClass.getClient().FETCH_LEAD_INFO_MODEL_SINGLE(sessionManager.getTokenDetails().get(SessionManager.AccessToken),
                customerID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<LeadInfoModel>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(@NonNull LeadInfoModel response) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                        if (response.getLeadInfo() != null) {
                            binding.tvCustomerID.setText(customerID);
                            binding.tvStatus.setText("Customer Status - " + response.getLeadInfo().getCustomerStatuses().get(0).getStatusName());
                            binding.tvCustomerName.setText(response.getLeadInfo().getFullName());
                            binding.tvPhone.setText(response.getLeadInfo().getPhoneNumber());
                            binding.tvNumber.setText(response.getLeadInfo().getPhoneNumber() + "");
                            phoneNo = response.getLeadInfo().getPhoneNumber() + "";
                            if (response.getLeadInfo().getDocumentNumber() != null) {
                                binding.tvDocNumber.setText(response.getLeadInfo().getDocumentNumber());
                            } else {
                                binding.llDocumentNo.setVisibility(View.GONE);
                            }
                            if (response.getLeadInfo().getAttributeValues() != null) {
                                binding.recyclerViewAttributes.setAdapter(new AttributeListAdapter(response.getLeadInfo().getAttributeValues(), LeadInfoActivity.this));
                            }
                        } else {
                            Toast.makeText(LeadInfoActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                        Toast.makeText(LeadInfoActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}