package com.dalelaw.aftership.request;

import com.dalelaw.aftership.error.AftershipException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by DaleLaw on 2/3/2016.
 */
public class GetTrackingRequest extends AftershipRequest {

    private String slug;

    private String tracking_number;

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

    @Override
    public String getRestEndPoint() throws AftershipException{
        if (slug == null || slug.isEmpty()){
            throw new AftershipException(AftershipException.REQUEST_PARSE_FAILED, "slug is not set");
        }
        else if (tracking_number == null || slug.isEmpty()){
            throw new AftershipException(AftershipException.REQUEST_PARSE_FAILED, "tracking_number is not set");
        }
        return "trackings/" + slug + "/" + tracking_number;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTracking_number() {
        return tracking_number;
    }

    public void setTracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
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
