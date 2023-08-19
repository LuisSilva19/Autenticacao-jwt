package com.security.board.mapper;

import com.security.board.model.CreationHistory;
import com.security.board.model.request.CreationHistoryRequest;
import com.security.board.model.response.CreationHistoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CreationHistoryMapper {
    public CreationHistoryResponse toCreationHistoryResponse(CreationHistory creationHistory) {
        return CreationHistoryResponse.builder()
                .id(creationHistory.getId())
                .product(creationHistory.getProduct())
                .approvedAmount(creationHistory.getApprovedAmount())
                .reason(creationHistory.getReason())
                .username(creationHistory.getUsername())
                .updateDate(creationHistory.getUpdateDate())
                .documentNumber(creationHistory.getDocumentNumber())
                .build();
    }

    public CreationHistory toCreationHistory(CreationHistoryRequest creationHistory) {
        return CreationHistory.builder()
                .documentNumber(creationHistory.getDocumentNumber())
                .product(creationHistory.getProduct())
                .approvedAmount(creationHistory.getApprovedAmount())
                .reason(creationHistory.getReason())
                .username(creationHistory.getUsername())
                .updateDate(creationHistory.getUpdateDate())
                .build();
    }
}
