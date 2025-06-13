package org.br.edu.ifsp.reviewcine.repository;

import org.br.edu.ifsp.reviewcine.model.Serie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie,Long> {

    // Encontrar séries pelo nome (equivalente ao seu 'title' para filmes)
    @Query("SELECT s FROM Serie s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Serie> findByNameContainingIgnoreCase(String keyword);

    // Encontrar séries exatamente pelo nome, ignorando maiúsculas/minúsculas
    @Query("SELECT s FROM Serie s WHERE LOWER(s.name) = LOWER(:name)")
    Optional<Serie> findByNameIgnoreCase(String name); // Usando Optional para uma única série

    // Encontrar séries pela linguagem original, contendo1 o termo e ignorando maiúsculas/minúsculas
    @Query("SELECT s FROM Serie s WHERE LOWER(s.original_language) LIKE LOWER(CONCAT('%', :language, '%'))")
    List<Serie> findAllByOriginalLanguageContainingIgnoreCase(String language);

    // Encontrar todas as séries ordenadas pela popularidade em ordem decrescente
    // (Apesar de você ter colocado ASC nos seus exemplos, usei DESC para ser mais comum para popularidade)
    @Query("SELECT s FROM Serie s ORDER BY s.popularity DESC")
    List<Serie> findAllByPopularityDesc();

    // Encontrar todas as séries ordenadas pela média de votos em ordem decrescente
    @Query("SELECT s FROM Serie s ORDER BY s.vote_average DESC")
    List<Serie> findAllByOrderByVoteAverageDesc(); // Note: Assumindo 'voteAverage' é o nome da propriedade na entidade

    // Encontrar todas as séries adultas
    @Query("SELECT s FROM Serie s WHERE s.adult = TRUE")
    List<Serie> findAllByAdultIsTrue();

    // Encontrar todas as séries não adultas
    @Query("SELECT s FROM Serie s WHERE s.adult = FALSE")
    List<Serie> findAllByAdultIsFalse();

    // Encontrar todas as séries ordenadas pela data da primeira exibição (mais recentes primeiro)
    @Query("SELECT s FROM Serie s ORDER BY s.first_air_date DESC")
    List<Serie> findAllByOrderByFirstAirDateDesc();
    @Query("SELECT s FROM Serie s ORDER BY s.popularity DESC")
    List<Serie> findMaisPopulares(Pageable pageable);
}
