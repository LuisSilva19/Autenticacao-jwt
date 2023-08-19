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
public class CreateLimitRequest {
    @CPF
    private String document;
    private String productId;
    private String subProductId;
    private String user = "MESA";
    private Integer availableAmount;
    private Integer maxPmtAmount;
    private Integer maxTerm;
    private String expiryDateSublimit;
    private String stampAdditional;
    private String sourceOfAction;
}
