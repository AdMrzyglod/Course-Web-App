package com.application.mainapp.dto.platformorder;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


@Data
@NoArgsConstructor
public class PlatformOrderResponseDTO {

    private long platformOrderID;
    private Timestamp createTimestamp;
    private BigDecimal totalPrice;
    private List<OrderDetailsResponseDTO> orderDetails;

}
