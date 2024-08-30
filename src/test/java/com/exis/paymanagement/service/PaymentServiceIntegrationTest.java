package com.exis.paymanagement.service;

import com.exis.paymanagement.domain.model.Card;
import com.exis.paymanagement.domain.model.Payment;
import com.exis.paymanagement.domain.repository.CardRepository;
import com.exis.paymanagement.domain.repository.PaymentRepository;
import com.exis.paymanagement.domain.service.CardService;
import com.exis.paymanagement.domain.service.PaymentService;
import com.exis.paymanagement.utils.Transformator;
import org.junit.jupiter.api.Test;
import org.openapitools.model.CardRequest;
import org.openapitools.model.PaymentRequest;
import org.openapitools.model.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // Use um profile específico para testes, se necessário
public class PaymentServiceIntegrationTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardService cardService;

    @Autowired
    private Transformator transformator;

    @Test
    @Transactional
    public void testSave_PaymentRequest_Success() {
        // Act
        paymentService.save(generatePayment());

        // Assert
        List<Payment> payments = paymentRepository.findByUserId(1L);
        assertFalse(payments.isEmpty());
        Payment savedPayment = payments.get(0);
        assertEquals(1L, savedPayment.getUserId());
        assertNotNull(savedPayment.getCard());
        assertEquals("123", savedPayment.getCard().getCvv());
    }

    private PaymentRequest generatePayment(){
        CardRequest cardRequest = new CardRequest();
        cardRequest.setCvv("123");
        cardRequest.setOwnerName("John Doe");
        cardRequest.setCardNumber("2507523112");
        cardRequest.setUserId(1L);
        cardRequest.setExpirationDate(LocalDate.now().plusMonths(1));
        cardService.registerCard(cardRequest);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setCardRequest(cardRequest);
        paymentRequest.setAmount(200D);
        paymentRequest.setPaymentDate(LocalDate.now());
        paymentRequest.setUserId(1L);
        return paymentRequest;

    }

    @Test
    @Transactional
    public void testGetMaskCardsByUser_ValidUserId() {

        paymentService.save(generatePayment());

        // Act
        List<PaymentResponse> maskedCards = paymentService.getMaskCardsByUser(1L);

        // Assert
        assertFalse(maskedCards.isEmpty());
        PaymentResponse response = maskedCards.get(0);
        assertTrue(response.getCard().getCardNumber().startsWith("**** **** **** "));
    }

    @Test
    public void testMaskCard_CardNumberMasking() {
        // Arrange
        Card card = new Card();
        card.setCardNumber("1234567812345678");

        Payment payment = new Payment();
        payment.setCard(card);

        // Act
        PaymentResponse maskedCardResponse = paymentService.maskCard(payment);

        // Assert
        assertEquals("**** **** **** 5678", maskedCardResponse.getCard().getCardNumber());
    }
}

