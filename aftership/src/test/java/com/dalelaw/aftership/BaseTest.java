package com.dalelaw.aftership;

import java.util.concurrent.CountDownLatch;

/**
 * Created by DaleLaw on 3/3/2016.
 */
public class BaseTest {

    protected final CountDownLatch signal = new CountDownLatch(1);

}
