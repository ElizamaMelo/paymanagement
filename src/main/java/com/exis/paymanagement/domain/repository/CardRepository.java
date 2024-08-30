package com.exis.paymanagement.domain.repository;

import com.exis.paymanagement.domain.model.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends CrudRepository<Card, Long> {
}
