package com.security.board.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UpdateLimitRequest {
    @CPF
    private String document;
    private String productId;
    private String subProductId;
    private String resultReason;
    private String userName = "MESA";
    private String operationContractId;
    private String operationStatus;
    private Integer approvedAmount;
    private Integer pmtAmount;
    private Integer term;
    private String stampAdditional;
}
