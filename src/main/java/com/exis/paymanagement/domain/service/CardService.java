package com.exis.paymanagement.domain.service;

import com.exis.paymanagement.domain.model.Card;
import com.exis.paymanagement.domain.repository.CardRepository;
import com.exis.paymanagement.utils.Transformator;
import org.openapitools.model.CardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CardService {

    @Autowired
    public CardRepository repository;
    @Autowired
    private Transformator transformator;


    public Card registerCard(CardRequest cardRequest) throws IllegalArgumentException {

        if (!isValidCVV(cardRequest.getCvv())) {
            throw new IllegalArgumentException("Invalid CVV. Must contain 3 or 4 digits.");
        }

        if (!isValidOwnerName(cardRequest.getOwnerName())) {
            throw new IllegalArgumentException("Owner's name must not be empty.");
        }

        if (!isValidExpirationDate(cardRequest.getExpirationDate())) {
            throw new IllegalArgumentException("Invalid expiration date.");
        }

        return repository.save(transformator.transform(cardRequest, Card.class));

    }

    public static boolean isValidCVV(String cvv) {
        return cvv != null && cvv.matches("\\d{3,4}");
    }


    public static boolean isValidOwnerName(String ownerName) {
        return ownerName != null && !ownerName.trim().isEmpty();
    }


    public static boolean isValidExpirationDate(LocalDate expirationDate) {
        return expirationDate != null && expirationDate.isAfter(LocalDate.now());
    }
}
