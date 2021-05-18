package ru.demo.camunda.db.access.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.demo.camunda.db.access.domain.AccessRequest;

import java.util.Optional;

public interface AccessRequestRepository extends JpaRepository<AccessRequest, Long> {

    Optional<AccessRequest> findOneByEntityId(Long entityId);
}
