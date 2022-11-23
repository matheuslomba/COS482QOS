package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Hd;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Hd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HdRepository extends JpaRepository<Hd, Long> {}
