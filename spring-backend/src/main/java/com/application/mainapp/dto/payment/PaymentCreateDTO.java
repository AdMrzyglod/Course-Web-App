package com.application.mainapp.dto.payment;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;


@Data
@NoArgsConstructor
public class PaymentCreateDTO {

    private String title;

    private String bankAccountNumber;

    private Date receiptDate;

    private BigDecimal amount;

    private String email;

}
