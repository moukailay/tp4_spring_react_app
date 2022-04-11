package com.tp3.persistence;

import com.tp3.model.Cd;
import com.tp3.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CdRepository extends JpaRepository<Cd, Long> { }
