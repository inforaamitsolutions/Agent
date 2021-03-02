package com.codeclinic.agent.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.codeclinic.agent.R;
import com.codeclinic.agent.adapter.InteractionAdapter;
import com.codeclinic.agent.databinding.ActivityInteractionBinding;

public class InteractionActivity extends AppCompatActivity {
ActivityInteractionBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_interaction);
        setSupportActionBar(binding.headerLayout.toolbar);
        binding.headerLayout.txtHeading.setText("Interaction");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new InteractionAdapter(this));
           binding.btnAddInteraction.setOnClickListener(view -> {
               startActivity(new Intent(this,AddInteractionActivity.class));
               finish();
           });
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