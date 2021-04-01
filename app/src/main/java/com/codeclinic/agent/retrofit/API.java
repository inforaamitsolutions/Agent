package com.codeclinic.agent.retrofit;


import com.codeclinic.agent.model.LoginModel;
import com.codeclinic.agent.model.customer.CustomerSubmitFormModel;
import com.codeclinic.agent.model.customer.FetchCustomerFormModel;
import com.codeclinic.agent.model.lead.FetchLeadFormModel;
import com.codeclinic.agent.model.lead.LeadSubmitFormModel;
import com.codeclinic.agent.model.user.UserModel;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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

}
