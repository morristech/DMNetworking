package networkexample.md.com.networkexample.networking;

import android.content.Context;

import com.dm.dmnetworking.api_client.base.DMBaseRequest;
import com.dm.dmnetworking.api_client.base.DMBaseTokenHandler;
import com.dm.dmnetworking.api_client.base.DMRequestListener;
import com.dm.dmnetworking.api_client.listeners.DMINetworkListener;
import com.dm.dmnetworking.api_client.listeners.DMIStatusHandleListener;

import org.json.JSONException;
import org.json.JSONObject;


public class ExampleNetworking extends DMBaseRequest {

    private static ExampleNetworking ourInstance;

    private ExampleNetworking() {
    }

    public static ExampleNetworking getInstance() {
        if (ourInstance == null) {
            ourInstance = new ExampleNetworking();
        }

        return ourInstance;
    }

    @Override
    protected void handleStatuses(final Context context, final int statusCode, final JSONObject jsonObject, final DMIStatusHandleListener listener) {
        try {
            String status = "";
            if (jsonObject != null) {
                status = jsonObject.getString("status");
            }
            switch (status) {
                case "INVALID_DATA":
                    listener.onError(status, jsonObject);
                    break;
                case "REFRESH_TOKEN":
                    listener.onTokenUpdate();
                    break;
                default:
                    listener.onComplete(status, jsonObject);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onError(e.getMessage(), jsonObject);
        }
    }

    @Override
    protected boolean isNeedToMakeRequest(final Context context, final DMINetworkListener listener) {
        return super.isNeedToMakeRequest(context, listener);
    }

    @Override
    protected void beforeRequest(final Context context, final DMRequestListener listener) {
        super.beforeRequest(context, listener);
    }

    @Override
    protected int getRequestTimeOut() {
        return 20000;
    }

    @Override
    protected String getFullUrl(final String url) {
        return url;
    }

    @Override
    public String getTagForLogger() {
        return "API";
    }

    @Override
    public boolean isEnableLogger() {
        return true;
    }

    @Override
    public DMBaseTokenHandler onTokenRefresh() {
        return new DMBaseTokenHandler("refreshUrl") {

            @Override
            protected void onTokenRefreshed(final Context context, final JSONObject jsonObject) {
                //save token in preference
            }

            @Override
            protected void onTokenRefreshFailure(final Context context, final JSONObject jsonObject) {

            }

            @Override
            protected void onNoInternetConnection(final Context context) {

            }
        };
    }
}
