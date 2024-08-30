package com.exis.paymanagement.domain.service;


import com.exis.paymanagement.domain.model.Payment;
import com.exis.paymanagement.domain.repository.PaymentRepository;
import com.exis.paymanagement.utils.Transformator;
import org.openapitools.model.PaymentRequest;
import org.openapitools.model.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private CardService cardService;

    @Autowired
    private Transformator transformator;

    public void save(PaymentRequest paymentRequest) {
        Payment payment = transformator.transform(paymentRequest, Payment.class);
        payment.setCard(cardService.registerCard(paymentRequest.getCardRequest()));
        repository.save(payment);
    }

    public List<PaymentResponse> getMaskCardsByUser(Long userId) {

        List<Payment> payments = repository.findByUserId(userId);

        if (!payments.isEmpty()) {
            return payments.stream()
                    .map(card -> maskCard(card))
                    .collect(Collectors.toList());
        } else return Collections.emptyList();

    }

    public PaymentResponse maskCard(Payment payment) {

        String cardNumber = null;
        PaymentResponse dto = new PaymentResponse();

        if (payment.getCard().getCardNumber().length() > 4) {
            cardNumber = "**** **** **** " + payment.getCard().getCardNumber().substring(payment.getCard().getCardNumber().length() - 4);
            payment.getCard().setCardNumber(cardNumber);
        }
        dto = transformator.transform(payment, PaymentResponse.class);
        return dto;
    }
}
