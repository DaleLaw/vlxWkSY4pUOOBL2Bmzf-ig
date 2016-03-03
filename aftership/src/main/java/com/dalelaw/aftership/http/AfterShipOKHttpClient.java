package com.dalelaw.aftership.http;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.*;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by DaleLaw on 23/2/2016.
 */
public class AfterShipOKHttpClient {

    public static final int RETRIES = 3;
    public static final int TIMEOUT = 5 * 1000;

    private volatile static OkHttpClient client;

    private static OkHttpClient.Builder clientBuilder() {
        return new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(TIMEOUT * 2, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        // try the request
                        Response response = chain.proceed(request);
                        int tryCount = 0;
                        while (!response.isSuccessful() && tryCount < RETRIES) {
                            tryCount++;
                            // retry the request
                            response = chain.proceed(request);
                        }
                        // otherwise just pass the original response on
                        return response;
                    }
                });
    }

    public static OkHttpClient client() {
        OkHttpClient localInstance = client;
        if (localInstance == null) {
            synchronized (OkHttpClient.class) {
                localInstance = client;
                if (localInstance == null) {
                    client = localInstance = clientBuilder().build();
                }
            }
        }
        return localInstance;
    }

    private static Call post(String url, RequestBody requestBody, Callback callback) {
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).post(requestBody).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }
}
