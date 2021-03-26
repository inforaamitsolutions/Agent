package com.codeclinic.agent.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.codeclinic.agent.R;
import com.codeclinic.agent.activity.CreateCustomerActivity;
import com.codeclinic.agent.activity.MainActivity;
import com.codeclinic.agent.adapter.CustomerListAdapter;
import com.codeclinic.agent.databinding.FragmentCustomerBinding;


public class CustomerFragment extends Fragment {
    FragmentCustomerBinding binding;
    public CustomerFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_customer, container, false);
        binding.headerLayout.imgBack.setVisibility(View.VISIBLE);
        binding.headerLayout.txtHeading.setText("Customers");
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(new CustomerListAdapter(getContext()));
        binding.headerLayout.imgBack.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), MainActivity.class));
            ((Activity) getContext()).finish();
        });

        binding.btnAddCustomer.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), CreateCustomerActivity.class));
        });
        return binding.getRoot();
    }
}