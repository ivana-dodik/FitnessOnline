package org.unibl.etf.ip.fitnessonline.exception;

import org.springframework.http.HttpStatus;

public class UnauthenticatedException extends BaseHttpException {

    public UnauthenticatedException() {
        super(HttpStatus.UNAUTHORIZED);
    }

    public UnauthenticatedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}