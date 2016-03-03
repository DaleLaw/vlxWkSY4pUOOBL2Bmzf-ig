package com.dalelaw.aftership.model;


import com.dalelaw.aftership.utils.DateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by dalelaw on 22/2/2016.
 */
public class Tracking {

    //Follows the naming and sequence of documentation
    private Date createdAt;
    private Date updatedAt;
    private String id;
    private String trackingPostalCode;
    private String trackingShipDate;    //YYYYMMDD
    private String trackingAccountNumber;
    //@
    private String trackingKey;
    //@
    private String trackingDestinationCountry;
    private String slug;
    private boolean active;
    //@
    private String android;
    private Map<String, String> customFields;
    private String customerName;
    private int deliveryTime;
    private String destinationCountryISO3;
    private List<String> emails;
    private String expectedDelivery;
    //@
    private String ios;
    private String orderID;
    private String orderIDPath;
    private String originCountryISO3;
    private String uniqueToken;
    private int shipmentPackageCount;
    private String shipmentType;
    private int shipmentWeight;
    private int shipmentWeightUnit;
    private String signedBy;
    private List<String> smses;
    private String source;
    private String tag;
    private String title;
    private int trackedCount;
    List<Checkpoint> checkpoints;

    /**Tracking number of a shipment. Duplicate tracking numbers, or tracking number with invalid tracking
     * number format will not be accepted. */
    private String trackingNumber;      //?

    public Tracking() {
    }


//    public Tracking(String trackingNumber) {
//        this.trackingNumber = trackingNumber;
//        this.title = trackingNumber;
//    }

    public Tracking(JSONObject json) throws JSONException, ParseException {
        this.id = json.optString("id");
        this.trackingNumber = json.optString("tracking_number");
        this.slug= json.optString("slug");
        this.title = json.optString("title");
        this.customerName = json.optString("customer_name");
        this.deliveryTime = json.optInt("delivery_time");

        this.destinationCountryISO3 = json.optString("destination_country_iso3");
        this.orderID = json.optString("order_id");
        this.orderIDPath = json.optString("order_id_path");

        this.trackingAccountNumber =
                json.optString("tracking_account_number");
        this.trackingPostalCode =
                json.optString("tracking_postal_code");
        this.trackingShipDate =
                json.optString("tracking_ship_date");

        JSONArray smsesArray =json.optJSONArray("smses");
        if(!json.isNull("smses") && smsesArray.length()!=0){
            this.smses = new ArrayList<>();
            for (int i=0;i<smsesArray.length();i++){
                this.smses.add(smsesArray.get(i).toString());
            }
        }

        JSONArray emailsArray =  json.optJSONArray("emails");
        if(emailsArray!=null && emailsArray.length()!=0){
            this.emails = new ArrayList<>();
            for (int i=0;i<emailsArray.length();i++){
                this.emails.add(emailsArray.get(i).toString());
            }
        }

        JSONObject customFieldsJSON =json.optJSONObject("custom_fields");
        if(customFieldsJSON!=null){
            this.customFields = new HashMap<>();
            Iterator<String> keys = json.keys();
            while( keys.hasNext() ) {
                String key = keys.next();
                this.customFields.put(key,customFieldsJSON.optString(key));
            }
        }


        this.createdAt = DateUtil.parseDate(json.optString("created_at"));
        this.updatedAt = DateUtil.parseDate(json.optString("updated_at"));
        this.expectedDelivery = json.optString("expected_delivery");

        this.active = json.optBoolean("active");
        this.originCountryISO3 = json.optString("origin_country_iso3");
        this.shipmentPackageCount =  json.optInt("shipment_package_count");
        this.shipmentType = json.optString("shipment_type");
        this.signedBy = json.optString("signed_by");
        this.source = json.optString("source");
        this.tag = json.optString("tag");
        this.trackedCount = json.optInt("tracked_count");
        this.uniqueToken = json.optString("unique_token");


        JSONArray checkpointsArray =  json.optJSONArray("checkpoints");
        if(!json.isNull("checkpoints") && checkpointsArray.length()!=0){
            this.checkpoints = new ArrayList<>();
            for (int i=0;i<checkpointsArray.length();i++){
                this.checkpoints.add(new Checkpoint((JSONObject)checkpointsArray.get(i)));
            }
        }
    }

