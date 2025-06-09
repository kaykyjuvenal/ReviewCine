package org.br.edu.ifsp.reviewcine.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.br.edu.ifsp.reviewcine.Results.ResultadoAPI;
import org.springframework.stereotype.Component; // Importação necessária

@Component // Anotação para que o Spring gerencie esta classe
public class ConverteDados implements IConverteDados {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao processar JSON: " + e.getMessage(), e);
        }
    }

    public <T> ResultadoAPI<T> obterListaDeDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory()
                    .constructParametricType(ResultadoAPI.class, classe));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao processar lista de dados JSON: " + e.getMessage(), e);
        }
    }
}