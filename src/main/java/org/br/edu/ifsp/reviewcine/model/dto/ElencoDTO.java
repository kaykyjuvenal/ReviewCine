package org.br.edu.ifsp.reviewcine.model.dto;

import org.br.edu.ifsp.reviewcine.model.Pessoa;

import java.util.List;

public record ElencoDTO(
        Long id,
        List<Pessoa> pessoas

) {
}
