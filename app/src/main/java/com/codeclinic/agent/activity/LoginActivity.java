package com.codeclinic.agent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.ActivityLoginBinding;
import com.codeclinic.agent.model.LoginModel;
import com.codeclinic.agent.retrofit.RestClass;
import com.codeclinic.agent.utils.RefreshTokenWorker;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.text.TextUtils.isEmpty;
import static com.codeclinic.agent.utils.SessionManager.sessionManager;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        if (sessionManager.isLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        binding.btnSingnin.setOnClickListener(view -> {
            if (isEmpty(binding.edtUserName.getText().toString())) {
                Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
            } else if (isEmpty(binding.edtPassword.getText().toString())) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            } else {
                callLoginAPI();
            }
        });
    }

    private void callLoginAPI() {
        binding.loadingView.loader.setVisibility(View.VISIBLE);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userName", binding.edtUserName.getText().toString());
            jsonObject.put("password", binding.edtPassword.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        disposable.add(RestClass.getClient().LOGIN_MODEL_SINGLE_CALL(jsonObject.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<LoginModel>() {
                    @Override
                    public void onSuccess(@NonNull LoginModel response) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                        if (response.getAccessToken() != null) {
                            sessionManager.setUserSession("Bearer " + response.getAccessToken(),
                                    binding.edtUserName.getText().toString()
                                    , response.getExpiresIn() + ""
                                    , response.getRefreshToken()
                                    , response.getRefreshExpiresIn() + "");
                            WorkManager workManager = WorkManager.getInstance(LoginActivity.this);
                            OneTimeWorkRequest oneTimeWorkRequest =
                                    new OneTimeWorkRequest.Builder(RefreshTokenWorker.class)
                                            .build();
                            workManager.enqueue(oneTimeWorkRequest);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }));
    }
}