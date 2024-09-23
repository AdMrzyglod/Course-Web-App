package com.application.mainapp.dto.platformorder;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class PlatformUserOrderListResponseDTO extends PlatformOrderListResponseDTO {

    private String userEmail;

    public PlatformUserOrderListResponseDTO(long platformOrderID, Timestamp createTimestamp, long numberOfAllCodes, BigDecimal totalPrice, String userEmail) {
        super(platformOrderID, createTimestamp, numberOfAllCodes, totalPrice);
        this.userEmail = userEmail;
    }
}
