package org.br.edu.ifsp.reviewcine.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record PessoaDTO(
        String adult,
        Integer  gender,
        Long id,
        String department,
        String name,
        String character,
        Integer popularity) {
}
