package com.dalelaw.aftership;

import com.dalelaw.aftership.error.AftershipException;
import com.dalelaw.aftership.http.AftershipCallback;
import com.dalelaw.aftership.request.AftershipRequest;
import com.dalelaw.aftership.request.CreateTrackingRequest;
import com.dalelaw.aftership.request.GetTrackingRequest;
import com.dalelaw.aftership.response.AftershipResponse;
import com.dalelaw.aftership.response.CreateTrackingResponse;
import com.dalelaw.aftership.response.GetTrackingResponse;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by dalelaw on 22/2/2016.
 */
public class Aftership{

    public static final String API_VERSION = "4";

    private static final int CONNECT_TIMEOUT = 3000;
    private static final int WRITE_TIMEOUT = 3000;
    private static final int READ_TIMEOUT = 3000;
    private static final int RETRY_COUNT = 3;

    private static final String URL = "https://api.aftership.com/v4";

    private boolean waitIfTooFrequent;

    private final String apiKey;

    private final OkHttpClient client;


    public Aftership(String apiKey) {
        this.apiKey = apiKey;
        this.waitIfTooFrequent = false;
        this.client = new OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(WRITE_TIMEOUT * 2, TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    //Intercept and handle retry cases before further parsing
                    Request request = chain.request();
                    Response response = chain.proceed(request);
                    int tryCount = 0;
                    while (!response.isSuccessful() && tryCount < RETRY_COUNT) {
                        tryCount++;
                        response = chain.proceed(request);
                    }
                    return response;
                }
            })
            .build();

    }

    public void executeSync(AftershipRequest request, AftershipCallback callback){
        try {
            Request okHttpRequest = createOkHttpRequest(request);
            Response response = client.newCall(okHttpRequest).execute();

            AftershipResponse aftershipResponse = createAftershipReponse(request, response);
            callback.onSuccess(aftershipResponse);

        } catch (AftershipException e) {
            //propagate the error
            callback.onError(e);
        } catch (IOException e) {
            callback.onError(new AftershipException(AftershipException.ERROR_GETTING_RESPONSE, "Error getting response from server", e));
        }

    }



    public void executeAsync(final AftershipRequest request, final AftershipCallback callback){
        try {
            client.newCall(createOkHttpRequest(request)).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onError(new AftershipException(e));
                }

                @Override
                public void onResponse(Call call, Response response){
                    try {
                        AftershipResponse aftershipResponse = createAftershipReponse(request, response);
                        callback.onSuccess(aftershipResponse);
                    } catch (AftershipException e) {
                        callback.onError(e);
                    }
                }
            });
        } catch (AftershipException e) {
            //propagate the error
            callback.onError(e);
        }
    }

    private Request createOkHttpRequest(AftershipRequest request) throws AftershipException {
        try {
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), request.toJson().toString());
            Request okHttpRequest = new Request.Builder()
                    .url(URL)
                    .addHeader("aftership-api-key", apiKey)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("aftership-user-agent", "aftership-android-sdk " + BuildConfig.VERSION_NAME)
                    .method(request.getHttpMethod(), body)
                    .build();
            return okHttpRequest;
        } catch (JSONException e) {
            throw new AftershipException(AftershipException.REQUEST_PARSE_FAILED, "Error parsing request.", e);
        }
    }




    private AftershipResponse createAftershipReponse(AftershipRequest request, Response response) throws AftershipException {
        if (request instanceof CreateTrackingRequest){
            return new CreateTrackingResponse(response);
        }
        else if (request instanceof GetTrackingRequest){
            return new GetTrackingResponse(response);
        }
        return null;
    }


    /**
         * Getter & Setter
         **/
    public String getApiKey() {
        return apiKey;
    }

    public boolean isWaitIfTooFrequent() {
        return waitIfTooFrequent;
    }

    public void setWaitIfTooFrequent(boolean waitIfTooFrequent) {
        this.waitIfTooFrequent = waitIfTooFrequent;
    }
}
