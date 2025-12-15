package com.chandu.resourceservice.repository;

import com.chandu.resourceservice.model.Resource;
import com.chandu.resourceservice.model.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    Optional<Resource> findByFileName(String fileName);

    List<Resource> findByFileType(ResourceType fileType);
}
