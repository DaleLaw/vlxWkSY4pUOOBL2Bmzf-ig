package com.dalelaw.aftership.model;

import com.dalelaw.aftership.utils.DateUtil;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by DaleLaw on 22/2/2016.
 */
public class Checkpoint {

    //Follows the naming and sequence of documentation
    private Date createdAt;
    private String slug;
    private String checkpointTime;
    private String city;
    private String countryISO3;
    private String countryName;
    private String message;
    private String state;
    private String tag;
    private String zip;


    public Checkpoint(JSONObject checkPointJSON) throws ParseException {
        this.createdAt = DateUtil.parseDate(checkPointJSON.optString("created_at"));
        this.checkpointTime = checkPointJSON.optString("checkpoint_time");
        this.city = checkPointJSON.optString("city");
        this.countryISO3 = checkPointJSON.optString("country_iso3");
        this.countryName = checkPointJSON.optString("country_name");
        this.message = checkPointJSON.optString("message");
        this.state = checkPointJSON.optString("state");
        this.tag = checkPointJSON.optString("tag");
        this.zip = checkPointJSON.optString("zip");

    }
}



