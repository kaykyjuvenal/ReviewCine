package org.br.edu.ifsp.reviewcine.repository;

import org.br.edu.ifsp.reviewcine.model.Elenco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ElencoRepository extends JpaRepository<Elenco, Long> {
    @Query("SELECT e FROM Elenco e JOIN FETCH e.pessoas WHERE e.id = :id")
    Optional<Elenco> findByIdWithPessoas(@Param("id") Long id);
}
