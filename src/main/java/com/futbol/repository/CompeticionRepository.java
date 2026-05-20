package com.futbol.repository;

import com.futbol.entity.Competicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompeticionRepository extends JpaRepository<Competicion, Long> {
}
