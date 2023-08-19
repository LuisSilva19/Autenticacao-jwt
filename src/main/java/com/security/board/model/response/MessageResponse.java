package com.security.board.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Builder
@ToString
public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}