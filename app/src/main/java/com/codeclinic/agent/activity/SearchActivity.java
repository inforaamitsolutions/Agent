package com.codeclinic.agent.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.WindowManager;

import com.codeclinic.agent.R;
import com.codeclinic.agent.adapter.RecentSearchListAdapter;
import com.codeclinic.agent.adapter.TopSearchListAdapter;
import com.codeclinic.agent.databinding.ActivitySeachBinding;

public class SearchActivity extends AppCompatActivity {
ActivitySeachBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_seach);
       binding.imgBack.setOnClickListener(view -> {
           onBackPressed();
       });
        binding.recentRecyclerView.setHasFixedSize(true);
        binding.recentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.TopRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.recentRecyclerView.setAdapter(new RecentSearchListAdapter(this));
        binding.TopRecyclerView.setAdapter(new TopSearchListAdapter(this));
    }
}