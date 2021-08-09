package com.codeclinic.agent.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.MainViewModel;
import com.codeclinic.agent.R;
import com.codeclinic.agent.activity.CreateCustomerActivity;
import com.codeclinic.agent.activity.CreateLeadActivity;
import com.codeclinic.agent.adapter.PerformanceAttributesListAdapter;
import com.codeclinic.agent.databinding.FragmentHomeBinding;
import com.codeclinic.agent.utils.LoadingDialog;
import com.codeclinic.agent.utils.SessionManager;

import java.util.HashMap;
import java.util.Map;

import static com.codeclinic.agent.utils.SessionManager.sessionManager;


public class HomeFragment extends Fragment {
    public FragmentHomeBinding binding;
    String[] period = {"Month", "Day", "week"};
    LoadingDialog loadingDialog;
    private MainViewModel viewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);

        loadingDialog = new LoadingDialog(getActivity());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        binding.recyclerView.setNestedScrollingEnabled(false);


        ArrayAdapter<String> ad = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, period);
        binding.periodSpinner.setAdapter(ad);

        viewModel.performanceData.observe(getActivity(), performanceModel -> {
            loadingDialog.hideProgressDialog();
            if (performanceModel != null) {
                if (performanceModel.getPerformanceBody() != null) {
                    binding.tvPerformancePercentage.setText(performanceModel.getPerformanceBody().getOverallPerformance() + "%");

                    if (performanceModel.getPerformanceBody().getPerformanceAttributesList() != null) {
                        binding.recyclerView.setAdapter(new PerformanceAttributesListAdapter(performanceModel.getPerformanceBody().getPerformanceAttributesList(), getActivity(), binding.recyclerView));
                    } else {
                        Toast.makeText(getActivity(), " " + performanceModel.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), " " + performanceModel.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            viewModel.getAllRequiredFiltersData();
        });

        binding.periodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

                Map<String, String> data = new HashMap<>();
                if (i == 0) {
                    data.put("period", "month");
                } else if (i == 1) {
                    data.put("period", "day");
                } else {
                    data.put("period", "week");
                }
                data.put("staffId", sessionManager.getUserDetails().get(SessionManager.UserID));

                loadingDialog.showProgressDialog("");
                viewModel.getPerformanceDataAPI(data);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.cardCreateCustomer.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), CreateCustomerActivity.class));
        });

        binding.cardCreateLead.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), CreateLeadActivity.class));
        });


        return binding.getRoot();
    }

}