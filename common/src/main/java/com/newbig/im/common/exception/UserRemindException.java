package com.newbig.im.common.exception;

public class UserRemindException extends RuntimeException {

    public UserRemindException() {
        super();
    }

    public UserRemindException(String message) {
        super(message);
    }

    public UserRemindException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserRemindException(Throwable cause) {
        super(cause);
    }
}
