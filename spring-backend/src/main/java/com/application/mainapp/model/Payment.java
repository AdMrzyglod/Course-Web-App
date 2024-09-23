package com.application.mainapp.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentID;

    private String title;

    private String bankAccountNumber;

    @CreationTimestamp
    private Timestamp createTimestamp;

    private Date receiptDate;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name="platformUserID")
    private IndividualUser individualUser;

}
