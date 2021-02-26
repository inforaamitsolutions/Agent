package com.codeclinic.agent.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.ActivityAddInteractionBinding;

public class AddInteractionActivity extends AppCompatActivity {
ActivityAddInteractionBinding binding;
    String[] type = { "Promise to Pay", "Visit",
            "Call", "SMS"};
    String[] channel = { "USD"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_add_interaction);
        setSupportActionBar(binding.headerLayout.toolbar);
       binding.headerLayout.txtHeading.setText("Add New Interaction");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                type);
        binding.typeSpinner.setAdapter(ad);

        ArrayAdapter ad2
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                channel);
        binding.channelSpinner.setAdapter(ad2);

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