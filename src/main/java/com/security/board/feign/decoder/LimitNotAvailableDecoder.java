package com.security.board.feign.decoder;

import com.security.board.exception.CustomException;
import com.security.board.exception.ExceptionCodes;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class LimitNotAvailableDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {
        if (HttpStatus.NOT_FOUND.value() == response.status()) {
            return new CustomException(ExceptionCodes.LIMIT_NOT_FOUND);
        }

        if (HttpStatus.BAD_REQUEST.value() == response.status()) {
            return new CustomException(ExceptionCodes.EXISTING_OFFER);
        }

        return defaultErrorDecoder.decode(s, response);
    }
}
