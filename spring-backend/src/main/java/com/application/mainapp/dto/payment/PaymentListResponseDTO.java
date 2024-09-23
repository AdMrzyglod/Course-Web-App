package com.application.mainapp.dto.payment;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
public class PaymentListResponseDTO {

    private Long paymentID;

    private String title;

    private String bankAccountNumber;

    private Timestamp createTimestamp;

    private Date receiptDate;

    private BigDecimal amount;

    public PaymentListResponseDTO(Long paymentID, String title, String bankAccountNumber, Timestamp createTimestamp, Date receiptDate, BigDecimal amount) {
        this.paymentID = paymentID;
        this.title = title;
        this.bankAccountNumber = bankAccountNumber;
        this.createTimestamp = createTimestamp;
        this.receiptDate = receiptDate;
        this.amount = amount;
    }
}
