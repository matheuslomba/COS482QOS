package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ClientOrderProcess;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ClientOrderProcess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientOrderProcessRepository extends JpaRepository<ClientOrderProcess, Long> {
    Optional<ClientOrderProcess> findByProcessInstanceId(Long processInstanceId);
}
