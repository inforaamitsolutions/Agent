package com.codeclinic.agent.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codeclinic.agent.R;
import com.codeclinic.agent.adapter.CustomerListAdapter;
import com.codeclinic.agent.adapter.DefaultListAdapter;
import com.codeclinic.agent.databinding.FragmentDefaultBinding;

public class DefaultFragment extends Fragment {

    FragmentDefaultBinding binding;
    public DefaultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_default, container, false);
        binding.headerLayout.imgBack.setVisibility(View.VISIBLE);
        binding.headerLayout.txtHeading.setText("Defaults");
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(new DefaultListAdapter(getContext()));
        return binding.getRoot();
    }
}