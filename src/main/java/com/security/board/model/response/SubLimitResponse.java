package com.security.board.model.response;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SubLimitResponse {

    private Double availablePmtAmount;
    private BigDecimal availableAmount;
    private Integer subLimitModelId;
    private String sourceCreditLimit;
    private String description;
    private Double approvedAmount;
    private Double contractedAmount;
    private Integer maxTerm;
    private Integer maxDelayDays;
    private Double usedAmount;
    private List<ProductResponse> products;


}
