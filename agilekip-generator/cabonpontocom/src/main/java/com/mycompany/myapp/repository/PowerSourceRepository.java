package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PowerSource;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PowerSource entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PowerSourceRepository extends JpaRepository<PowerSource, Long> {}
