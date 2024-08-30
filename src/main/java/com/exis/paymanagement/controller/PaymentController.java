package com.exis.paymanagement.controller;

import com.exis.paymanagement.domain.service.PaymentService;
import org.openapitools.api.PaymentsApi;
import org.openapitools.model.PaymentRequest;
import org.openapitools.model.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/payment-methods")
public class PaymentController implements PaymentsApi {

    @Autowired
    private PaymentService service;

    @GetMapping("/paymentsGet/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PaymentResponse>> paymentsGet(@PathVariable("userId") Long userId) {
        List<PaymentResponse> list = service.getMaskCardsByUser(userId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/paymentsPost")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> paymentsPost(@Valid @RequestBody PaymentRequest paymentRequest) {
        // Create the payment method
        service.save(paymentRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
