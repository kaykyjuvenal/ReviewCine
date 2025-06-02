package org.br.edu.ifsp.reviewcine.model.dto;

public record FilmeDTO(
         Boolean adult,
         Long id,
         String language,
         String overview,
         Integer popularity,
         String release_date,
         String title,
         Double vote_average,
         Integer vote_count) {
}
