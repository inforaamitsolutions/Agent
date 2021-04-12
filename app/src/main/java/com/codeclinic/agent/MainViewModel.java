package com.codeclinic.agent;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.codeclinic.agent.fragment.CustomerFragment;
import com.codeclinic.agent.fragment.HomeFragment;
import com.codeclinic.agent.fragment.LeadFragment;
import com.codeclinic.agent.fragment.LoanFragment;
import com.codeclinic.agent.model.StaffListModel;
import com.codeclinic.agent.model.StaffModel;
import com.codeclinic.agent.model.ZoneListModel;
import com.codeclinic.agent.model.ZonesModel;
import com.codeclinic.agent.model.customer.CustomerSurveyDefinitionPageModel;
import com.codeclinic.agent.model.customer.FetchCustomerFormBodyModel;
import com.codeclinic.agent.model.customer.FetchCustomerFormModel;
import com.codeclinic.agent.model.lead.FetchLeadFormBodyModel;
import com.codeclinic.agent.model.lead.FetchLeadFormModel;
import com.codeclinic.agent.model.lead.LeadSurveyDefinitionPageModel;
import com.codeclinic.agent.model.leadList.LeadListModel;
import com.codeclinic.agent.model.leadList.LeadModel;
import com.codeclinic.agent.retrofit.RestClass;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.codeclinic.agent.database.LocalDatabase.localDatabase;
import static com.codeclinic.agent.utils.Constants.CUSTOMER_FRAGMENT;
import static com.codeclinic.agent.utils.Constants.HOME_FRAGMENT;
import static com.codeclinic.agent.utils.Constants.LEAD_FRAGMENT;
import static com.codeclinic.agent.utils.Constants.LOAN_FRAGMENT;
import static com.codeclinic.agent.utils.SessionManager.AccessToken;
import static com.codeclinic.agent.utils.SessionManager.sessionManager;

public class MainViewModel extends AndroidViewModel {

    private final Application application;

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final HomeFragment homeFragment = new HomeFragment();
    private final CustomerFragment customerFragment = new CustomerFragment();
    private final LeadFragment leadFragment = new LeadFragment();
    private final LoanFragment loanFragment = new LoanFragment();

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }


    public void callCustomerForm() {
        /*"Customer Registration Form"*/
        disposable.add(RestClass.getClient().FETCH_CUSTOMER_FORM_MODEL_SINGLE(
                sessionManager.getTokenDetails().get(AccessToken),
                "customer_registration_form")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<FetchCustomerFormModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull FetchCustomerFormModel response) {
                        if (response.getBody() != null) {
                            List<CustomerSurveyDefinitionPageModel> surveyPagesList = response.getBody().getSurveyDefinitionPages();
                            if (surveyPagesList != null) {
                                addCustomerSurveyForm(response.getBody());
                            }

                        } else {
                            Log.i("customerForm", "Response Error " + response.getSuccessStatus());
                        }

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("customerForm", "Server Error " + e.getMessage());
                    }
                }));
    }


    private void addCustomerSurveyForm(FetchCustomerFormBodyModel customerForm) {
        disposable.add(Completable.fromAction(() -> localDatabase.getDAO()
                .addCustomerSurveyForm(customerForm))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.i("customerForm", "added to local");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("customerForm", "Error  ==  " + e.getMessage());
                    }
                }));
    }

    /****************************** Manage Filters Data Section *********************************************/

    public MutableLiveData<List<StaffListModel>> staffList = new MutableLiveData<>();

    private void addLeadSurveyForm(FetchLeadFormBodyModel entity) {
        disposable.add(Completable.fromAction(() -> localDatabase.getDAO()
                .addLeadSurveyForm(entity))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.i("LeadSurveyForm", "added to local");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("LeadSurveyForm", "Error  ==  " + e.getMessage());
                    }
                }));
    }

    public MutableLiveData<List<ZoneListModel>> zoneList = new MutableLiveData<>();
    public MutableLiveData<List<LeadListModel>> leadList = new MutableLiveData<>();

    public void callLeadForm() {
        disposable.add(RestClass.getClient().FETCH_LEAD_FORM_MODEL_SINGLE(
                sessionManager.getTokenDetails().get(AccessToken),
                "lead_registration_form_test")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<FetchLeadFormModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull FetchLeadFormModel response) {
                        if (response.getBody() != null) {
                            List<LeadSurveyDefinitionPageModel> surveyPagesList = response.getBody().getSurveyDefinitionPages();
                            if (surveyPagesList != null) {
                                addLeadSurveyForm(response.getBody());
                            }

                        } else {
                            Log.i("leadForm", "Server Error " + response.getSuccessStatus());
                        }

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("leadForm", "Server Error " + e.getMessage());
                    }
                }));
    }

    public void getStaffAPI() {
        disposable.add(RestClass.getClient().FETCH_STAFF_MODEL_SINGLE(sessionManager.getTokenDetails().get(AccessToken))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<StaffModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull StaffModel response) {
                        if (response.getStaffList() != null) {
                            staffList.postValue(response.getStaffList());
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("staff", "" + e.getMessage());
                        staffList.postValue(null);
                    }
                }));
    }

    public void getZonesAPI() {
        disposable.add(RestClass.getClient().FETCH_ZONES_MODEL_SINGLE(sessionManager.getTokenDetails().get(AccessToken))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ZonesModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull ZonesModel response) {
                        if (response.getZoneList() != null) {
                            zoneList.postValue(response.getZoneList());
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("zones", "" + e.getMessage());
                        zoneList.postValue(null);
                    }
                }));
    }

    public void getLeadsAPI(JSONObject jsonObject) {
        disposable.add(RestClass.getClient().GET_LEAD_LIST_MODEL_SINGLE_CALL(
                sessionManager.getTokenDetails().get(AccessToken), jsonObject.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<LeadModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull LeadModel response) {
                        if (response.getLeadList() != null) {
                            leadList.postValue(response.getLeadList());
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("leads", "" + e.getMessage());
                        leadList.postValue(null);
                    }
                }));
    }

    /****************************** Manage Fragment Section *********************************************/

    public Fragment getFragment(int fragment) {

        if (fragment == HOME_FRAGMENT) {
            return homeFragment;
        } else if (fragment == LOAN_FRAGMENT) {
            return loanFragment;
        } else if (fragment == LEAD_FRAGMENT) {
            return leadFragment;
        } else if (fragment == CUSTOMER_FRAGMENT) {
            return customerFragment;
        }
        return null;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
