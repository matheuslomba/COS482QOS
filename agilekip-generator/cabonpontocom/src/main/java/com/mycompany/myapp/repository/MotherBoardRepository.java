package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MotherBoard;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MotherBoard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MotherBoardRepository extends JpaRepository<MotherBoard, Long> {}
