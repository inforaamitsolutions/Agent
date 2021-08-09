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

import static android.text.TextUtils.isEmpty;
import static com.codeclinic.agent.utils.SessionManager.AccessToken;
import static com.codeclinic.agent.utils.SessionManager.RefreshToken;
import static com.codeclinic.agent.utils.SessionManager.UName;
import static com.codeclinic.agent.utils.SessionManager.sessionManager;

public class TokenAuthenticator implements Authenticator {

    private boolean isAPICalled = false;

    @Override
    public synchronized Request authenticate(Route route, @NonNull Response response) {

        String oldToken = sessionManager.getTokenDetails().get(AccessToken);
        assert oldToken != null;
        String newToken = callRefreshTokenAPI(response);
        return response.request().newBuilder()
                .header("Authorization", isEmpty(newToken) ? oldToken : newToken)
                .build();

    }

    private synchronized String callRefreshTokenAPI(Response response) {
        String newToken = "";
        if (!isAPICalled) {
            isAPICalled = true;
            long timeDiff = System.currentTimeMillis() - sessionManager.getTokenTime();
            Log.i("timeDiff", timeDiff + "");
            if (response.code() == 401 && (timeDiff > 3600000 || timeDiff == 0)) {
                // Refresh your access_token using a synchronous api request
                LoginModel refreshTokenResponse = null;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("refreshToken", sessionManager.getTokenDetails().get(RefreshToken));
                    jsonObject.put("userName", sessionManager.getTokenDetails().get(UName));
                    Log.i("tokenRequest", jsonObject.toString());
                    refreshTokenResponse = RestClass.getClient().refreshTokenAPI(jsonObject.toString()).blockingGet();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("refreshToken", "error while refreshing the token on API call" + e.getMessage());
                }

                assert refreshTokenResponse != null;
                newToken = refreshTokenResponse.getAccessToken();
                if (newToken != null) {
                    sessionManager.setTokenTime();
                    sessionManager.setUserSession("Bearer " + refreshTokenResponse.getAccessToken(),
                            sessionManager.getTokenDetails().get(UName),
                            refreshTokenResponse.getExpiresIn() + "",
                            refreshTokenResponse.getRefreshToken(),
                            refreshTokenResponse.getRefreshExpiresIn() + "");
                }

                Log.i("refreshToken", "token is refreshed in api the token is : " + newToken);
            }
        }

        if (!isEmpty(newToken)) {
            isAPICalled = false;
        }

        return newToken;
    }
}
