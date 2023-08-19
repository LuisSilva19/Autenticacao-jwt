package com.security.board.model.enun;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SubLimitId {

    A(3,"1001010004","00001"),
    B(266,"1001010014","04000"),
    C(1,"1000","1000"),
    D(106,"3004020007","00001"),
    E(2,"230","005"),
    F(6,"1007010001","00001"),
    G(3,"1001010014","04002"),
    H(6,"1007014012","4013"),
    I(126,"1001010004","2"),
    J(226,"1007014012","4015"),
    K(226,"1007010001","8153");

    private Integer sublimitModel;
    private String product;
    private String subProduct;

}
