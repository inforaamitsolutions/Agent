package com.codeclinic.agent.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.ActivityCreateCustomerBinding;

public class CreateCustomerActivity extends AppCompatActivity {
ActivityCreateCustomerBinding binding;
    String[] products = { "MobiDrinks"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_create_customer);
        binding.headerLayout.imgBack.setVisibility(View.VISIBLE);
        binding.headerLayout.txtHeading.setText("Create Customer");

        binding.headerLayout.imgBack.setOnClickListener(view -> {
            onBackPressed();

        });
        ArrayAdapter ad2
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                products);
        binding.productSpinner.setAdapter(ad2);

        binding.linearCustomers.setOnClickListener(view -> {
            binding.imgDropDown2.setVisibility(View.GONE);
            binding.imgDropDown1.setVisibility(View.VISIBLE);

            binding.linearCustomers.setBackgroundColor(getResources().getColor(R.color.black));
            binding.txtCustomer.setTextColor(getResources().getColor(R.color.white));
            binding.txtDistributors.setTextColor(getResources().getColor(R.color.black));
            binding.linearDistributors.setBackgroundColor(getResources().getColor(R.color.white));

        });

        binding.linearDistributors.setOnClickListener(view -> {
            binding.imgDropDown2.setVisibility(View.VISIBLE);
            binding.imgDropDown1.setVisibility(View.GONE);

            binding.linearCustomers.setBackgroundColor(getResources().getColor(R.color.white));
            binding.txtCustomer.setTextColor(getResources().getColor(R.color.black));
            binding.txtDistributors.setTextColor(getResources().getColor(R.color.white));
            binding.linearDistributors.setBackgroundColor(getResources().getColor(R.color.black));
        });

        binding.btnNext1.setOnClickListener(view -> {
            binding.btnNext1.setVisibility(View.GONE);
            binding.linear1.setVisibility(View.GONE);
            binding.linear2.setVisibility(View.VISIBLE);
        });

        binding.btnNext2.setOnClickListener(view -> {
            binding.btnNext2.setVisibility(View.GONE);
            binding.btnNext3.setVisibility(View.VISIBLE);
            binding.txtLabel.setText("2.Business Location");
            binding.edtLocation.setVisibility(View.VISIBLE);
            binding.radioGroup.setVisibility(View.GONE);
        });

        binding.btnNext3.setOnClickListener(view -> {
                  startActivity(new Intent(this,AddCustomerActivity.class));
        });
    }
}