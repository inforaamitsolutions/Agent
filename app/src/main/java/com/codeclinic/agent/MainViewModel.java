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
import com.codeclinic.agent.model.LoadingResult;
import com.codeclinic.agent.model.LoanAccountsByNoModel;
import com.codeclinic.agent.model.LoanAccountsModel;
import com.codeclinic.agent.model.LoanProductListModel;
import com.codeclinic.agent.model.LoanProductsModel;
import com.codeclinic.agent.model.LoanStatusModel;
import com.codeclinic.agent.model.MarketListModel;
import com.codeclinic.agent.model.MarketModel;
import com.codeclinic.agent.model.PerformanceModel;
import com.codeclinic.agent.model.ProductSegmentListModel;
import com.codeclinic.agent.model.ProductSegmentModel;
import com.codeclinic.agent.model.StaffListModel;
import com.codeclinic.agent.model.StaffModel;
import com.codeclinic.agent.model.StatusListModel;
import com.codeclinic.agent.model.StatusModel;
import com.codeclinic.agent.model.SupplierListModel;
import com.codeclinic.agent.model.SupplierModel;
import com.codeclinic.agent.model.TimeLineStatusListModel;
import com.codeclinic.agent.model.TimeLineStatusModel;
import com.codeclinic.agent.model.ZoneListModel;
import com.codeclinic.agent.model.ZonesModel;
import com.codeclinic.agent.model.businesDataUpdate.BusinessDataSurveyDefinitionPageModel;
import com.codeclinic.agent.model.businesDataUpdate.FetchBusinessDataFormBodyModel;
import com.codeclinic.agent.model.businesDataUpdate.FetchBusinessDataFormModel;
import com.codeclinic.agent.model.customer.CustomerSurveyDefinitionPageModel;
import com.codeclinic.agent.model.customer.FetchCustomerFormBodyModel;
import com.codeclinic.agent.model.customer.FetchCustomerFormModel;
import com.codeclinic.agent.model.customerList.CustomerModel;
import com.codeclinic.agent.model.lead.FetchLeadFormBodyModel;
import com.codeclinic.agent.model.lead.FetchLeadFormModel;
import com.codeclinic.agent.model.lead.LeadSurveyDefinitionPageModel;
import com.codeclinic.agent.model.leadList.LeadModel;
import com.codeclinic.agent.retrofit.RestClass;
import com.codeclinic.agent.utils.SessionManager;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

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

    public final MutableLiveData<LoadingResult> formFetchingComplete = new MutableLiveData<>();

    public MutableLiveData<Boolean> isSessionClear = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    /****************************** Home Screen Data Section *********************************************/


    public MutableLiveData<PerformanceModel> performanceData = new MutableLiveData<>();

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
                            formFetchingComplete.postValue(new LoadingResult("Response Error " + response.getSuccessStatus() + " ", true));
                        }

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("customerForm", "Server Error " + e.getMessage());
                        if (e.getMessage().contains("401")) {
                            isSessionClear.postValue(true);
                        }
                        formFetchingComplete.postValue(new LoadingResult(e.getMessage() + " ", true));
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
                        callLeadForm();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("customerForm", "Error  ==  " + e.getMessage());
                        formFetchingComplete.postValue(new LoadingResult(e.getMessage() + " ", true));
                    }
                }));
    }

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
                            formFetchingComplete.postValue(new LoadingResult("Response Error " + response.getSuccessStatus() + " ", true));
                        }

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("leadForm", "Server Error " + e.getMessage());
                        formFetchingComplete.postValue(new LoadingResult(e.getMessage() + " ", true));
                    }
                }));
    }

    private void addLeadSurveyForm(FetchLeadFormBodyModel entity) {
        disposable.add(Completable.fromAction(() -> localDatabase.getDAO()
                .addLeadSurveyForm(entity))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.i("LeadSurveyForm", "added to local");
                        callBusinessDataForm();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("LeadSurveyForm", "Error  ==  " + e.getMessage());
                        formFetchingComplete.postValue(new LoadingResult(e.getMessage() + " ", true));
                    }
                }));
    }

    public void callBusinessDataForm() {
        disposable.add(RestClass.getClient().FETCH_BUSINESS_DATA_FORM_MODEL_SINGLE(
                sessionManager.getTokenDetails().get(AccessToken),
                "business_details_update_form_test")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<FetchBusinessDataFormModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull FetchBusinessDataFormModel response) {
                        if (response.getBody() != null) {
                            List<BusinessDataSurveyDefinitionPageModel> surveyPagesList = response.getBody().getSurveyDefinitionPages();
                            if (surveyPagesList != null) {
                                addBusinessDataSurveyForm(response.getBody());
                            }

                        } else {
                            Log.i("BusinessDataForm", "Server Error " + response.getSuccessStatus());
                            formFetchingComplete.postValue(new LoadingResult("Response Error " + response.getSuccessStatus() + " ", true));
                        }

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("BusinessDataForm", "Server Error " + e.getMessage());
                        formFetchingComplete.postValue(new LoadingResult(e.getMessage() + " ", true));
                    }
                }));
    }

    private void addBusinessDataSurveyForm(FetchBusinessDataFormBodyModel entity) {
        disposable.add(Completable.fromAction(() -> localDatabase.getDAO()
                .addBusinessDataSurveyForm(entity))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.i("BusinessDateSurveyForm", "added to local");
                        formFetchingComplete.postValue(new LoadingResult(" All Forms are fetched and saved", true));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("BusinessDateSurveyForm", "Error  ==  " + e.getMessage());
                        formFetchingComplete.postValue(new LoadingResult(e.getMessage() + " ", true));
                    }
                }));
    }

    public MutableLiveData<List<String>> loanStatusList = new MutableLiveData<>();


    /****************************** Manage Filters Data Section *********************************************/

    public MutableLiveData<List<StaffListModel>> staffList = new MutableLiveData<>();
    public MutableLiveData<List<StatusListModel>> statusesList = new MutableLiveData<>();
    public MutableLiveData<List<ProductSegmentListModel>> productSegmentList = new MutableLiveData<>();
    public MutableLiveData<List<ZoneListModel>> zoneList = new MutableLiveData<>();
    public MutableLiveData<List<MarketListModel>> marketList = new MutableLiveData<>();
    public MutableLiveData<LeadModel> lead = new MutableLiveData<>();
    public MutableLiveData<CustomerModel> customer = new MutableLiveData<>();
    public MutableLiveData<LoanAccountsModel> loanAccounts = new MutableLiveData<>();
    public MutableLiveData<LoanAccountsByNoModel> loanAccountsByNo = new MutableLiveData<>();
    public MutableLiveData<List<LoanProductListModel>> productList = new MutableLiveData<>();
    public MutableLiveData<List<SupplierListModel>> supplierList = new MutableLiveData<>();

    public void getPerformanceDataAPI(Map<String, String> params) {
        disposable.add(RestClass.getClient().PERFORMANCE_MODEL_SINGLE(sessionManager.getTokenDetails().get(AccessToken), params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<PerformanceModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull PerformanceModel response) {

                        performanceData.postValue(response);

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("performance", "" + e.getMessage());
                        if (e.getMessage().contains("401")) {
                            isSessionClear.postValue(true);
                        }
                        performanceData.postValue(null);
                    }
                }));
    }
    public MutableLiveData<List<TimeLineStatusListModel>> timeLineStatusList = new MutableLiveData<>();

    public void getStatusAPI() {
        disposable.add(RestClass.getClient().FETCH_STATUSES_MODEL_SINGLE(sessionManager.getTokenDetails().get(AccessToken))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<StatusModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull StatusModel response) {
                        if (response.getStatusList() != null) {
                            statusesList.postValue(response.getStatusList());
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("status", "" + e.getMessage());
                        statusesList.postValue(null);
                    }
                }));
    }

    public void getMarketsAPI(String parentId) {
        disposable.add(RestClass.getClient().FETCH_MARKETS_MODEL_SINGLE(sessionManager.getTokenDetails().get(AccessToken), parentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MarketModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull MarketModel response) {
                        if (response.getMarketList() != null) {
                            marketList.postValue(response.getMarketList());
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("market", "" + e.getMessage());
                        marketList.postValue(null);
                    }
                }));
    }

    public void getSegmentsAPI() {
        disposable.add(RestClass.getClient().FETCH_SEGMENTS_MODEL_SINGLE(sessionManager.getTokenDetails().get(AccessToken))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ProductSegmentModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull ProductSegmentModel response) {
                        if (response.getProductSegmentList() != null) {
                            productSegmentList.postValue(response.getProductSegmentList());
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("segment", "" + e.getMessage());
                        productSegmentList.postValue(null);
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
                        Log.i("responseLead", new Gson().toJson(response) + " ");
                        lead.postValue(response);

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("leads", "" + e.getMessage());
                        lead.postValue(null);
                    }
                }));
    }

    public void getCustomersAPI(JSONObject jsonObject) {
        disposable.add(RestClass.getClient().GET_CUSTOMER_LIST_MODEL_SINGLE_CALL(
                sessionManager.getTokenDetails().get(AccessToken), jsonObject.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CustomerModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull CustomerModel response) {
                        Log.i("responseCustomer", new Gson().toJson(response) + " ");
                        customer.postValue(response);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("customer", "" + e.getMessage());
                        customer.postValue(null);
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

    public void getLoanProductAPI() {
        disposable.add(RestClass.getClient().FETCH_LOAN_PRODUCT_MODEL_SINGLE(sessionManager.getTokenDetails().get(AccessToken))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<LoanProductsModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull LoanProductsModel response) {
                        if (response.getLoanProductList() != null) {
                            productList.postValue(response.getLoanProductList());
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("loanProducts", "" + e.getMessage());
                        productList.postValue(null);
                    }
                }));
    }

    public void getSuppliersAPI() {
        disposable.add(RestClass.getClient().FETCH_SUPPLIER_MODEL_SINGLE(sessionManager.getTokenDetails().get(AccessToken))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SupplierModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull SupplierModel response) {
                        if (response.getBody() != null) {
                            supplierList.postValue(response.getBody());
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("suppliers", "" + e.getMessage());
                        supplierList.postValue(null);
                    }
                }));
    }

    public void getLoanStatusAPI() {
        disposable.add(RestClass.getClient().FETCH_LOAN_STATUS_MODEL_SINGLE(sessionManager.getTokenDetails().get(AccessToken))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<LoanStatusModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull LoanStatusModel response) {
                        if (response.getLoanStatuses() != null) {
                            loanStatusList.postValue(response.getLoanStatuses());
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("loanStatus", "" + e.getMessage());
                        loanStatusList.postValue(null);
                    }
                }));
    }

    public void getTimeLoanStatusAPI(String productID) {
        disposable.add(RestClass.getClient().FETCH_TIMELINE_STATUS_MODEL_SINGLE(
                sessionManager.getTokenDetails().get(AccessToken)
                , productID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<TimeLineStatusModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull TimeLineStatusModel response) {
                        if (response.getTimeLineStatusList() != null) {
                            timeLineStatusList.postValue(response.getTimeLineStatusList());
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("timeLineStatus", "" + e.getMessage());
                        timeLineStatusList.postValue(null);
                    }
                }));
    }

    public void fetchLoanAccountsByCustomerIDAPI(String customerID, String startDate, String endDate) {

        disposable.add(RestClass.getClient().FETCH_CUSTOMER_LOAN_ACCOUNTS_MODEL_SINGLE(
                sessionManager.getTokenDetails().get(SessionManager.AccessToken),
                customerID,
                startDate,
                endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<LoanAccountsModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull LoanAccountsModel response) {
                        Log.i("responseLoan", new Gson().toJson(response) + " ");
                        loanAccounts.postValue(response);

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("loanAccountsByCustomer", " " + e.getMessage());
                        loanAccounts.postValue(null);
                    }
                }));
    }

    public void fetchLoanAccountsByLoanNoAPI(String loanNumber) {
        disposable.add(RestClass.getClient().GET_LOAN_ACCOUNT_BY_NUMBER_CALL(
                sessionManager.getTokenDetails().get(SessionManager.AccessToken),
                loanNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<LoanAccountsByNoModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull LoanAccountsByNoModel response) {
                        Log.i("responseLoan", new Gson().toJson(response) + " ");
                        loanAccountsByNo.postValue(response);

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("loanAccountsByNo", " " + e.getMessage());
                        loanAccountsByNo.postValue(null);
                    }
                }));
    }

    public void fetchLoanAccountsByFiltersAPI(JSONObject jsonObject) {
        disposable.add(RestClass.getClient().GET_LOAN_ACCOUNT_BY_FILTERS_CALL(
                sessionManager.getTokenDetails().get(SessionManager.AccessToken),
                jsonObject.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<LoanAccountsModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull LoanAccountsModel response) {
                        Log.i("responseLoan", new Gson().toJson(response) + " ");
                        loanAccounts.postValue(response);

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("loanAccountsByFilters", " " + e.getMessage());
                        loanAccounts.postValue(null);
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
