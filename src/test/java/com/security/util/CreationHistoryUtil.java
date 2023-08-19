package com.security.util;

import com.security.board.model.CreationHistory;
import com.security.board.model.response.CreationHistoryResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public class CreationHistoryUtil {

    public CreationHistory getCreationHistory(Long id){
        return CreationHistory.builder()
                .id(id)
                .approvedAmount(new BigDecimal("3000"))
                .documentNumber("3663911489")
                .username("username")
                .reason("reason")
                .updateDate(LocalDateTime.now())
                .product("product")
                .build();
    }

    public CreationHistoryResponse getCreationHistoryResponse(Long id){
        return CreationHistoryResponse.builder()
                .id(id)
                .approvedAmount(new BigDecimal("3000"))
                .documentNumber("3663911489")
                .username("username")
                .reason("reason")
                .updateDate(LocalDateTime.now())
                .product("product")
                .build();
    }
}
