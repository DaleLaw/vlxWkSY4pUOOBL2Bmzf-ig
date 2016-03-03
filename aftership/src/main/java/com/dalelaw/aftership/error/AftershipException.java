package com.dalelaw.aftership.error;

/**
 * Created by DaleLaw on 22/2/2016.
 */
public class AftershipException extends Exception{

    public static final int OTHER_CAUSE = -1;

    public static final int REQUEST_PARSE_FAILED = 100;
    public static final int ERROR_GETTING_RESPONSE = 101;
    public static final int RESPONSE_NOT_JSON = 102;
    public static final int RESPONSE_PARSE_FAILED = 103;


    private int type;

    public AftershipException() {

    }

    public AftershipException(int type, String message) {
        super(message);
        this.type = type;
    }

    public AftershipException(String message) {
        super(message);
        this.type = OTHER_CAUSE;
    }

    //For internal error
    public AftershipException(int type, String message, Throwable cause) {
        super(message, cause);
        this.type = type;
    }

    public AftershipException(Throwable cause) {
        super(cause);
        type = OTHER_CAUSE;
    }

    public int getType() {
        return type;
    }

}
