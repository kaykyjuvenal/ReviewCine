package org.br.edu.ifsp.reviewcine.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DadosElenco(
        @JsonAlias("id") Long id,
        @JsonAlias("pessoas") List<Pessoa> pessoas) {
}
