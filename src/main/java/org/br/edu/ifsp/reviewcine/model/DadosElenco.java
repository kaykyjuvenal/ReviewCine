package org.br.edu.ifsp.reviewcine.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DadosElenco(
        @JsonAlias("idFilme") Integer idFilme,
        @JsonAlias("idSerie") Integer idSerie,
        @JsonAlias("pessoas") List<Pessoa> pessoas) {
}
