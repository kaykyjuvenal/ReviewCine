package org.br.edu.ifsp.reviewcine.model.dados;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DadosPessoa(

    @JsonAlias("adult")String adult,
    @JsonAlias("gender") Integer gender,
    @JsonAlias("id") Long id,
    @JsonAlias("known_for_department") String department,
    @JsonAlias("name") String name,
    @JsonAlias("character") String character,
    @JsonAlias("popularity") Double popularity

){ }
