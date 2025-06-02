package org.br.edu.ifsp.reviewcine.model.dados;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DadosFilme(@JsonAlias ("adult") Boolean adult,
                         @JsonAlias ("id") Long id,
                         @JsonAlias("original_language") String original_language,
                         @JsonAlias("overview") String overview,
                         @JsonAlias ("popularity") Integer popularity,
                         @JsonAlias ("release_date") String release_date,
                         @JsonAlias("title") String title,
                         @JsonAlias ("vote_average") Double vote_average,
                         @JsonAlias ("vote_count") Integer vote_count){
}
