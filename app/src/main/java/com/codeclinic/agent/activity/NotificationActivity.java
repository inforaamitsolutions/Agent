package com.codeclinic.agent.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.codeclinic.agent.R;
import com.codeclinic.agent.adapter.NotificationListAdapter;
import com.codeclinic.agent.databinding.ActivityNotificationBinding;

public class NotificationActivity extends AppCompatActivity {
ActivityNotificationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_notification);
        binding.headerLayout.txtHeading.setText("Notifications");
        binding.headerLayout.imgBack.setVisibility(View.VISIBLE);
        binding.headerLayout.imgBack.setOnClickListener(view -> {
            onBackPressed();
        });
        binding.notificationRecyclerView.setHasFixedSize(true);
        binding.notificationRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.notificationRecyclerView.setAdapter(new NotificationListAdapter(this));
    }
}