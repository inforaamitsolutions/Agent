package com.codeclinic.agent.retrofit;


import com.codeclinic.agent.model.CheckCustomerExistModel;
import com.codeclinic.agent.model.ImageUploadModel;
import com.codeclinic.agent.model.InteractionCategoryModel;
import com.codeclinic.agent.model.InteractionModel;
import com.codeclinic.agent.model.LoanAccountsByNoModel;
import com.codeclinic.agent.model.LoanAccountsModel;
import com.codeclinic.agent.model.LoanProductsModel;
import com.codeclinic.agent.model.LoanStatusModel;
import com.codeclinic.agent.model.LoginModel;
import com.codeclinic.agent.model.MarketModel;
import com.codeclinic.agent.model.PerformanceModel;
import com.codeclinic.agent.model.ProductSegmentModel;
import com.codeclinic.agent.model.StaffModel;
import com.codeclinic.agent.model.StatusModel;
import com.codeclinic.agent.model.SubmitInteractionRecordModel;
import com.codeclinic.agent.model.SupplierModel;
import com.codeclinic.agent.model.TimeLineStatusModel;
import com.codeclinic.agent.model.ZonesModel;
import com.codeclinic.agent.model.businesDataUpdate.BusinessDataSubmitModel;
import com.codeclinic.agent.model.businesDataUpdate.FetchBusinessDataFormModel;
import com.codeclinic.agent.model.customer.CustomerSubmitFormModel;
import com.codeclinic.agent.model.customer.FetchCustomerFormModel;
import com.codeclinic.agent.model.customerList.CustomerModel;
import com.codeclinic.agent.model.lead.FetchLeadFormModel;
import com.codeclinic.agent.model.lead.LeadSubmitFormModel;
import com.codeclinic.agent.model.leadInfo.LeadInfoModel;
import com.codeclinic.agent.model.leadInfo.LeadInteractionHistoryModel;
import com.codeclinic.agent.model.leadList.LeadModel;
import com.codeclinic.agent.model.product.ProductModel;
import com.codeclinic.agent.model.supplier.FetchSupplierFormModel;
import com.codeclinic.agent.model.user.UserModel;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface API {


    //Login API
    @Headers("Content-Type: application/json")
    @POST("baseapi/userservice/userauthentication/getAccessToken")
    Single<LoginModel> LOGIN_MODEL_SINGLE_CALL(@Body String body);


    //Get User Credentials API
    @Headers("Content-Type: application/json")
    @GET("baseapi/userservice/manage-users/findUserAndStaffByUserName")
    Single<UserModel> USER_MODEL_SINGLE_CALL(@Header("Authorization") String header, @Query("userName") String params);

    //Get User Credentials API
    @Headers("Content-Type: application/json")
    @POST("baseapi/userservice/userauthentication/refreshAccessToken")
    Single<LoginModel> REFRESH_TOKEN_SINGLE_CALL(@Body String params);

    //Refresh Token Credentials API
    @Headers("Content-Type: application/json")
    @POST("baseapi/userservice/userauthentication/refreshAccessToken")
    Single<LoginModel> refreshTokenAPI(@Body String params);


    /****************************************** Performance Data Forms  *****************************************************/


    //Fetch All Filters Interactions API
    @Headers("Content-Type: application/json")
    @GET("baseapi/staffservice/staff-performance/getPerformanceSummary")
    Single<PerformanceModel> PERFORMANCE_MODEL_SINGLE(@Header("Authorization") String header, @QueryMap Map<String, String> paramsMap);

    /****************************************** Get Products And Survey Name  *****************************************************/


    //All Products List API
    @Headers("Content-Type: application/json")
    @GET("customer/customerState/getProductAndSurveyNameByStaffId")
    Single<ProductModel> callProductsListAPI(@Header("Authorization") String header, @Query("staffId") String staffID);


    /****************************************** Dynamic Lead Customer Business and Supplier Forms  *****************************************************/

    //Fetch CustomRegistration Form API
    @Headers("Content-Type: application/json")
    @GET("customer/customerState/getSurveyBySurveyName")
    Single<FetchCustomerFormModel> FETCH_CUSTOMER_FORM_MODEL_SINGLE(@Header("Authorization") String header, @Query("surveyName") String params);

    //Check Customer Exist API
    @Headers("Content-Type: application/json")
    @GET("customer/customers/checkCustomer")
    Single<CheckCustomerExistModel> CHECK_CUSTOMER_EXIST_MODEL_SINGLE(@Header("Authorization") String header, @Query("documentNumber") String param1, @Query("mobileNumber") String param2);


    //Add Images in Customer Form
    @Multipart
    @POST("customer/customerState/file-upload")
    Call<ImageUploadModel> uploadImageAPI(@Header("Authorization") String header,
                                          @Part MultipartBody.Part image,
                                          @PartMap() Map<String, RequestBody> partMap);

    //Submit customer form API
    @Headers("Content-Type: application/json")
    @POST("customer/customerState/registerCustomer")
    Single<CustomerSubmitFormModel> CUSTOMER_SUBMIT_FORM_MODEL_SINGLE_CALL(@Header("Authorization") String header, @Body String body);

    //Submit customer form API
    @Headers("Content-Type: application/json")
    @POST("customer/customerState/registerCustomer")
    Observable<CustomerSubmitFormModel> CUSTOMER_OBSERVABLE_SUBMIT_FORM_MODEL_SINGLE_CALL(@Header("Authorization") String header, @Body String body);

    //Fetch LeadRegistration Form API
    @Headers("Content-Type: application/json")
    @GET("customer/customerState/getSurveyBySurveyName")
    Single<FetchLeadFormModel> FETCH_LEAD_FORM_MODEL_SINGLE(@Header("Authorization") String header, @Query("surveyName") String params);

    //Lead Submit Form API
    @Headers("Content-Type: application/json")
    @POST("customer/customerState/registerLeadCustomer")
    Single<LeadSubmitFormModel> LEAD_SUBMIT_FORM_MODEL_SINGLE_CALL(@Header("Authorization") String header, @Body String body);

    //Lead Submit Form API
    @Headers("Content-Type: application/json")
    @POST("customer/customerState/registerLeadCustomer")
    Observable<LeadSubmitFormModel> LEAD_OBSERVABLE_SUBMIT_FORM_MODEL_SINGLE_CALL(@Header("Authorization") String header, @Body String body);

    //Fetch Business Data Update Form API
    @Headers("Content-Type: application/json")
    @GET("customer/customerState/getSurveyBySurveyName")
    Single<FetchBusinessDataFormModel> FETCH_BUSINESS_DATA_FORM_MODEL_SINGLE(@Header("Authorization") String header, @Query("surveyName") String params);

    //Business data update form API
    @Headers("Content-Type: application/json")
    @POST("customer/customerState/kycRefresh")
    Single<BusinessDataSubmitModel> BUSINESS_DATA_SUBMIT_FORM_MODEL_SINGLE_CALL(@Header("Authorization") String header, @Body String body);

    //Business data update form API
    @Headers("Content-Type: application/json")
    @POST("customer/customerState/kycRefresh")
    Observable<BusinessDataSubmitModel> BUSINESS_OBSERVABLE_DATA_SUBMIT_FORM_MODEL_SINGLE_CALL(@Header("Authorization") String header, @Body String body);

    //Fetch Supplier Data Update Form API
    @Headers("Content-Type: application/json")
    @GET("customer/customerState/getSurveyBySurveyName")
    Single<FetchSupplierFormModel> FETCH_SUPPLIER_FORM_MODEL_SINGLE(@Header("Authorization") String header, @Query("surveyName") String params);


    /****************************************** Filters Loan , Lead  and Customer list *****************************************************/

    //Fetch Staff List API
    @Headers("Content-Type: application/json")
    @GET("baseapi/staffservice/staff/findAllStaff")
    Single<StaffModel> FETCH_STAFF_MODEL_SINGLE(@Header("Authorization") String header);

    //Fetch Statuses List API
    @Headers("Content-Type: application/json")
    @GET("baseapi/customerservice/customer-status/getAll")
    Single<StatusModel> FETCH_STATUSES_MODEL_SINGLE(@Header("Authorization") String header);

    //Fetch Product List API
    @Headers("Content-Type: application/json")
    @GET("baseapi/loanservice/products/getAll")
    Single<LoanProductsModel> FETCH_LOAN_PRODUCT_MODEL_SINGLE(@Header("Authorization") String header);

    //Fetch Supplier List API
    @Headers("Content-Type: application/json")
    @GET("customer/customers/getCustomersByRole?role=MYMOBI_PARTNER")
    Single<SupplierModel> FETCH_SUPPLIER_MODEL_SINGLE(@Header("Authorization") String header);

    //Fetch Loan Status List API
    @Headers("Content-Type: application/json")
    @GET("baseapi/constants/loanAccountStatuses")
    Single<LoanStatusModel> FETCH_LOAN_STATUS_MODEL_SINGLE(@Header("Authorization") String header);

    //Fetch Time Lines Status List API
    @Headers("Content-Type: application/json")
    @GET("baseapi/loanservice/timeline-states/getByProductId")
    Single<TimeLineStatusModel> FETCH_TIMELINE_STATUS_MODEL_SINGLE(@Header("Authorization") String header, @Query("productId") String params);


    //Fetch Segments List API
    @Headers("Content-Type: application/json")
    @GET("baseapi/loanservice/products/getAll")
    Single<ProductSegmentModel> FETCH_SEGMENTS_MODEL_SINGLE(@Header("Authorization") String header);

    //Fetch Zones List API
    @Headers("Content-Type: application/json")
    @GET("customer/customers/getAllParentGroups")
    Single<ZonesModel> FETCH_ZONES_MODEL_SINGLE(@Header("Authorization") String header);

    //Fetch Markets List API
    @Headers("Content-Type: application/json")
    @GET("customer/customers/getSubGroupsByParent")
    Single<MarketModel> FETCH_MARKETS_MODEL_SINGLE(@Header("Authorization") String header, @Query("parentId") String params);

    //Lead List API
    @Headers("Content-Type: application/json")
    @POST("customer/customers/getLeadCustomers")
    Single<LeadModel> GET_LEAD_LIST_MODEL_SINGLE_CALL(@Header("Authorization") String header, @Body String body);

    //Customer List API
    @Headers("Content-Type: application/json")
    @POST("customer/customers/getCustomers")
    Single<CustomerModel> GET_CUSTOMER_LIST_MODEL_SINGLE_CALL(@Header("Authorization") String header, @Body String body);

    //Loan List By Number List API
    @Headers("Content-Type: application/json")
    @GET("baseapi/loansummary/getLoanAccountSummaryByLoanNumber/{loanNo}")
    Single<LoanAccountsByNoModel> GET_LOAN_ACCOUNT_BY_NUMBER_CALL(@Header("Authorization") String header, @Path("loanNo") String loanNo);

    //Loan List By Multiple Filters List API
    @Headers("Content-Type: application/json")
    @POST("baseapi/loanservice/loanSummary")
    Single<LoanAccountsModel> GET_LOAN_ACCOUNT_BY_FILTERS_CALL(@Header("Authorization") String header, @Body String body);


    /****************************************** Lead Info and interactions *****************************************************/


    //Fetch Lead Info API
    @Headers("Content-Type: application/json")
    @GET("customer/customers/getOneCustomer")
    Single<LeadInfoModel> FETCH_LEAD_INFO_MODEL_SINGLE(@Header("Authorization") String header, @Query("custId") String params);

    //Fetch Lead Interaction history API
    @Headers("Content-Type: application/json")
    @GET("customer/customers/interactions")
    Single<LeadInteractionHistoryModel> LEAD_INTERACTION_HISTORY_MODEL_SINGLE(@Header("Authorization") String header, @Query("customerId") String params);

    //Fetch Lead Interaction Categories API
    @Headers("Content-Type: application/json")
    @GET("customer/customers/interactions/categories")
    Single<InteractionCategoryModel> INTERACTION_CATEGORY_MODEL_SINGLE(@Header("Authorization") String header);

    //Submit Interactions Record API
    @Headers("Content-Type: application/json")
    @POST("customer/customers/interactions/create")
    Single<SubmitInteractionRecordModel> SUBMIT_INTERACTION_RECORD_MODEL_SINGLE(@Header("Authorization") String header, @Body String body);


    /****************************************** Customer Info and interactions *****************************************************/


    //Fetch CustomerInfo API
    @Headers("Content-Type: application/json")
    @GET("customer/customerState")
    Single<com.codeclinic.agent.model.customerInfo.CustomerModel> FETCH_CUSTOMER_INFO_MODEL_SINGLE(@Header("Authorization") String header, @Query("customerId") String params);

    //Fetch Customer Loan Accounts API
    @Headers("Content-Type: application/json")
    @GET("baseapi/loansummary/findAllAccountsByCustomerId/{CustomerId}")
    Single<LoanAccountsModel> FETCH_CUSTOMER_LOAN_ACCOUNTS_MODEL_SINGLE(@Header("Authorization") String header, @Path("CustomerId") String CustomerID, @Query("startDate") String startDate, @Query("endDate") String endDateDate);

    /******************************************  Interactions Filters , DueFollowUp and PromiseToPay *****************************************************/

    //Fetch Due Followup Interactions API
    @Headers("Content-Type: application/json")
    @GET("customer/customers/interactions/getDueFollowUp")
    Single<InteractionModel> FETCH_DUE_FOLLOWUP_MODEL_SINGLE(@Header("Authorization") String header, @Query("followUpDate") String params);

    //Fetch Promise to pay Interactions API
    @Headers("Content-Type: application/json")
    @GET("customer/customers/interactions/getDuePtp")
    Single<InteractionModel> FETCH_PROMISE_TO_PAY_MODEL_SINGLE(@Header("Authorization") String header, @Query("dateToPay") String params);

    //Fetch All Filters Interactions API
    @Headers("Content-Type: application/json")
    @GET("customer/customers/interactions/getInteractions")
    Single<InteractionModel> FETCH_FILTERS_INTERACTIONS_MODEL_SINGLE(@Header("Authorization") String header, @QueryMap Map<String, String> paramsMap);


}
