package com.codeclinic.agent.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.ActivityLeadInfoBinding;
import com.codeclinic.agent.model.leadInfo.LeadInfoModel;
import com.codeclinic.agent.retrofit.RestClass;
import com.codeclinic.agent.utils.SessionManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.codeclinic.agent.utils.Constants.CustomerID;
import static com.codeclinic.agent.utils.SessionManager.sessionManager;

public class LeadInfoActivity extends AppCompatActivity {
    CompositeDisposable disposable = new CompositeDisposable();
    String customerID;
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

        callLeadInfoAPI();

    }

    private void callLeadInfoAPI() {
        binding.loadingView.loader.setVisibility(View.VISIBLE);
        disposable.add(RestClass.getClient().FETCH_LEAD_INFO_MODEL_SINGLE(sessionManager.getTokenDetails().get(SessionManager.AccessToken),
                customerID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<LeadInfoModel>() {
                    @Override
                    public void onSuccess(@NonNull LeadInfoModel leadInfoModel) {
                        binding.loadingView.loader.setVisibility(View.GONE);
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