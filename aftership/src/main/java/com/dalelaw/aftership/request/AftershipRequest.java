package com.dalelaw.aftership.request;

import com.dalelaw.aftership.error.AftershipException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Abstract class for construction of HTTP request
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
