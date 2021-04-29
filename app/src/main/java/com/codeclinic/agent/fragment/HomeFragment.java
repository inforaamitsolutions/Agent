package com.codeclinic.agent.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codeclinic.agent.R;
import com.codeclinic.agent.adapter.PerformanceAttributesListAdapter;
import com.codeclinic.agent.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {
    public FragmentHomeBinding binding;
    String[] period = {"Month", "Day", "week"};

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        binding.recyclerView.setNestedScrollingEnabled(false);


        ArrayAdapter<String> ad = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, period);
        binding.periodSpinner.setAdapter(ad);


        binding.recyclerView.setAdapter(new PerformanceAttributesListAdapter(getActivity(), binding.recyclerView));


        return binding.getRoot();
    }

}