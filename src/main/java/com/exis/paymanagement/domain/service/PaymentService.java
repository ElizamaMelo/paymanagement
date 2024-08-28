package com.exis.paymanagement.domain.service;


import com.exis.paymanagement.domain.model.Payment;
import com.exis.paymanagement.utils.Transformator;
import com.exis.paymanagement.domain.repository.PaymentRepository;
import org.openapitools.model.PaymentRequest;
import org.openapitools.model.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private Transformator transformator;

    public void save (PaymentRequest paymentRequest) {

        repository.save(transformator.transform(paymentRequest,Payment.class));
    }

    public List<PaymentResponse> getMaskCardsByUser(Long userId) {

        List<Payment> payments = repository.findByUserId(userId);

        return payments.stream()
                .map(card -> {
                    return maskCard(card);
                })
                .collect(Collectors.toList());
    }

    private PaymentResponse maskCard(Payment payment) {

        String cardNumber = null;
        PaymentResponse dto = new PaymentResponse();

        if (payment.getCardNumber().length() > 4) {
            cardNumber = "**** **** **** " + payment.getCardNumber().substring(payment.getCardNumber().length() - 4);
            payment.setCardNumber(cardNumber);
            dto = transformator.transform(payment, PaymentResponse.class);
        }
        return dto;
    }
}
