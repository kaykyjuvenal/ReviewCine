package org.br.edu.ifsp.reviewcine.service;

import org.springframework.stereotype.Component; // Importação necessária

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component // Anotação para que o Spring gerencie esta classe
public class ConsumoAPI {

    // O HttpClient é criado uma única vez e reutilizado para todas as chamadas.
    // Isso melhora o desempenho e o gerenciamento de recursos.
    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public String obterDados(String endereco) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // Verifica se a resposta foi bem-sucedida (código 2xx)
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return response.body();
            } else {
                throw new RuntimeException("Falha na requisição HTTP. Código: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            // Lança uma exceção mais específica para falhas de rede ou interrupção
            throw new RuntimeException("Erro ao consumir a API: " + e.getMessage(), e);
        }
    }
}