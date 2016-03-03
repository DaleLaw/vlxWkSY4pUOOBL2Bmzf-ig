package com.dalelaw.aftership.response;

import com.dalelaw.aftership.error.AftershipException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Response;

/**
 * Created by DaleLaw on 23/2/2016.
 */
public abstract class AftershipResponse {

    private final int code;
    private final String message;
    private final String type;
    //Just a internal properties holder, don't really need getter & setter
    public Response originalResponse;

    private final int status;
    public final int rateLimitReset;
    public final int rateLimitLimit;
    public final int rateLimitRemaining;
    private final String responseTime;


    public AftershipResponse(Response response) throws AftershipException {
        String responseString;
        try {
            responseString = response.body().string();
        } catch (IOException e) {
            throw new AftershipException(AftershipException.RESPONSE_PARSE_FAILED, "Error parsing response", e);
        }

        //Throw error if it is not Json
        JSONObject json;
        try {
            json = new JSONObject(responseString);
        }
        catch (JSONException e){
            throw new AftershipException(AftershipException.RESPONSE_NOT_JSON, "Response is not json", e);
        }


        //Parse response
        try{
            originalResponse = response;

            Headers headers = response.headers();

            status = Integer.parseInt(headers.get("status"));
            rateLimitReset = Integer.parseInt(headers.get("X-RateLimit-Reset"));
            rateLimitLimit = Integer.parseInt(headers.get("X-RateLimit-Limit"));
            rateLimitRemaining = Integer.parseInt(headers.get("X-RateLimit-Remaining"));
            responseTime = headers.get("X-Response-Time");


            JSONObject meta = json.optJSONObject("meta");

            code = meta.optInt("code");
            message = meta.optString("message");
            type = meta.optString("type");

            JSONObject data = json.optJSONObject("data");
            parseData(data);

        } catch (Exception e) {
            throw new AftershipException(AftershipException.RESPONSE_PARSE_FAILED, "Error parsing response", e);
        }
    }

    protected abstract void parseData(JSONObject data) throws AftershipException;

}
