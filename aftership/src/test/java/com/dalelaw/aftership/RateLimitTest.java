package com.dalelaw.aftership;

import com.dalelaw.aftership.error.AftershipException;
import com.dalelaw.aftership.request.GetTrackingRequest;
import com.dalelaw.aftership.response.GetTrackingResponse;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

/**
 * Created by dalelaw on 5/3/2016.
 */
public class RateLimitTest extends BaseTest {

    private volatile Throwable error;
    private Aftership aftership;

    @Before
    public void setUp(){
        aftership = new Aftership(TestConfig.API_KEY);
    }


    @Test
    public void testRateLimit() throws Throwable {
        MockWebServer server = new MockWebServer();
        //Mock 2 responses: first one with status code 429 and second one with status code 200
        MockResponse response1 = new MockResponse()
                .setResponseCode(429)
                .setHeader("Date", new Date(System.currentTimeMillis()).toGMTString())
                .setHeader("x-ratelimit-limit", "600")
                .setHeader("x-ratelimit-remaining", "599")
                .setHeader("x-ratelimit-reset", Long.toString(System.currentTimeMillis() / 1000 + 5))
                .setBody("{\"meta\":{\"code\":429,\"type\":\"BadRequest\",\"message\":\"Too many requests.\"},\"data\":{}}");
        server.enqueue(response1);




        MockResponse response2 = new MockResponse()
                .setResponseCode(200)
                .setHeader("Date", new Date(System.currentTimeMillis()).toGMTString())
                .setHeader("x-ratelimit-limit", "600")
                .setHeader("x-ratelimit-remaining", "599")
                .setHeader("x-ratelimit-reset", Long.toString(System.currentTimeMillis() / 1000 + 60))
                .setBody("{\"meta\":{\"code\":200},\"data\":{\"tracking\":{\"id\":\"56d949369c69b7490dc78d8f\",\"created_at\":\"2016-03-04T08:37:10+00:00\",\"updated_at\":\"2016-03-04T08:37:11+00:00\",\"last_updated_at\":\"2016-03-04T08:37:11+00:00\",\"tracking_number\":\"1111111111\",\"slug\":\"dhl\",\"active\":false,\"android\":[],\"custom_fields\":null,\"customer_name\":null,\"delivery_time\":19,\"destination_country_iso3\":\"ESP\",\"emails\":[],\"expected_delivery\":null,\"ios\":[],\"note\":null,\"order_id\":null,\"order_id_path\":null,\"origin_country_iso3\":null,\"shipment_package_count\":1,\"shipment_pickup_date\":\"2016-01-28T16:40:00\",\"shipment_delivery_date\":\"2016-02-16T13:03:00\",\"shipment_type\":null,\"shipment_weight\":null,\"shipment_weight_unit\":null,\"signed_by\":\"JUAN\",\"smses\":[],\"source\":\"api\",\"tag\":\"Delivered\",\"title\":\"1111111111\",\"tracked_count\":1,\"unique_token\":\"Nyv8dVGhx\",\"checkpoints\":[{\"slug\":\"dhl\",\"city\":\"MADRID\",\"created_at\":\"2016-03-04T08:37:11+00:00\",\"location\":\"MADRID - SPAIN\",\"country_name\":null,\"message\":\"Awaiting collection by recipient as requested\",\"country_iso3\":\"ESP\",\"tag\":\"InTransit\",\"checkpoint_time\":\"2016-01-28T16:40:00\",\"coordinates\":[],\"state\":null,\"zip\":null},{\"slug\":\"dhl\",\"city\":\"MADRID\",\"created_at\":\"2016-03-04T08:37:11+00:00\",\"location\":\"MADRID - SPAIN\",\"country_name\":null,\"message\":\"Delivered - Signed for by JUAN\",\"country_iso3\":\"ESP\",\"tag\":\"Delivered\",\"checkpoint_time\":\"2016-01-28T16:41:00\",\"coordinates\":[],\"state\":null,\"zip\":null},{\"slug\":\"dhl\",\"city\":\"MADRID\",\"created_at\":\"2016-03-04T08:37:11+00:00\",\"location\":\"MADRID - SPAIN\",\"country_name\":null,\"message\":\"Awaiting collection by recipient as requested\",\"country_iso3\":\"ESP\",\"tag\":\"InTransit\",\"checkpoint_time\":\"2016-02-10T13:32:00\",\"coordinates\":[],\"state\":null,\"zip\":null},{\"slug\":\"dhl\",\"city\":\"MADRID\",\"created_at\":\"2016-03-04T08:37:11+00:00\",\"location\":\"MADRID - SPAIN\",\"country_name\":null,\"message\":\"Delivered - Signed for by BEATRIZ\",\"country_iso3\":\"ESP\",\"tag\":\"Delivered\",\"checkpoint_time\":\"2016-02-10T13:34:00\",\"coordinates\":[],\"state\":null,\"zip\":null},{\"slug\":\"dhl\",\"city\":\"MADRID\",\"created_at\":\"2016-03-04T08:37:11+00:00\",\"location\":\"MADRID - SPAIN\",\"country_name\":null,\"message\":\"Awaiting collection by recipient as requested\",\"country_iso3\":\"ESP\",\"tag\":\"InTransit\",\"checkpoint_time\":\"2016-02-10T16:48:00\",\"coordinates\":[],\"state\":null,\"zip\":null},{\"slug\":\"dhl\",\"city\":\"MADRID\",\"created_at\":\"2016-03-04T08:37:11+00:00\",\"location\":\"MADRID - SPAIN\",\"country_name\":null,\"message\":\"Delivered - Signed for by RAMON\",\"country_iso3\":\"ESP\",\"tag\":\"Delivered\",\"checkpoint_time\":\"2016-02-10T16:49:00\",\"coordinates\":[],\"state\":null,\"zip\":null},{\"slug\":\"dhl\",\"city\":\"MADRID\",\"created_at\":\"2016-03-04T08:37:11+00:00\",\"location\":\"MADRID - SPAIN\",\"country_name\":null,\"message\":\"Awaiting collection by recipient as requested\",\"country_iso3\":\"ESP\",\"tag\":\"InTransit\",\"checkpoint_time\":\"2016-02-11T10:40:00\",\"coordinates\":[],\"state\":null,\"zip\":null},{\"slug\":\"dhl\",\"city\":\"MADRID\",\"created_at\":\"2016-03-04T08:37:11+00:00\",\"location\":\"MADRID - SPAIN\",\"country_name\":null,\"message\":\"Delivered - Signed for by Marcos\",\"country_iso3\":\"ESP\",\"tag\":\"Delivered\",\"checkpoint_time\":\"2016-02-12T10:24:00\",\"coordinates\":[],\"state\":null,\"zip\":null},{\"slug\":\"dhl\",\"city\":\"MADRID\",\"created_at\":\"2016-03-04T08:37:11+00:00\",\"location\":\"MADRID - SPAIN\",\"country_name\":null,\"message\":\"Awaiting collection by recipient as requested\",\"country_iso3\":\"ESP\",\"tag\":\"InTransit\",\"checkpoint_time\":\"2016-02-16T12:51:00\",\"coordinates\":[],\"state\":null,\"zip\":null},{\"slug\":\"dhl\",\"city\":\"MADRID\",\"created_at\":\"2016-03-04T08:37:11+00:00\",\"location\":\"MADRID - SPAIN\",\"country_name\":null,\"message\":\"Delivered - Signed for by Alberto\",\"country_iso3\":\"ESP\",\"tag\":\"Delivered\",\"checkpoint_time\":\"2016-02-16T13:03:00\",\"coordinates\":[],\"state\":null,\"zip\":null}],\"tracking_account_number\":null,\"tracking_destination_country\":null,\"tracking_key\":null,\"tracking_postal_code\":null,\"tracking_ship_date\":null}}}");

        server.enqueue(response2);

        server.start();



        aftership.setUrl(server.getUrl("").toString() + "/");



        aftership.setWaitIfRateLimitReached(true);

        GetTrackingRequest getTrackingRequest = new GetTrackingRequest();
        getTrackingRequest.setSlug("dhl");
        getTrackingRequest.setTracking_number("1111111111");

        aftership.executeAsync(getTrackingRequest, new AftershipCallback<GetTrackingResponse>() {
            @Override
            public void onSuccess(GetTrackingResponse response) {
                try {
                    assertNotNull(response.getTracking());
                } catch (AssertionError e) {
                    error = e;
                } finally {
                    signal.countDown();
                }
            }

            @Override
            public void onError(AftershipException e) {
                error = e;
                signal.countDown();
            }
        });


        signal.await();
        if (error != null){
            throw error;
        }

    }

}
