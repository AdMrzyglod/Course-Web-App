package com.application.mainapp.repository;


import com.application.mainapp.dto.payment.PaymentListResponseDTO;
import com.application.mainapp.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT new com.application.mainapp.dto.payment.PaymentListResponseDTO(pt.paymentID, pt.title, pt.bankAccountNumber, pt.createTimestamp, pt.receiptDate, pt.amount) " +
            "FROM Payment pt WHERE pt.individualUser.platformUserID = :userId ")
    List<PaymentListResponseDTO> findPaymentListResponseDTO(@Param(("userId")) long userId);
}
