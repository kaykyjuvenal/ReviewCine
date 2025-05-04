package org.br.edu.ifsp.reviewcine.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record DadosElenco(
        @JsonAlias("idFilme") Integer idFilme,
        @JsonAlias("idSerie") Integer idSerie,
        @JsonAlias("pessoas") List<Pessoa> pessoas) {
}
