package com.codeclinic.agent.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestClass {

    //LiveUrl
    private static final String BASE_URL = "https://retail-apitst.mfs.co.ke/";

    private static API api = null;

    public static void createRetrofitInstance() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .authenticator(new TokenAuthenticator())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(API.class);
    }

    public static API getClient() {
        return api;
    }

}
