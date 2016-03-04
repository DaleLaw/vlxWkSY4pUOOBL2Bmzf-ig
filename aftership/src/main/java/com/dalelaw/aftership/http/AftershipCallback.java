package com.dalelaw.aftership.http;

import com.dalelaw.aftership.R;
import com.dalelaw.aftership.error.AftershipException;
import com.dalelaw.aftership.response.AftershipResponse;

/**
 * Created by DaleLaw on 24/2/2016.
 */
public interface AftershipCallback<R>{

    void onSuccess(R response);

    void onError(AftershipException e);
}
