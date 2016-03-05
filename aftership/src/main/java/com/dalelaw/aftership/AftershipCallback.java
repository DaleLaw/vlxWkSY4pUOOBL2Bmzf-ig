package com.dalelaw.aftership;

import com.dalelaw.aftership.error.AftershipException;
import com.dalelaw.aftership.response.AftershipResponse;

/**
 * Abstract Callback for api call.
 * Generic R must inherits from AftershipResponse class
 */
public interface AftershipCallback<R extends AftershipResponse>{

    void onSuccess(R response);

    void onError(AftershipException e);
}
