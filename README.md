### Usage
First initialize the aftership client by the api key.
```
Aftership aftership = new Aftership("API_KEY");
```

Instantiate request and execute it asynchronously:
```
GetTrackingRequest getTrackingRequest = new GetTrackingRequest();
getTrackingRequest.setTracking_number("1111111111");
aftership.executeAsync(getTrackingRequest, new AftershipCallback<GetTrackingResponse>() {
    @Override
    public void onSuccess(GetTrackingResponse response) {
        if (response.isSuccessful()){
          //handle reponse
        }
        else{
          //handle server errors
        }
    }

    @Override
    public void onError(AftershipException e) {
        //handle any exception encountered (not server returned error, may caused by improper request)
    }
});
```

If you would like to execute it synchronously:
```
GetTrackingRequest getTrackingRequest = new GetTrackingRequest();
getTrackingRequest.setTracking_number("1111111111");
aftership.executeSync(getTrackingRequest, new AftershipCallback<GetTrackingResponse>() {
  //**Code**//
);
```



### Run the Tests
```
./gradlew clean testDebug
```

