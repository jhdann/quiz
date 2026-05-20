package com.futbol.repository;

import com.futbol.entity.Asociacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsociacionRepository extends JpaRepository<Asociacion, Long> {
}
