package com.codeclinic.agent.retrofit;

import android.util.Log;

import com.codeclinic.agent.model.LoginModel;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.annotations.NonNull;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

import static com.codeclinic.agent.utils.SessionManager.AccessToken;
import static com.codeclinic.agent.utils.SessionManager.RefreshToken;
import static com.codeclinic.agent.utils.SessionManager.UName;
import static com.codeclinic.agent.utils.SessionManager.sessionManager;

public class TokenAuthenticator implements Authenticator {


    @Override
    public Request authenticate(Route route, @NonNull Response response) {

        String oldToken = sessionManager.getTokenDetails().get(AccessToken);
        String newToken = null;

        if (response.code() == 401) {
            // Refresh your access_token using a synchronous api request
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("refreshToken", sessionManager.getTokenDetails().get(RefreshToken));
                jsonObject.put("userName", sessionManager.getTokenDetails().get(UName));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("tokenRequest", jsonObject.toString());
            LoginModel refreshTokenResponse = null;
            try {
                refreshTokenResponse = RestClass.getClient().refreshTokenAPI(jsonObject.toString()).blockingGet();
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("refreshToken", "refreshing the token error on API call" + e.getMessage());
            }

            newToken = refreshTokenResponse.getAccessToken();
            if (newToken != null) {
                sessionManager.setUserSession("Bearer " + refreshTokenResponse.getAccessToken(),
                        sessionManager.getTokenDetails().get(UName),
                        refreshTokenResponse.getExpiresIn() + "",
                        refreshTokenResponse.getRefreshToken(),
                        refreshTokenResponse.getRefreshExpiresIn() + "");
            }

            Log.i("refreshToken", "token is refreshed in api the token is : " + newToken);
            return response.request().newBuilder()
                    .header("Authorization", "Bearer " + newToken)
                    .build();
        }

        Log.i("refreshToken", "token is not refreshed and api is started");

        assert oldToken != null;
        return response.request().newBuilder()
                .header("Authorization", oldToken)
                .build();

    }
}
