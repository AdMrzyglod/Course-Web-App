package com.application.mainapp.service;


import com.application.mainapp.dto.payment.PaymentCreateDTO;
import com.application.mainapp.dto.payment.PaymentListResponseDTO;
import com.application.mainapp.dto.payment.PaymentResponseDTO;
import com.application.mainapp.model.IndividualUser;
import com.application.mainapp.model.Payment;
import com.application.mainapp.repository.IndividualUserRepository;
import com.application.mainapp.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final IndividualUserRepository individualUserRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public PaymentService(PaymentRepository paymentRepository, IndividualUserRepository individualUserRepository, ModelMapper modelMapper) {
        this.paymentRepository = paymentRepository;
        this.individualUserRepository = individualUserRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void savePayment(PaymentCreateDTO paymentCreateDTO) {
        Optional<IndividualUser> individualUserOptional = this.individualUserRepository.findByEmail(paymentCreateDTO.getEmail());
        if(individualUserOptional.isEmpty()){
            throw new RuntimeException("User not exists");
        }

        IndividualUser individualUser = individualUserOptional.get();

        if(paymentCreateDTO.getAmount().compareTo(BigDecimal.ZERO)<=0){
            throw new RuntimeException("The amount must be greater than 0");
        }

        Payment payment = this.modelMapper.map(paymentCreateDTO, Payment.class);
        payment.setIndividualUser(individualUser);
        individualUser.setMoney(individualUser.getMoney().add(paymentCreateDTO.getAmount()));

        this.paymentRepository.save(payment);
        this.individualUserRepository.save(individualUser);
    }

    public List<PaymentResponseDTO> getAllPayments(){
         List<Payment> paymentList = this.paymentRepository.findAll();
         return paymentList.stream()
                 .map(payment -> modelMapper.map(payment, PaymentResponseDTO.class))
                 .collect(Collectors.toList());
    }


    public List<PaymentListResponseDTO> getIndividualUserPayments(Long platformUserID){
        return this.paymentRepository.findPaymentListResponseDTO(platformUserID);
    }

    public void deletePayment(Long id) {
        Optional<Payment> paymentOptional = this.paymentRepository.findById(id);
        if(paymentOptional.isPresent()){
            Payment payment = paymentOptional.get();
            IndividualUser individualUser = payment.getIndividualUser();
            individualUser.setMoney(individualUser.getMoney().subtract(payment.getAmount()));
            this.individualUserRepository.save(individualUser);
            this.paymentRepository.delete(payment);
        }
        else{
            throw new RuntimeException("Payment not exists");
        }
    }
}
