package com.exis.paymanagement.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_generator")
    @SequenceGenerator(name = "card_generator", sequenceName = "card_sequence", allocationSize = 1)
    private Long id;

    @CreditCardNumber
    private String cardNumber;

    private String cvv;

    private String ownerName;
    private Long userId;

    private LocalDate expirationDate;


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
