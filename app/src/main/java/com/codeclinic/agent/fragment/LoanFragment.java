package com.codeclinic.agent.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.codeclinic.agent.MainViewModel;
import com.codeclinic.agent.R;
import com.codeclinic.agent.adapter.LoanListAdapter;
import com.codeclinic.agent.databinding.FragmentLoanBinding;


public class LoanFragment extends Fragment {

    public FragmentLoanBinding binding;
    private MainViewModel viewModel;

    public LoanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_loan, container, false);


        binding.headerLayout.txtHeading.setText("Loans");


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(new LoanListAdapter(getContext()));


        return binding.getRoot();
    }
}