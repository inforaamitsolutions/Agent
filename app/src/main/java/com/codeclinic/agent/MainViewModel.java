package com.codeclinic.agent;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;

import com.codeclinic.agent.fragment.CustomerFragment;
import com.codeclinic.agent.fragment.HomeFragment;
import com.codeclinic.agent.fragment.LeadFragment;
import com.codeclinic.agent.fragment.LoanFragment;
import com.codeclinic.agent.model.customer.CustomerSurveyDefinitionPageModel;
import com.codeclinic.agent.model.customer.FetchCustomerFormBodyModel;
import com.codeclinic.agent.model.customer.FetchCustomerFormModel;
import com.codeclinic.agent.model.lead.FetchLeadFormBodyModel;
import com.codeclinic.agent.model.lead.FetchLeadFormModel;
import com.codeclinic.agent.model.lead.LeadSurveyDefinitionPageModel;
import com.codeclinic.agent.retrofit.RestClass;

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

   /* private void getCustomerSurveyForm(List<CustomerSurveyDefinitionPageModel> customers) {
        disposable.add(localDatabase.getDAO().getCustomerSurveyForm()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                            disposable.add(Completable.fromAction(() -> localDatabase.getDAO()
                                    .removeCustomerForm(list))
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeWith(new DisposableCompletableObserver() {
                                        @Override
                                        public void onComplete() {
                                            Log.i("customerFormDelete", "removed");
                                            addCustomerSurveyForm(customers);
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            Log.i("customerFormDelete", "Error  ==  " + e.getMessage());
                                        }
                                    }));

                        },
                        throwable -> {
                            if (throwable.getMessage() != null)
                                Log.i("leadSurveyForm", "Error == " + throwable.getMessage());
                        }
                )
        );
    }*/

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

    public void callLeadForm() {
        disposable.add(RestClass.getClient().FETCH_LEAD_FORM_MODEL_SINGLE(
                sessionManager.getTokenDetails().get(AccessToken),
                "Lead Registration")
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

     /*    private void getLeadSurveyForm(List<LeadSurveyDefinitionPageModel> leads) {
        disposable.add(localDatabase.getDAO().getLeadSurveyFormList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                            disposable.add(Completable.fromAction(() -> localDatabase.getDAO()
                                    .removeLeadForm(list))
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeWith(new DisposableCompletableObserver() {
                                        @Override
                                        public void onComplete() {
                                            Log.i("LeadFormDelete", "removed");
                                            addLeadSurveyForm(leads);
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            Log.i("LeadFormDelete", "Error  ==  " + e.getMessage());
                                        }
                                    }));

                        },
                        throwable -> {
                            if (throwable.getMessage() != null)
                                Log.i("leadSurveyForm", "Error == " + throwable.getMessage());
                        }
                )
        );
    }*/

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

   /* public void callUserDetailsAPI() {
        disposable.add(RestClass.getClient().USER_MODEL_SINGLE_CALL(
                sessionManager.getTokenDetails().get(AccessToken),
                sessionManager.getTokenDetails().get(UName))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull UserModel response) {
                        if (response.getBody() != null) {
                            UserDetailsModel user = response.getBody().getUser();
                            Log.i("userDetails", "Data ==> " + new Gson().toJson(user));
                            sessionManager.setUserCredentials(user.getId() + "", user.getUserName(), user.getFirstName(), user.getLastName(), user.getOtherName(), user.getPhoneNumber() + "");
                        } else {
                            Log.i("userDetails", "Server Error " + response.getSuccessStatus());
                        }

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("userDetails", "Server Error " + e.getMessage());
                    }
                }));
    }*/


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
