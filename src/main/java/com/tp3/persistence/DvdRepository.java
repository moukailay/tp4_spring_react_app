package com.tp3.persistence;

import com.tp3.model.Dvd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DvdRepository extends JpaRepository<Dvd, Long> {
    Long countByidDoc(Long id);

    @Query("select d from Dvd d where d.nbExemplaire >=:nombre")
    List<Dvd> findDvdDisponible( @Param(("nombre"))int text);
}
