package com.codeclinic.agent.retrofit;


import com.codeclinic.agent.model.InteractionCategoryModel;
import com.codeclinic.agent.model.LoanAccountsByNoModel;
import com.codeclinic.agent.model.LoanAccountsModel;
import com.codeclinic.agent.model.LoanProductsModel;
import com.codeclinic.agent.model.LoanStatusModel;
import com.codeclinic.agent.model.LoginModel;
import com.codeclinic.agent.model.MarketModel;
import com.codeclinic.agent.model.ProductSegmentModel;
import com.codeclinic.agent.model.StaffModel;
import com.codeclinic.agent.model.StatusModel;
import com.codeclinic.agent.model.SubmitInteractionRecordModel;
import com.codeclinic.agent.model.SupplierModel;
import com.codeclinic.agent.model.TimeLineStatusModel;
import com.codeclinic.agent.model.ZonesModel;
import com.codeclinic.agent.model.customer.CustomerSubmitFormModel;
import com.codeclinic.agent.model.customer.FetchCustomerFormModel;
import com.codeclinic.agent.model.customerList.CustomerModel;
import com.codeclinic.agent.model.lead.FetchLeadFormModel;
import com.codeclinic.agent.model.lead.LeadSubmitFormModel;
import com.codeclinic.agent.model.leadInfo.LeadInfoModel;
import com.codeclinic.agent.model.leadInfo.LeadInteractionHistoryModel;
import com.codeclinic.agent.model.leadList.LeadModel;
import com.codeclinic.agent.model.user.UserModel;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {


    //Login API
    @Headers("Content-Type: application/json")
    @POST("baseapi/userservice/userauthentication/getAccessToken")
    Single<LoginModel> LOGIN_MODEL_SINGLE_CALL(@Body String body);


    //Get User Credentials API
    @Headers("Content-Type: application/json")
    @GET("baseapi/userservice/manage-users/findUserAndStaffByUserName")
    Single<UserModel> USER_MODEL_SINGLE_CALL(@Header("Authorization") String header, @Query("userName") String params);


    /****************************************** Dynamic Lead Customer Forms  *****************************************************/

    //Fetch CustomRegistration Form API
    @Headers("Content-Type: application/json")
    @GET("customer/customerState/getSurveyBySurveyName")
    Single<FetchCustomerFormModel> FETCH_CUSTOMER_FORM_MODEL_SINGLE(@Header("Authorization") String header, @Query("surveyName") String params);

    //Submit customer form API
    @Headers("Content-Type: application/json")
    @POST("customer/customerState/registerCustomer")
    Single<CustomerSubmitFormModel> CUSTOMER_SUBMIT_FORM_MODEL_SINGLE_CALL(@Header("Authorization") String header, @Body String body);

    //Fetch LeadRegistration Form API
    @Headers("Content-Type: application/json")
    @GET("customer/customerState/getSurveyBySurveyName")
    Single<FetchLeadFormModel> FETCH_LEAD_FORM_MODEL_SINGLE(@Header("Authorization") String header, @Query("surveyName") String params);

    //Login API
    @Headers("Content-Type: application/json")
    @POST("customer/customerState/registerLeadCustomer")
    Single<LeadSubmitFormModel> LEAD_SUBMIT_FORM_MODEL_SINGLE_CALL(@Header("Authorization") String header, @Body String body);


    /****************************************** Filter Loan , Lead  and Customer list *****************************************************/

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
    @GET("customer/customers/getCustomersByRole?role=SUPPLIER")
    Single<SupplierModel> FETCH_SUPPLIER_MODEL_SINGLE(@Header("Authorization") String header);

    //Fetch Loan Status List API
    @Headers("Content-Type: application/json")
    @GET("baseapi/loanstatus")
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
    @GET("customer/customers/getSubGroupsByParent/{parentId}")
    Single<MarketModel> FETCH_MARKETS_MODEL_SINGLE(@Header("Authorization") String header, @Query("parentId") String params);

    //Lead List API
    @Headers("Content-Type: application/json")
    @POST("customer/customers/getLeadCustomers")
    Single<LeadModel> GET_LEAD_LIST_MODEL_SINGLE_CALL(@Header("Authorization") String header, @Body String body);

    //Customer List API
    @Headers("Content-Type: application/json")
    @POST("customer/customers/getLeadCustomers")
    Single<CustomerModel> GET_CUSTOMER_LIST_MODEL_SINGLE_CALL(@Header("Authorization") String header, @Body String body);

    //Loan List By Number List API
    @Headers("Content-Type: application/json")
    @GET("baseapi/loansummary/getLoanAccountSummaryByLoanNumber/{loanNo}")
    Single<LoanAccountsByNoModel> GET_LOAN_ACCOUNT_BY_NUMBER_CALL(@Header("Authorization") String header, @Path("loanNo") String loanNo);


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
    Single<LoanAccountsModel> FETCH_CUSTOMER_LOAN_ACCOUNTS_MODEL_SINGLE(@Header("Authorization") String header,
                                                                        @Path("CustomerId") String CustomerID,
                                                                        @Query("startDate") String startDate,
                                                                        @Query("endDate") String endDateDate);


}
