package com.codeclinic.agent.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.codeclinic.agent.R;
import com.codeclinic.agent.adapter.FAQListAdapter;
import com.codeclinic.agent.databinding.ActivityFAQBinding;

import java.util.ArrayList;
import java.util.List;

public class FAQActivity extends AppCompatActivity {
ActivityFAQBinding binding;
List<String> heading = new ArrayList<>();
List<String> description = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_f_a_q);
        setSupportActionBar(binding.headerLayout.toolbar);
        binding.headerLayout.txtHeading.setText("MobiDrinks FAQs");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

       // binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        for (int i=0;i<20;i++){
            heading.add(String.valueOf(i));
            description.add(String.valueOf(i));
        }
        binding.recyclerView.setAdapter(new FAQListAdapter(this,heading,description));

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}