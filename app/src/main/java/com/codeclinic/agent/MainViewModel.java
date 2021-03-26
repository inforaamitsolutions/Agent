package com.codeclinic.agent;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.codeclinic.agent.model.customer.CustomerSurveyDefinitionPageModel;
import com.codeclinic.agent.model.customer.FetchCustomerFormModel;
import com.codeclinic.agent.model.lead.FetchLeadFormModel;
import com.codeclinic.agent.model.lead.LeadSurveyDefinitionPageModel;
import com.codeclinic.agent.model.user.UserDetailsModel;
import com.codeclinic.agent.model.user.UserModel;
import com.codeclinic.agent.retrofit.RestClass;
import com.codeclinic.agent.utils.SessionManager;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.codeclinic.agent.database.LocalDatabase.localDatabase;
import static com.codeclinic.agent.utils.SessionManager.UName;
import static com.codeclinic.agent.utils.SessionManager.sessionManager;

public class MainViewModel extends AndroidViewModel {

    private Application application;

    private CompositeDisposable disposable = new CompositeDisposable();

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }


    public void callCustomerForm() {
        disposable.add(RestClass.getClient().FETCH_CUSTOMER_FORM_MODEL_SINGLE(
                sessionManager.getTokenDetails().get(SessionManager.AccessToken),
                "Customer Registration Form")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<FetchCustomerFormModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull FetchCustomerFormModel response) {
                        if (response.getBody() != null) {
                            List<CustomerSurveyDefinitionPageModel> surveyPagesList = response.getBody().getSurveyDefinitionPages();
                            if (surveyPagesList != null) {
                                addCustomerSurveyForm(surveyPagesList);
                            }

                        } else {
                            Log.i("customerForm", "Server Error " + response.getSuccessStatus());
                        }

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("customerForm", "Server Error " + e.getMessage());
                    }
                }));
    }

    private void addCustomerSurveyForm(List<CustomerSurveyDefinitionPageModel> list) {
        disposable.add(Completable.fromAction(() -> localDatabase.getDAO()
                .addCustomerSurveyForm(list))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.i("customerSurveyForm", "added to local");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("customerSurveyForm", "Error  ==  " + e.getMessage());
                    }
                }));
    }

    public void callLeadForm() {
        disposable.add(RestClass.getClient().FETCH_LEAD_FORM_MODEL_SINGLE(
                sessionManager.getTokenDetails().get(SessionManager.AccessToken),
                "Lead Registration")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<FetchLeadFormModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull FetchLeadFormModel response) {
                        if (response.getBody() != null) {
                            List<LeadSurveyDefinitionPageModel> surveyPagesList = response.getBody().getSurveyDefinitionPages();
                            if (surveyPagesList != null) {
                                addLeadSurveyForm(surveyPagesList);
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

    private void addLeadSurveyForm(List<LeadSurveyDefinitionPageModel> list) {
        disposable.add(Completable.fromAction(() -> localDatabase.getDAO()
                .addLeadSurveyForm(list))
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

    public void callUserDetailsAPI() {
        disposable.add(RestClass.getClient().USER_MODEL_SINGLE_CALL(
                sessionManager.getTokenDetails().get(SessionManager.AccessToken),
                sessionManager.getTokenDetails().get(UName))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull UserModel response) {
                        if (response.getBody() != null) {
                            UserDetailsModel user = response.getBody();
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
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
