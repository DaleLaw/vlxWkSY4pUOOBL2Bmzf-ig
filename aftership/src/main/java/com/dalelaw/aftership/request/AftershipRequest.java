package com.dalelaw.aftership.request;

import com.dalelaw.aftership.error.AftershipException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dalelaw on 22/2/2016.
 */
public abstract class AftershipRequest {


    public static String HTTP_POST = "POST";
    public static String HTTP_GET = "GET";
    public static String HTTP_PUT = "PUT";
    public static String HTTP_PATCH = "PATCH";
    public static String HTTP_DELETE = "DELETE";


    protected String httpMethod;


    public AftershipRequest(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public abstract JSONObject toJson() throws JSONException;

    public String getHttpMethod() {
        return httpMethod;
    }

    public abstract String getRestEndPoint() throws AftershipException;
}
