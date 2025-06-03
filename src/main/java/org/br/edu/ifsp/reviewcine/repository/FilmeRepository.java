package org.br.edu.ifsp.reviewcine.repository;

import org.br.edu.ifsp.reviewcine.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {
    @Query("SELECT f FROM Filme f WHERE LOWER(f.title) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Filme> findByKeyWordContainingIgnoreCase(String name);
    @Query("SELECT f FROM Filme f WHERE LOWER(f.title) LIKE LOWER(:name)")
    List<Filme> findByTitleContainingIgnoreCase(String name);
    @Query("SELECT f FROM Filme f WHERE LOWER(f.language) LIKE LOWER(CONCAT('%', :language, '%'))")
    List<Filme> findAllByLanguage(String language);
    @Query( "SELECT f FROM Filme f ORDER BY f.popularity ASC")
    List<Filme> findAllByPopularityDesc();
    @Query("SELECT f FROM Filme f ORDER BY f.vote_average ASC")
    List<Filme> findAllByOrderByVote_averageDesc();

}
