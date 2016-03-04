package com.dalelaw.aftership.request;

import com.dalelaw.aftership.error.AftershipException;
import com.dalelaw.aftership.model.Tracking;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DaleLaw on 25/2/2016.
 */
public class CreateTrackingRequest extends AftershipRequest {

    public static final String END_POINT = "trackings";

    private final Tracking tracking;

    public CreateTrackingRequest(Tracking tracking) {
        super(HTTP_POST);
        this.tracking = tracking;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("tracking", tracking.toJson());
        return json;
    }

    @Override
    public String getRestEndPoint() throws AftershipException {
        return END_POINT;
    }
}
