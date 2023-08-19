package com.security.board.model.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreationHistoryRequest {
    @NotBlank
    private String documentNumber;
    @NotNull
    private LocalDateTime updateDate;
    @NotBlank
    private String username;
    @DecimalMin(value = "0.0")
    private BigDecimal approvedAmount;
    @NotBlank
    private String product;
    @NotBlank
    @Size(max = 200)
    private String reason;
}
