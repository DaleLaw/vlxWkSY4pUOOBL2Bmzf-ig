package com.dalelaw.aftership;

import com.dalelaw.aftership.error.AftershipException;
import com.dalelaw.aftership.model.Tracking;
import com.dalelaw.aftership.request.CreateTrackingRequest;
import com.dalelaw.aftership.request.GetTrackingRequest;
import com.dalelaw.aftership.response.CreateTrackingResponse;
import com.dalelaw.aftership.response.GetTrackingResponse;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by DaleLaw on 3/3/2016.
 */
public class TrackingTest extends BaseTest {

    private volatile Throwable error;
    private Aftership aftership;

    @Before
    public void setUp(){
        aftership = new Aftership(TestConfig.API_KEY);
    }


    @Test
    public void testCreateTrackingRequestSync() throws Throwable {
        String trackingNumber = "4444444444";
        Tracking tracking = new Tracking();
        tracking.setTrackingNumber(trackingNumber);

        final boolean[] finish = {false};

        CreateTrackingRequest createTrackingRequest = new CreateTrackingRequest(tracking);
        aftership.executeSync(createTrackingRequest, new AftershipCallback<CreateTrackingResponse>() {
            @Override
            public void onSuccess(CreateTrackingResponse response) {
                try {
                    assertTrue(!response.isSuccessful());
                } catch (AssertionError e) {
                    error = e;
                } finally {
                    finish[0] = true;
                }
            }

            @Override
            public void onError(AftershipException e) {
                error = e;
                finish[0] = true;
            }
        });

        assertTrue(finish[0]);
        if (error != null){
            throw error;
        }
    }

    @Test
    public void testCreateTrackingRequestAsync() throws Throwable {
        String trackingNumber = "2222222222";

        //Test success case
        Tracking tracking = new Tracking();
        tracking.setTrackingNumber(trackingNumber);
        CreateTrackingRequest createTrackingRequest = new CreateTrackingRequest(tracking);
        aftership.executeAsync(createTrackingRequest, new AftershipCallback<CreateTrackingResponse>() {
            @Override
            public void onSuccess(CreateTrackingResponse response) {
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






    @Test
    public void testCreateTrackingRequestInvalidArgument() throws Throwable {

        //Test fail case
        CreateTrackingRequest createTrackingRequest2 = new CreateTrackingRequest(new Tracking());
        aftership.executeAsync(createTrackingRequest2, new AftershipCallback<CreateTrackingResponse>() {
            @Override
            public void onSuccess(CreateTrackingResponse response) {
                try {
                    assertEquals(response.getStatusCode(), 400);
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



    @Test
    public void testGetTrackingRequest() throws Throwable {
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


    @Test
    public void testGetTrackingRequestInvalidArgument() throws Throwable {
        GetTrackingRequest getTrackingRequest = new GetTrackingRequest();
//        getTrackingRequest.setSlug("dhl");
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
                assertEquals(e.getMessage(), "slug is not set");
                signal.countDown();
            }
        });


        signal.await();
        if (error != null){
            throw error;
        }
    }

}
