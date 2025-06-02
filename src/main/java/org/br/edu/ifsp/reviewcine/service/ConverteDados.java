package org.br.edu.ifsp.reviewcine.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.br.edu.ifsp.reviewcine.Results.ResultadoAPI;

public class ConverteDados implements  IConverteDados {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public <T> ResultadoAPI<T> obterListaDeDados(String json, Class<T> classe){
        try {
            return mapper.readValue(json, mapper.getTypeFactory()
                    .constructParametricType(ResultadoAPI.class, classe));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}