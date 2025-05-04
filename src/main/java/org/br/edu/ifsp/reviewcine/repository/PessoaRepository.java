package org.br.edu.ifsp.reviewcine.repository;

import org.br.edu.ifsp.reviewcine.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>  {
}
