package com.exis.paymanagement.service;

import com.exis.paymanagement.domain.model.Card;
import com.exis.paymanagement.domain.repository.CardRepository;
import com.exis.paymanagement.utils.Transformator;
import com.exis.paymanagement.domain.service.CardService;
import org.openapitools.model.CardRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CardServiceIntegrationTest {

    @Autowired
    private CardService cardService;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private Transformator transformator;

    @Test
    @Transactional
    public void testRegisterCard_ValidCard() {
        // Arrange
        CardRequest cardRequest = new CardRequest();
        cardRequest.setCvv("123");
        cardRequest.setOwnerName("John Doe");
        cardRequest.setExpirationDate(LocalDate.now().plusMonths(1));

        // Act
        Card savedCard = cardService.registerCard(cardRequest);

        // Assert
        assertNotNull(savedCard);
        assertEquals("123", savedCard.getCvv());
        assertEquals("John Doe", savedCard.getOwnerName());
        assertTrue(savedCard.getExpirationDate().isAfter(LocalDate.now()));
    }

    @Test
    public void testRegisterCard_InvalidCVV() {
        // Arrange
        CardRequest cardRequest = new CardRequest();
        cardRequest.setCvv("12"); // Invalid CVV
        cardRequest.setOwnerName("John Doe");
        cardRequest.setExpirationDate(LocalDate.now().plusMonths(1));

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            cardService.registerCard(cardRequest);
        });
        assertEquals("Invalid CVV. Must contain 3 or 4 digits.", thrown.getMessage());
    }

    @Test
    public void testRegisterCard_InvalidOwnerName() {
        // Arrange
        CardRequest cardRequest = new CardRequest();
        cardRequest.setCvv("123");
        cardRequest.setOwnerName(""); // Invalid owner name
        cardRequest.setExpirationDate(LocalDate.now().plusMonths(1));

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            cardService.registerCard(cardRequest);
        });
        assertEquals("Owner's name must not be empty.", thrown.getMessage());
    }

    @Test
    public void testRegisterCard_InvalidExpirationDate() {
        // Arrange
        CardRequest cardRequest = new CardRequest();
        cardRequest.setCvv("123");
        cardRequest.setOwnerName("John Doe");
        cardRequest.setExpirationDate(LocalDate.now().minusMonths(1)); // Invalid expiration date

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            cardService.registerCard(cardRequest);
        });
        assertEquals("Invalid expiration date.", thrown.getMessage());
    }
}
