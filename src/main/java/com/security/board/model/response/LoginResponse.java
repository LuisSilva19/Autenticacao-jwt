package com.security.board.model.response;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record LoginResponse(
 Long id,
 String email,
 String senha,
 LocalDate creationDate,
 String username,
 Boolean admin) {
}
