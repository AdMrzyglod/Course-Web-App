package com.application.mainapp.dto.platformorder;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
public class OrderDetailsResponseDTO {

    private long orderDetailsID;
    private BigDecimal coursePrice;

}
