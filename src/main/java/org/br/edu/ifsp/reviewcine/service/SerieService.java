package org.br.edu.ifsp.reviewcine.service;

import org.br.edu.ifsp.reviewcine.Results.ResultadoAPI;
import org.br.edu.ifsp.reviewcine.model.Serie;
import org.br.edu.ifsp.reviewcine.model.dados.DadosSerie;
import org.br.edu.ifsp.reviewcine.model.dto.SerieDTO;
import org.br.edu.ifsp.reviewcine.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SerieService {
    @Autowired
    private final SerieRepository serieRepository;
    @Autowired
    private final ConverteDados converteDados;
    @Autowired
    private final ConsumoAPI consumoAPI;

    private final String API_KEY = "api_key=cd190993f189e0a225dc0799ddb4b9d1"; // Removido o '&' do final

    // Injeção de dependência via construtor
    @Autowired
    public SerieService(SerieRepository serieRepository, ConverteDados converteDados, ConsumoAPI consumoAPI) {
        this.serieRepository = serieRepository;
        this.converteDados = converteDados;
        this.consumoAPI = consumoAPI;
    }

    public Serie obterPorNome(String nome) {
        List<Serie> seriesEncontradas = serieRepository.findByNameContainingIgnoreCase(nome);

        if (!seriesEncontradas.isEmpty()) {
            System.out.println("Série '" + nome + "' encontrada no banco de dados local.");
            return seriesEncontradas.get(0);
        } else {
            System.out.println("Série '" + nome + "' não encontrada localmente. Buscando na web...");
            return buscarSerieNaWebPorNome(nome);
        }
    }

    private Serie buscarSerieNaWebPorNome(String nomeSerie) {
        try {
            // Codifica o nome da série para ser seguro para URL
            String nomeCodificado = URLEncoder.encode(nomeSerie, StandardCharsets.UTF_8);
            String ENDERECO_PESQUISA = "https://api.themoviedb.org/3/search/tv?" + API_KEY + "&query=" + nomeCodificado;

            var json = consumoAPI.obterDados(ENDERECO_PESQUISA);
            System.out.println("JSON da busca por nome: " + json);

            // A API de busca retorna uma lista de resultados
            ResultadoAPI<DadosSerie> resultado = converteDados.obterListaDeDados(json, DadosSerie.class);

            if (resultado != null && !resultado.getResults().isEmpty()) {
                // Pega o primeiro resultado, que geralmente é o mais relevante
                DadosSerie dados = resultado.getResults().get(0);
                return new Serie(dados);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar série na web por nome: " + e.getMessage());
        }
        return null; // Retorna null se não encontrar ou se houver erro
    }

    // ... Outros métodos da sua classe ...

    public List<Serie> findAll(){
        return serieRepository.findAll();
    }

    public void buscarSeriesWeb() {
        String ENDERECO_SERIE = "https://api.themoviedb.org/3/discover/tv?" + API_KEY + "&first_air_date.gte=2025-01-01&first_air_date.lte=2025-12-31";
        List<DadosSerie> seriesWeb = getTodosSerie(ENDERECO_SERIE);
        for (DadosSerie serie : seriesWeb) {
            System.out.println(serie.toString());
            serieRepository.save(new Serie(serie));
        }
        System.out.println("Séries armazenadas com sucesso no banco de dados!");
    }

    private List<DadosSerie> getTodosSerie(String enderecoBase) {
        // ... (lógica para paginação)
        return new ArrayList<>(); // Implementar lógica
    }

    public Optional<Serie> buscarSerieWeb(long id) {
        return serieRepository.findById(id);
    }
}