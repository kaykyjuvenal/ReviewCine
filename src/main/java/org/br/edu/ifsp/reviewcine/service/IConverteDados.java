package org.br.edu.ifsp.reviewcine.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IConverteDados
{
    <T> T obterDados(String json, Class<T> classe);
}
