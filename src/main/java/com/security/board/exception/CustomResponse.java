package com.security.board.exception;


import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse {
    private List<ErrorResponse> errors = new ArrayList<>();

    public CustomResponse(Integer status, String message) {
        this.errors.add(ErrorResponse.builder()
                .message(message)
                .status(status)
                .build());
    }
}
