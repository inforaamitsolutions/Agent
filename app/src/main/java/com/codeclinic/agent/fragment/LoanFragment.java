package com.codeclinic.agent.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codeclinic.agent.R;
import com.codeclinic.agent.activity.MainActivity;
import com.codeclinic.agent.adapter.LoanListAdapter;
import com.codeclinic.agent.databinding.FragmentLoanBinding;


public class LoanFragment extends Fragment {

public FragmentLoanBinding binding;
    public LoanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_loan, container, false);
        binding.headerLayout.txtHeading.setText("Loans");
        binding.headerLayout.imgBack.setVisibility(View.VISIBLE);
        binding.loanRecyclerView.setHasFixedSize(true);
        binding.loanRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.loanRecyclerView.setAdapter(new LoanListAdapter(getContext()));

        binding.headerLayout.imgBack.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), MainActivity.class));
            ((Activity)getContext()).finish();
        });
        return binding.getRoot();
    }
}