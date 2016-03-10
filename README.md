![Travis CI](https://travis-ci.org/developerdalelaw/vlxWkSY4pUOOBL2Bmzf-ig.svg?branch=master)

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

### Rate Limit Handling
Set this flag if you would like to pend the request when rate limit is reached(Default value is true). Set to false if you would like to handle it manually. An AftershipException would be thrown instead.
```
aftership.setWaitIfRateLimitReached(true);
```



### Run the Tests
```
./gradlew clean testDebug
```

### Code structure
```
Main Classes:
1. Aftership: This class is responsible for managing and executing calls with respect to one API key
2. AftershipRequest: Abstract class for construction of HTTP request
3. AftershipResponse: Abstract class for parsing HTTP response
4. AftershipCallback: Callback for api call.
5. AftershipException: Internal exception encountered will be wrapped by AftershipException and be thrown.
```
