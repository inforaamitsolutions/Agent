package com.codeclinic.agent.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.codeclinic.agent.R;
import com.codeclinic.agent.adapter.InteractionAdapter;
import com.codeclinic.agent.databinding.ActivityCustomerInfoBinding;

public class CustomerInfoActivity extends AppCompatActivity {
ActivityCustomerInfoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_customer_info);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new InteractionAdapter(this));
        binding.imgBack.setOnClickListener(view -> {
              onBackPressed();
        });
        binding.btnAddInteraction.setOnClickListener(view -> {
            startActivity(new Intent(CustomerInfoActivity.this,AddInteractionActivity.class));

        });
    }
}