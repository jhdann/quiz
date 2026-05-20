package com.futbol.repository;

import com.futbol.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {

    // Carga clubes con sus competiciones (evita N+1 en ManyToMany)
    @Query("SELECT DISTINCT c FROM Club c LEFT JOIN FETCH c.competiciones")
    List<Club> findAllWithCompeticiones();

    // Carga clubes con sus jugadores
    @Query("SELECT DISTINCT c FROM Club c LEFT JOIN FETCH c.jugadores")
    List<Club> findAllWithJugadores();
}
