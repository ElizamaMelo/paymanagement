package com.exis.paymanagement.domain.repository;

import com.exis.paymanagement.domain.model.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

    Payment save(Payment payment);
    List<Payment> findByUserId(Long userId);
}
