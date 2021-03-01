package com.codeclinic.agent.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.ActivityAddCustomerBinding;


public class AddCustomerActivity extends AppCompatActivity {
ActivityAddCustomerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_customer);
        binding.headerLayout.imgBack.setVisibility(View.VISIBLE);
        binding.headerLayout.txtHeading.setText("Add Customer");

        binding.headerLayout.imgBack.setOnClickListener(view -> {
            onBackPressed();
        });
    }
}