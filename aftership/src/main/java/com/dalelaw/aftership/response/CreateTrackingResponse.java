package com.dalelaw.aftership.response;

import com.dalelaw.aftership.error.AftershipException;
import com.dalelaw.aftership.model.Tracking;

import org.json.JSONObject;

import okhttp3.Response;

/**
 * Created by DaleLaw on 1/3/2016.
 */
public class CreateTrackingResponse extends AftershipResponse {

    private Tracking tracking;

    public CreateTrackingResponse(Response response) throws AftershipException {
        super(response);
    }

    @Override
    protected void parseData(JSONObject data) throws AftershipException {
        try {
            this.tracking = new Tracking(data.optJSONObject("tracking"));
        } catch (Exception e) {
            throw new AftershipException(AftershipException.RESPONSE_PARSE_FAILED, "Error parsing response");
        }
    }

}