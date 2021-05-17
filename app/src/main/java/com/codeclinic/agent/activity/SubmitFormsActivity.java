package com.codeclinic.agent.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.codeclinic.agent.R;
import com.codeclinic.agent.database.BusinessDataFinalFormEntity;
import com.codeclinic.agent.database.CustomerFinalFormEntity;
import com.codeclinic.agent.database.LeadFinalFormEntity;
import com.codeclinic.agent.databinding.ActivitySubmitFormsBinding;
import com.codeclinic.agent.model.businesDataUpdate.BusinessDataSubmitModel;
import com.codeclinic.agent.model.customer.CustomerSubmitFormModel;
import com.codeclinic.agent.model.lead.LeadSubmitFormModel;
import com.codeclinic.agent.retrofit.RestClass;
import com.codeclinic.agent.utils.Connection_Detector;
import com.codeclinic.agent.utils.LoadingDialog;
import com.codeclinic.agent.utils.SessionManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.codeclinic.agent.database.LocalDatabase.localDatabase;
import static com.codeclinic.agent.utils.SessionManager.sessionManager;

public class SubmitFormsActivity extends AppCompatActivity {

    private final CompositeDisposable disposable = new CompositeDisposable();
    List<CustomerFinalFormEntity> customerFormsList = new ArrayList<>();
    List<CustomerFinalFormEntity> deleteCustomerFormsList = new ArrayList<>();
    List<LeadFinalFormEntity> leadFormsList = new ArrayList<>();
    List<LeadFinalFormEntity> deleteLeadFormsList = new ArrayList<>();
    List<BusinessDataFinalFormEntity> businessFormsList = new ArrayList<>();
    List<BusinessDataFinalFormEntity> deleteBusinessFormsList = new ArrayList<>();
    String totalForms = "Total Forms : ";
    int currentPos = 0;
    private ActivitySubmitFormsBinding binding;
    private LoadingDialog loadingDialog;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_submit_forms);
        loadingDialog = new LoadingDialog(this);
        binding.headerLayout.txtHeading.setText("Pending forms submission");

        binding.headerLayout.imgBack.setVisibility(View.VISIBLE);
        binding.headerLayout.imgBack.setOnClickListener(v -> {
            finish();
        });


        binding.btnCustomer.setOnClickListener(v -> {
            if (Connection_Detector.isInternetAvailable(this) && customerFormsList.size() != 0) {
                submitCustomerForms();
            } else {
                Toast.makeText(this,
                        !Connection_Detector.isInternetAvailable(this)
                                ? "Please check internet connection" :
                                "no forms available to submit",
                        Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnLead.setOnClickListener(v -> {
            if (Connection_Detector.isInternetAvailable(this) && leadFormsList.size() != 0) {
                submitLeadForms();
            } else {
                Toast.makeText(this,
                        !Connection_Detector.isInternetAvailable(this)
                                ? "Please check internet connection" :
                                "no forms available to submit",
                        Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnBusiness.setOnClickListener(v -> {
            if (Connection_Detector.isInternetAvailable(this) && businessFormsList.size() != 0) {
                submitBusinessForms();
            } else {
                Toast.makeText(this,
                        !Connection_Detector.isInternetAvailable(this)
                                ? "Please check internet connection" :
                                "no forms available to submit",
                        Toast.LENGTH_SHORT).show();
            }
        });


        binding.tvCustomerTotalForms.setText(totalForms + customerFormsList.size());
        binding.tvLeadTotalForms.setText(totalForms + leadFormsList.size());
        binding.tvBusinessTotalForms.setText(totalForms + businessFormsList.size());

        getCustomerFinalForm();
        getLeadFinalForm();
        getBusinessFinalForm();

    }

    @SuppressLint("SetTextI18n")
    public void getCustomerFinalForm() {
        disposable.add(localDatabase.getDAO().getCustomerFinalForm()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(form -> {
                            Log.i("customerFinalForm", new Gson().toJson(form));
                            customerFormsList = form;
                            binding.tvCustomerTotalForms.setText(totalForms + customerFormsList.size());
                        },
                        throwable -> {
                            if (throwable.getMessage() != null)
                                Log.i("customerFinalForm", "Error == " + throwable.getMessage());
                        }
                )
        );
    }

    public void getLeadFinalForm() {
        disposable.add(localDatabase.getDAO().getLeadFinalForm()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(form -> {
                            Log.i("leadFinalForm", new Gson().toJson(form));
                            leadFormsList = form;
                        },
                        throwable -> {
                            if (throwable.getMessage() != null)
                                Log.i("leadFinalForm", "Error == " + throwable.getMessage());
                        }
                )
        );
    }

    public void getBusinessFinalForm() {
        disposable.add(localDatabase.getDAO().getBusinessDataFinalForm()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(form ->
                        {
                            Log.i("businessFinalForm", new Gson().toJson(form));
                            businessFormsList = form;
                        },
                        throwable -> {
                            if (throwable.getMessage() != null)
                                Log.i("businessFinalForm", "Error == " + throwable.getMessage());
                        }
                )
        );
    }


    private void submitCustomerForms() {
        loadingDialog.showProgressDialog("");
        int size = customerFormsList.size();
        Observable.range(0, size)
                .concatMap(pos -> {
                    currentPos = pos;
                    return RestClass.getClient().CUSTOMER_OBSERVABLE_SUBMIT_FORM_MODEL_SINGLE_CALL(sessionManager.getTokenDetails().get(SessionManager.AccessToken)
                            , customerFormsList.get(pos).getRequest());
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<CustomerSubmitFormModel>() {
                    @Override
                    public void onNext(CustomerSubmitFormModel response) {
                        if (response.getSuccessStatus().equals("success")) {
                            deleteCustomerFormsList.add(customerFormsList.get(currentPos));
                        }
                        Log.i("formResponse", "response ==> " + new Gson().toJson(response));
                    }

                    @Override
                    public void onError(Throwable e) {
                        currentPos = 0;
                        Log.d("formResponse", "error " + e.getMessage());
                        if (e.getMessage().contains("401")) {
                            Toast.makeText(SubmitFormsActivity.this, "Session Time out you have to login again", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SubmitFormsActivity.this, "Server Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete() {
                        loadingDialog.hideProgressDialog();
                        currentPos = 0;
                        if (deleteCustomerFormsList.size() != 0) {
                            for (int i = 0; i < deleteCustomerFormsList.size(); i++) {
                                customerFormsList.remove(deleteCustomerFormsList.get(i));
                            }
                            deleteCustomerForm();
                        }
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void deleteCustomerForm() {
        disposable.add(Completable.fromAction(() -> localDatabase.getDAO().deleteCustomerFinalForms(deleteCustomerFormsList))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {

                    @Override
                    public void onComplete() {
                        Log.i("customerFormDelete", "formDeleted");
                        binding.tvCustomerTotalForms.setText(totalForms + customerFormsList.size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("customerFormDelete", "Error = > " + e.getMessage());
                        binding.tvCustomerTotalForms.setText(totalForms + customerFormsList.size());
                    }
                }));
    }


    private void submitLeadForms() {
        loadingDialog.showProgressDialog("");
        int size = leadFormsList.size();
        Observable.range(0, size)
                .concatMap(pos -> {
                    currentPos = pos;
                    return RestClass.getClient().LEAD_OBSERVABLE_SUBMIT_FORM_MODEL_SINGLE_CALL(sessionManager.getTokenDetails().get(SessionManager.AccessToken)
                            , leadFormsList.get(pos).getRequest());
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<LeadSubmitFormModel>() {
                    @Override
                    public void onNext(LeadSubmitFormModel response) {
                        if (response.getSuccessStatus().equals("success")) {
                            deleteLeadFormsList.add(leadFormsList.get(currentPos));
                        }
                        Log.i("formResponse", "response ==> " + new Gson().toJson(response));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("formResponse", "error " + e.getMessage());
                        currentPos = 0;
                        if (e.getMessage().contains("401")) {
                            Toast.makeText(SubmitFormsActivity.this, "Session Time out you have to login again", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SubmitFormsActivity.this, "Server Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete() {
                        loadingDialog.hideProgressDialog();
                        currentPos = 0;
                        if (deleteLeadFormsList.size() != 0) {
                            for (int i = 0; i < deleteLeadFormsList.size(); i++) {
                                leadFormsList.remove(deleteLeadFormsList.get(i));
                            }
                            deleteLeadForm();
                        }
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void deleteLeadForm() {
        disposable.add(Completable.fromAction(() -> localDatabase.getDAO().deleteLeadFinalForms(deleteLeadFormsList))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {

                    @Override
                    public void onComplete() {
                        Log.i("leadFormDelete", "formDeleted");
                        binding.tvLeadTotalForms.setText(totalForms + leadFormsList.size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("leadFormDelete", "Error = > " + e.getMessage());
                        binding.tvLeadTotalForms.setText(totalForms + leadFormsList.size());
                    }
                }));
    }


    private void submitBusinessForms() {
        loadingDialog.showProgressDialog("");
        int size = businessFormsList.size();
        Observable.range(0, size)
                .concatMap(pos -> {
                    currentPos = pos;
                    return RestClass.getClient().BUSINESS_OBSERVABLE_DATA_SUBMIT_FORM_MODEL_SINGLE_CALL(sessionManager.getTokenDetails().get(SessionManager.AccessToken)
                            , businessFormsList.get(pos).getRequest());
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<BusinessDataSubmitModel>() {
                    @Override
                    public void onNext(BusinessDataSubmitModel response) {
                        if (response.getSuccessStatus().equals("success")) {
                            deleteBusinessFormsList.add(businessFormsList.get(currentPos));
                        }
                        Log.i("formResponse", "response ==> " + new Gson().toJson(response));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("formResponse", "error " + e.getMessage());
                        currentPos = 0;
                        if (e.getMessage().contains("401")) {
                            Toast.makeText(SubmitFormsActivity.this, "Session Time out you have to login again", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SubmitFormsActivity.this, "Server Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete() {
                        loadingDialog.hideProgressDialog();
                        currentPos = 0;
                        if (deleteBusinessFormsList.size() != 0) {
                            for (int i = 0; i < deleteBusinessFormsList.size(); i++) {
                                businessFormsList.remove(deleteBusinessFormsList.get(i));
                            }
                            deleteBusinessForm();
                        }
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void deleteBusinessForm() {
        disposable.add(Completable.fromAction(() -> localDatabase.getDAO().deleteBusinessDataFinalForms(deleteBusinessFormsList))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {

                    @Override
                    public void onComplete() {
                        Log.i("businessFormDelete", "formDeleted");
                        binding.tvBusinessTotalForms.setText(totalForms + businessFormsList.size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("businessFormDelete", "Error = > " + e.getMessage());
                        binding.tvBusinessTotalForms.setText(totalForms + businessFormsList.size());
                    }
                }));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}

