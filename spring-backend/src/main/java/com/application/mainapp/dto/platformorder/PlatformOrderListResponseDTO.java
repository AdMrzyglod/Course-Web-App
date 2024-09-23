package com.application.mainapp.dto.platformorder;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


@Data
@NoArgsConstructor
public class PlatformOrderListResponseDTO {

    private long platformOrderID;
    private Timestamp createTimestamp;
    private long numberOfAllCodes;
    private BigDecimal totalPrice;
    private List<PlatformOrderCourseResponseDTO> orderCourses;

    public PlatformOrderListResponseDTO(long platformOrderID, Timestamp createTimestamp, long numberOfAllCodes, BigDecimal totalPrice) {
        this.platformOrderID = platformOrderID;
        this.createTimestamp = createTimestamp;
        this.numberOfAllCodes = numberOfAllCodes;
        this.totalPrice = totalPrice;
    }
}
