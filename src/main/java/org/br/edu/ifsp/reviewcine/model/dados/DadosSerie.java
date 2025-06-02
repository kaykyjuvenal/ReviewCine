package org.br.edu.ifsp.reviewcine.model.dados;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DadosSerie(
        @JsonAlias("id") Long id,
        @JsonAlias("original_language") String original_language,
        @JsonAlias("overview") String overview,
        @JsonAlias("name") String name,
        @JsonAlias("vote_average") Double vote_average,
        @JsonAlias("vote_count") Integer vote_count,
        @JsonAlias("first_air_date") String first_air_date,
        @JsonAlias("adult") Boolean adult,
        @JsonAlias("popularity") Integer popularity) {
}
