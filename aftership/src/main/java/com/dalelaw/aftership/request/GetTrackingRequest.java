package com.dalelaw.aftership.request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by DaleLaw on 2/3/2016.
 */
public class GetTrackingRequest extends AftershipRequest {

    private ArrayList<String> fields;

    private String lang;

    public GetTrackingRequest() {
        super(HTTP_GET);
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        if (fields != null){
            json.put("fields", fields.toString().replaceAll("\\[|\\]|\\s",""));
        }
        if (lang != null){
            json.put("lang", lang);
        }
        return json;
    }

    public ArrayList<String> getFields() {
        return fields;
    }

    public void setFields(ArrayList<String> fields) {
        this.fields = fields;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
