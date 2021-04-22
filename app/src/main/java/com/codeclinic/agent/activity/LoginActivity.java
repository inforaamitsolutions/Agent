package com.codeclinic.agent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.codeclinic.agent.R;
import com.codeclinic.agent.databinding.ActivityLoginBinding;
import com.codeclinic.agent.model.LoginModel;
import com.codeclinic.agent.model.user.UserDetailsModel;
import com.codeclinic.agent.model.user.UserModel;
import com.codeclinic.agent.retrofit.RestClass;
import com.codeclinic.agent.utils.LogoutService;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.text.TextUtils.isEmpty;
import static com.codeclinic.agent.utils.SessionManager.AccessToken;
import static com.codeclinic.agent.utils.SessionManager.UName;
import static com.codeclinic.agent.utils.SessionManager.sessionManager;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private final CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);


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
                           /* WorkManager workManager = WorkManager.getInstance(LoginActivity.this);
                            OneTimeWorkRequest oneTimeWorkRequest =
                                    new OneTimeWorkRequest.Builder(RefreshTokenWorker.class)
                                            .build();
                            workManager.enqueue(oneTimeWorkRequest);*/
                            callUserDetailsAPI();
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

    public void callUserDetailsAPI() {
        binding.loadingView.loader.setVisibility(View.VISIBLE);
        disposable.add(RestClass.getClient().USER_MODEL_SINGLE_CALL(
                sessionManager.getTokenDetails().get(AccessToken),
                sessionManager.getTokenDetails().get(UName))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull UserModel response) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                        if (response.getBody() != null) {
                            UserDetailsModel user = response.getBody().getUser();
                            Log.i("userDetails", "Data ==> " + new Gson().toJson(user));
                            sessionManager.setUserCredentials(user.getId() + "", user.getEmail(), user.getUserName(), user.getFirstName(), user.getLastName(), user.getOtherName(), user.getPhoneNumber() + "");
                            startService(new Intent(LoginActivity.this, LogoutService.class));
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Log.i("userDetails", "Server Error " + response.getSuccessStatus());
                        }

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        binding.loadingView.loader.setVisibility(View.GONE);
                        Log.i("userDetails", "Server Error " + e.getMessage());
                    }
                }));
    }

}