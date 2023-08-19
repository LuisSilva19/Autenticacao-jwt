package com.security.board.model.enun;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SubLimitsCreate {

    A("3004020007","00001"),
    B("1001010014","04000");

    private String product;
    private String subProduct;

}
