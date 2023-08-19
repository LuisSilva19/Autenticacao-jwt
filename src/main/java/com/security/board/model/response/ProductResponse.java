package com.security.board.model.response;

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
public class ProductResponse {

    private String productId;
    private String subProductId;
    private String description;
    private Double contractedAmount;
    private Double usedAmount;
    private int term;
    private Integer delayDays;
    private String sourceCreditLimit;

}
