package com.codeclinic.agent.retrofit;

import android.util.Log;

import com.codeclinic.agent.model.LoginModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.codeclinic.agent.utils.SessionManager.AccessToken;
import static com.codeclinic.agent.utils.SessionManager.RefreshToken;
import static com.codeclinic.agent.utils.SessionManager.UName;
import static com.codeclinic.agent.utils.SessionManager.sessionManager;

public class TokenRefreshInterceptor implements Interceptor {
    private boolean isRefreshing;

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request original = chain.request();
        Request request = chain.request();

        String token = sessionManager.getTokenDetails().get(AccessToken);

        Request.Builder builder = request.newBuilder();
        builder.header("Authorization", token);
        builder.header("Content-Type", "application/json");
        builder.method(original.method(), original.body());


        request = builder.build();
        Response response = chain.proceed(request);

        if (response.code() == 401) {

            Log.i("refreshToken", "Failed " + request.toString() + " with token -> " + token);

            // Refresh your access_token using a synchronous api request
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("refreshToken", sessionManager.getTokenDetails().get(RefreshToken));
                jsonObject.put("userName", sessionManager.getTokenDetails().get(UName));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("tokenRequest", jsonObject.toString());
            LoginModel refreshTokenResponse = RestClass.getClient().refreshTokenAPI(jsonObject.toString()).blockingGet();

            if (refreshTokenResponse.getAccessToken() != null) {
                sessionManager.setUserSession("Bearer " + refreshTokenResponse.getAccessToken(),
                        sessionManager.getTokenDetails().get(UName),
                        refreshTokenResponse.getExpiresIn() + "",
                        refreshTokenResponse.getRefreshToken(),
                        refreshTokenResponse.getRefreshExpiresIn() + "");
                builder.header("Authorization", refreshTokenResponse.getAccessToken());
                request = builder.build();
                Log.i("refreshToken", "Send " + request.toString() + " again with new token -> " + refreshTokenResponse.getAccessToken());
                Log.i("refreshToken", "--------------------------------------------------------------------------------");
                return chain.proceed(request);
            }

        }

        Log.i("refreshToken", "req 200 with token -> " + sessionManager.getTokenDetails().get(AccessToken));

        return response;

    }
}