    @Override
    public String toString() {
        return "Tracking{" +
                "createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", id='" + id + '\'' +
                ", trackingPostalCode='" + trackingPostalCode + '\'' +
                ", trackingShipDate='" + trackingShipDate + '\'' +
                ", trackingAccountNumber='" + trackingAccountNumber + '\'' +
                ", trackingKey='" + trackingKey + '\'' +
                ", trackingDestinationCountry='" + trackingDestinationCountry + '\'' +
                ", slug='" + slug + '\'' +
                ", active=" + active +
                ", android='" + android + '\'' +
                ", customFields=" + customFields +
                ", customerName='" + customerName + '\'' +
                ", deliveryTime=" + deliveryTime +
                ", destinationCountryISO3='" + destinationCountryISO3 + '\'' +
                ", emails=" + emails +
                ", expectedDelivery='" + expectedDelivery + '\'' +
                ", ios='" + ios + '\'' +
                ", orderID='" + orderID + '\'' +
                ", orderIDPath='" + orderIDPath + '\'' +
                ", originCountryISO3='" + originCountryISO3 + '\'' +
                ", uniqueToken='" + uniqueToken + '\'' +
                ", shipmentPackageCount=" + shipmentPackageCount +
                ", shipmentType='" + shipmentType + '\'' +
                ", shipmentWeight=" + shipmentWeight +
                ", shipmentWeightUnit=" + shipmentWeightUnit +
                ", signedBy='" + signedBy + '\'' +
                ", smses=" + smses +
                ", source='" + source + '\'' +
                ", tag=" + tag +
                ", title='" + title + '\'' +
                ", trackedCount=" + trackedCount +
                ", checkpoints=" + checkpoints +
                ", trackingNumber='" + trackingNumber + '\'' +
                '}';
    }

