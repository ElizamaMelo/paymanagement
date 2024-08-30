package com.exis.paymanagement.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_generator")
    @SequenceGenerator(name = "payment_generator", sequenceName = "payment_sequence", allocationSize = 1)
    private Long id;


    private Double amount;
    private LocalDate paymentDate;
    private String description;
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "card_id")  // Specifies the foreign key column in the Payment table
    private Card card;

}
