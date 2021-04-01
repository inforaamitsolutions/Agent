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
import com.codeclinic.agent.activity.CreateLeadActivity;
import com.codeclinic.agent.activity.MainActivity;
import com.codeclinic.agent.adapter.DefaultListAdapter;
import com.codeclinic.agent.databinding.FragmentLeadBinding;

public class LeadFragment extends Fragment {

    FragmentLeadBinding binding;

    public LeadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lead, container, false);
        binding.headerLayout.imgBack.setVisibility(View.VISIBLE);
        binding.headerLayout.txtHeading.setText("Defaults");
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(new DefaultListAdapter(getContext()));
        binding.headerLayout.imgBack.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), MainActivity.class));
            ((Activity) getContext()).finish();
        });
        binding.btnAddLead.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), CreateLeadActivity.class));
        });
        return binding.getRoot();
    }
}