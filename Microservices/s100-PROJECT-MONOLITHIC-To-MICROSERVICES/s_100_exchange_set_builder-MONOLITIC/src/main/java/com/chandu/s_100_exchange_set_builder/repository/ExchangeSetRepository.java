package com.chandu.s_100_exchange_set_builder.repository;

import com.chandu.s_100_exchange_set_builder.model.ExchangeSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeSetRepository extends JpaRepository<ExchangeSet, Long> {
    Optional<ExchangeSet> findByName(String name);

    boolean existsByName(String name);
}
