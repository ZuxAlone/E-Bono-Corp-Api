package com.ebono.bonosapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class GeneralServiceException extends RuntimeException {
    public GeneralServiceException() {
        super();
    }

    public GeneralServiceException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public GeneralServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeneralServiceException(String message) {
        super(message);
    }

    public GeneralServiceException(Throwable cause) {
        super(cause);
    }
}
