package com.chandu.s_100_exchange_set_builder.repository;

import com.chandu.s_100_exchange_set_builder.model.Resource;
import com.chandu.s_100_exchange_set_builder.model.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    Optional<Resource> findByFileName(String fileName);

    List<Resource> findByFileType(ResourceType fileType);
}
