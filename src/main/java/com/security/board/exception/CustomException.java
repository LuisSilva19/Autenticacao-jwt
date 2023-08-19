package com.security.board.exception;

import java.io.Serial;
import lombok.Getter;

public class CustomException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1958853396653067230L;

    @Getter
    private final transient CustomResponse response;

    @Getter
    private final ExceptionCodes exceptionCodes;

    @Getter
    private final Integer status;

    public CustomException(ExceptionCodes code) {
        super(code.getMessage());
        this.exceptionCodes = code;
        this.status = code.getStatus();
        response = new CustomResponse(code.getStatus(), code.getMessage());
    }

    public CustomException(ExceptionCodes code, Object ...params) {
        super(code.getMessage());
        this.exceptionCodes = code;
        this.response = new CustomResponse(code.getStatus(), String.format(code.getMessage(), params));
        status = code.getStatus();
    }

}
