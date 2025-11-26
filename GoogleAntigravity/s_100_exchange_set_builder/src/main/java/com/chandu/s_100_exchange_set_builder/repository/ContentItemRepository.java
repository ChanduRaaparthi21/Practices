package com.chandu.s_100_exchange_set_builder.repository;

import com.chandu.s_100_exchange_set_builder.model.ContentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentItemRepository extends JpaRepository<ContentItem, Long> {
}
