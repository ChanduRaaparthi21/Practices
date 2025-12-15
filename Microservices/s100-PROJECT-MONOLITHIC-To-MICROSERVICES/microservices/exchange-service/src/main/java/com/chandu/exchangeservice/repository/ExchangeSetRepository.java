package com.chandu.exchangeservice.repository;

import com.chandu.exchangeservice.model.ExchangeSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeSetRepository extends JpaRepository<ExchangeSet, Long> {
    Optional<ExchangeSet> findByName(String name);

    boolean existsByName(String name);
}
