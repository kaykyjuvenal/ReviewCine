package org.br.edu.ifsp.reviewcine.model.dados;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.br.edu.ifsp.reviewcine.model.Pessoa;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DadosElenco(
        @JsonAlias("id") Long id,
        @JsonAlias("cast") List<DadosPessoa> pessoas) {
}