    public JSONObject toJson() throws JSONException{
        JSONObject globalJSON = new JSONObject();
        JSONObject trackingJSON = new JSONObject();
        JSONObject customFieldsJSON;

        trackingJSON.put("tracking_number", this.trackingNumber);
        if (this.slug != null) trackingJSON.put("slug", this.slug);
        if (this.title != null) trackingJSON.put("title", this.title);
        if (this.emails != null) {
            JSONArray emailsJSON = new JSONArray(this.emails);
            trackingJSON.put("emails", emailsJSON);
        }
        if (this.smses != null) {
            JSONArray smsesJSON = new JSONArray(this.smses);
            trackingJSON.put("smses", smsesJSON);
        }
        if (this.customerName != null) trackingJSON.put("customer_name", this.customerName);
        if (this.destinationCountryISO3 != null)
            trackingJSON.put("destination_country_iso3", this.destinationCountryISO3.toString());
        if (this.orderID != null) trackingJSON.put("order_id", this.orderID);
        if (this.orderIDPath != null) trackingJSON.put("order_id_path", this.orderIDPath);

        if (this.trackingAccountNumber != null) trackingJSON.put("tracking_account_number", this.trackingAccountNumber);
        if (this.trackingPostalCode != null) trackingJSON.put("tracking_postal_code", this.trackingPostalCode);
        if (this.trackingShipDate != null) trackingJSON.put("tracking_ship_date", this.trackingShipDate);

        if (this.customFields != null) {
            customFieldsJSON = new JSONObject();

            for (Map.Entry<String, String> entry : this.customFields.entrySet()) {
                customFieldsJSON.put(entry.getKey(), entry.getValue());
            }
            trackingJSON.put("custom_fields", customFieldsJSON);
        }
        globalJSON.put("tracking", trackingJSON);

        return globalJSON;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrackingPostalCode() {
        return trackingPostalCode;
    }

    public void setTrackingPostalCode(String trackingPostalCode) {
        this.trackingPostalCode = trackingPostalCode;
    }

    public String getTrackingShipDate() {
        return trackingShipDate;
    }

    public void setTrackingShipDate(String trackingShipDate) {
        this.trackingShipDate = trackingShipDate;
    }

    public String getTrackingAccountNumber() {
        return trackingAccountNumber;
    }

    public void setTrackingAccountNumber(String trackingAccountNumber) {
        this.trackingAccountNumber = trackingAccountNumber;
    }

    public String getTrackingKey() {
        return trackingKey;
    }

    public void setTrackingKey(String trackingKey) {
        this.trackingKey = trackingKey;
    }

    public String getTrackingDestinationCountry() {
        return trackingDestinationCountry;
    }

    public void setTrackingDestinationCountry(String trackingDestinationCountry) {
        this.trackingDestinationCountry = trackingDestinationCountry;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAndroid() {
        return android;
    }

    public void setAndroid(String android) {
        this.android = android;
    }

    public Map<String, String> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(Map<String, String> customFields) {
        this.customFields = customFields;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDestinationCountryISO3() {
        return destinationCountryISO3;
    }

    public void setDestinationCountryISO3(String destinationCountryISO3) {
        this.destinationCountryISO3 = destinationCountryISO3;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public String getExpectedDelivery() {
        return expectedDelivery;
    }

    public void setExpectedDelivery(String expectedDelivery) {
        this.expectedDelivery = expectedDelivery;
    }

    public String getIos() {
        return ios;
    }

    public void setIos(String ios) {
        this.ios = ios;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderIDPath() {
        return orderIDPath;
    }

    public void setOrderIDPath(String orderIDPath) {
        this.orderIDPath = orderIDPath;
    }

    public String getOriginCountryISO3() {
        return originCountryISO3;
    }

    public void setOriginCountryISO3(String originCountryISO3) {
        this.originCountryISO3 = originCountryISO3;
    }

    public String getUniqueToken() {
        return uniqueToken;
    }

    public void setUniqueToken(String uniqueToken) {
        this.uniqueToken = uniqueToken;
    }

    public int getShipmentPackageCount() {
        return shipmentPackageCount;
    }

    public void setShipmentPackageCount(int shipmentPackageCount) {
        this.shipmentPackageCount = shipmentPackageCount;
    }

    public String getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(String shipmentType) {
        this.shipmentType = shipmentType;
    }

    public int getShipmentWeight() {
        return shipmentWeight;
    }

    public void setShipmentWeight(int shipmentWeight) {
        this.shipmentWeight = shipmentWeight;
    }

    public int getShipmentWeightUnit() {
        return shipmentWeightUnit;
    }

    public void setShipmentWeightUnit(int shipmentWeightUnit) {
        this.shipmentWeightUnit = shipmentWeightUnit;
    }

    public String getSignedBy() {
        return signedBy;
    }

    public void setSignedBy(String signedBy) {
        this.signedBy = signedBy;
    }

    public List<String> getSmses() {
        return smses;
    }

    public void setSmses(List<String> smses) {
        this.smses = smses;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTrackedCount() {
        return trackedCount;
    }

    public void setTrackedCount(int trackedCount) {
        this.trackedCount = trackedCount;
    }

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(List<Checkpoint> checkpoints) {
        this.checkpoints = checkpoints;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }


    public static class Tag {
        public String PENDING = "Pending";
        public String INFO_RECEIVED = "InfoReceived";
        public String IN_TRANSIT = "InTransit";
        public String OUT_FOR_DELIBERY = "OutForDelivery";
        public String ATTEMPT_FAIL = "AttemptFail";
        public String DELIVERED = "Delivered";
        public String EXCEPTION = "Exception";
        public String EXPIRED = "Expired";
    }
}