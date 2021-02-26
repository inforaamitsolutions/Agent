package com.codeclinic.agent.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.btnSingnin.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        });
    }
}