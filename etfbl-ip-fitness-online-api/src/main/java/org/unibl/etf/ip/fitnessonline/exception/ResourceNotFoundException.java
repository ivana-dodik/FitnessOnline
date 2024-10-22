package org.unibl.etf.ip.fitnessonline.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseHttpException {

    public ResourceNotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
