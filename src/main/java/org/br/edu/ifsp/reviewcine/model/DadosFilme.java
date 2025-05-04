package org.br.edu.ifsp.reviewcine.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record DadosFilme(@JsonAlias ("name") String name,
                         @JsonAlias ("vote_average") Integer vote_average,
                         @JsonAlias ("vote_count") Integer vote_count,
                         @JsonAlias ("first_air_date") String first_air_date,
                         @JsonAlias ("adult") Boolean adult,
                         @JsonAlias ("popularity") Double popularity,
                         @JsonAlias ("elencos")List<DadosElenco> elencos){
}
