package com.chandu.exchangeservice.repository;

import com.chandu.exchangeservice.model.ContentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentItemRepository extends JpaRepository<ContentItem, Long> {
}
