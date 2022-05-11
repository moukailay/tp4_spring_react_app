package com.tp3.persistence;

import com.tp3.model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Long> {
    Long countByidDoc(Long id);
    @Query("select l from Livre l where l.nbExemplaire >=:nombre")
    List<Livre> findLivreDisponible( @Param(("nombre"))int text);
}
