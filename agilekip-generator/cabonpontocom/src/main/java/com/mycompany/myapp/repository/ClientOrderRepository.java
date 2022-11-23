package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ClientOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ClientOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientOrderRepository extends JpaRepository<ClientOrder, Long> {}
