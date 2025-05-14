package org.br.edu.ifsp.reviewcine.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DadosSerie(@JsonAlias("nome") String nome,
                         @JsonAlias ("backdrop_path") String backdrop_path,
                         @JsonAlias("vote_average") Double vote_average,
                         @JsonAlias("vote_count") Integer vote_count,
                         @JsonAlias("first_air_date") String first_air_date,
                         @JsonAlias("adult") Boolean adult,
                         @JsonAlias("popularity") Double popularity,
                         @JsonAlias("elenco") DadosElenco elenco) {
}
