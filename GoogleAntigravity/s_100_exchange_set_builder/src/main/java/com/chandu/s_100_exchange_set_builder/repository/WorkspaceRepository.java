package com.chandu.s_100_exchange_set_builder.repository;

import com.chandu.s_100_exchange_set_builder.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    Optional<Workspace> findTopByOrderByIdDesc(); // Assuming single workspace for now, or last used
}
