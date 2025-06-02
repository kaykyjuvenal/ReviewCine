package org.br.edu.ifsp.reviewcine.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record SerieDTO (
        Long id,
        String name,
        Double vote_average,
        Integer vote_count,
        String first_air_date,
        Boolean adult,
        Integer popularity,
        String original_language,
        String overview
){}
