package org.br.edu.ifsp.reviewcine.repository;

import org.br.edu.ifsp.reviewcine.model.Pessoa;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @Query("SELECT p FROM Pessoa p WHERE LOWER(p.name) = LOWER(:name)")
    Optional<Pessoa> findByNameIgnoreCase(String name);

    @Query("SELECT p FROM Pessoa p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :nameFragment, '%'))")
    List<Pessoa> findByNameContainingIgnoreCase(String nameFragment);

    @Query("SELECT p FROM Pessoa p WHERE LOWER(p.character) LIKE LOWER(CONCAT('%', :characterName, '%'))")
    List<Pessoa> findByCharacterContainingIgnoreCase(String characterName);

    @Query("SELECT p FROM Pessoa p WHERE p.department = :department")
    List<Pessoa> findByDepartment(String department);

    @Query("SELECT p FROM Pessoa p WHERE p.department IN :departments")
    List<Pessoa> findByDepartmentIn(@Param("departments") Collection<String> departments);

    @Query("SELECT p FROM Pessoa p ORDER BY p.popularity DESC")
    List<Pessoa> findAllComOrdenacaoDePopularidade();
    @Query("SELECT p FROM Pessoa p ORDER BY p.popularity DESC")
    List<Pessoa> findMaisPopulares(Pageable pageable);
    @Query("SELECT p FROM Pessoa p ORDER BY p.popularity DESC")
    List<Pessoa> findPorPopularidade();

    @Query("SELECT p FROM Pessoa p WHERE p.gender = :genderCode")
    List<Pessoa> findByGender(int genderCode);

    @Query("SELECT p FROM Pessoa p WHERE p.adult = :isAdult")
    List<Pessoa> findByAdult(@Param("isAdult") boolean isAdult);
}