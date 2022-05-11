package com.tp3.persistence;

import com.tp3.model.Cd;
import com.tp3.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CdRepository extends JpaRepository<Cd, Long> {
    Long countByidDoc(Long id);
    @Query("select cd from Cd cd where cd.nbExemplaire >=:nombre")
    List<Cd> findCdDisponible( @Param(("nombre"))int text);
}
