package com.codeclinic.agent.retrofit;


import com.codeclinic.agent.model.FetchCustomerFormModel;
import com.codeclinic.agent.model.LoginModel;
import com.codeclinic.agent.model.SubmitFormModel;

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
    @POST("userservice/userauthentication/getAccessToken")
    Single<LoginModel> LOGIN_MODEL_SINGLE_CALL(@Body String body);


    //Fetch CustomRegistration Form API
    @Headers("Content-Type: application/json")
    @GET("surveyservice/survey-definitions/getById")
    Single<FetchCustomerFormModel> FETCH_CUSTOMER_FORM_MODEL_SINGLE(@Header("Authorization") String header, @Query("surveyDefinitionId") String params);

    //Login API
    @Headers("Content-Type: application/json")
    @POST("customerState/registerCustomer")
    Single<SubmitFormModel> CUSTOMER_SUBMIT_FORM_MODEL_SINGLE_CALL(@Header("Authorization") String header, @Body String body);

}
