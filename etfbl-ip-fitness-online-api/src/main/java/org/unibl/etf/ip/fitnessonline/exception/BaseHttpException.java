package org.unibl.etf.ip.fitnessonline.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
public abstract class BaseHttpException extends RuntimeException {

    private Object data = null;
    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public BaseHttpException(HttpStatus status) {
        if (status != null) {
            this.status = status;
        }
    }

    public BaseHttpException(HttpStatus status, Object data) {
        if (status != null) {
            this.status = status;
        }
        this.data = data;
    }
}
