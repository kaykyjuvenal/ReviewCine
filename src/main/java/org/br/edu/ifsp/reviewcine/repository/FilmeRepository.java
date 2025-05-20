package org.br.edu.ifsp.reviewcine.repository;

import org.br.edu.ifsp.reviewcine.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {
}
