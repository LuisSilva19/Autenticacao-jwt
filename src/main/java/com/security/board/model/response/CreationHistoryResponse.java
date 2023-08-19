package com.security.board.model.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CreationHistoryResponse(
 Long id,
 String documentNumber,
 LocalDateTime updateDate,
 String username,
 BigDecimal approvedAmount,
 String product,
 String reason) {}
