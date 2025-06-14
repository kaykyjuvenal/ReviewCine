package org.br.edu.ifsp.reviewcine.service;

import org.br.edu.ifsp.reviewcine.Results.ResultadoAPI;
import org.br.edu.ifsp.reviewcine.model.Serie;
import org.br.edu.ifsp.reviewcine.model.dados.DadosSerie;
import org.br.edu.ifsp.reviewcine.model.dto.SerieDTO;
import org.br.edu.ifsp.reviewcine.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SerieService {

    private final SerieRepository serieRepository;
    private final ConverteDados converteDados;
    private final ConsumoAPI consumoAPI;

    private final String API_KEY = "api_key=cd190993f189e0a225dc0799ddb4b9d1";

    @Autowired
    public SerieService(SerieRepository serieRepository, ConverteDados converteDados, ConsumoAPI consumoAPI) {
        this.serieRepository = serieRepository;
        this.converteDados = converteDados;
        this.consumoAPI = consumoAPI;
    }

    public List<SerieDTO> obterTodasAsSeries() {
        return converteDadosParaListaDTO(serieRepository.findAll());
    }

    public SerieDTO obterPorId(long id) {
        Serie serieEncontrada = serieRepository.findById(id)
                .orElseGet(() -> {
                    System.out.println("Série com ID " + id + " não encontrada localmente. Buscando na web...");
                    return buscarSerieNaWebPorId(id);
                });
        return converteDadosParaDTO(serieEncontrada);
    }

    public SerieDTO obterPorNome(String nome) {
        Serie serieEncontrada = serieRepository.findByNameIgnoreCase(nome)
                .orElseGet(() -> {
                    System.out.println("Série '" + nome + "' não encontrada localmente. Buscando na web...");
                    return buscarSerieNaWebPorNome(nome);
                });
        return converteDadosParaDTO(serieEncontrada);
    }

    public void buscarSeriesDaWebEGravar() {
        String ENDERECO_DISCOVER = "https://api.themoviedb.org/3/discover/tv?" + API_KEY + "&language=pt-BR&sort_by=popularity.desc&first_air_date_year=2025";
        List<DadosSerie> seriesWeb = getTodasSeriesComPaginacao(ENDERECO_DISCOVER);
        for (DadosSerie dadosSerie : seriesWeb) {
            serieRepository.save(new Serie(dadosSerie));
        }
        System.out.println(seriesWeb.size() + " séries populares de 2025 armazenadas com sucesso no banco de dados!");
    }

    private Serie buscarSerieNaWebPorId(long id) {
        String endereco = String.format("https://api.themoviedb.org/3/tv/%d?%s&language=pt-BR", id, API_KEY);
        try {
            String json = consumoAPI.obterDados(endereco);
            DadosSerie dadosSerie = converteDados.obterDados(json, DadosSerie.class);
            Serie serie = new Serie(dadosSerie);
            serieRepository.save(serie);
            return serie; // Retorna a entidade Serie
        } catch (Exception e) {
            System.err.println("Erro ao buscar série na web por ID: " + e.getMessage());
            return null;
        }
    }

    private Serie buscarSerieNaWebPorNome(String nomeSerie) {
        try {
            String nomeCodificado = URLEncoder.encode(nomeSerie, StandardCharsets.UTF_8);
            String endereco = "https://api.themoviedb.org/3/search/tv?" + API_KEY + "&language=pt-BR&query=" + nomeCodificado;
            String json = consumoAPI.obterDados(endereco);
            ResultadoAPI<DadosSerie> resultado = converteDados.obterListaDeDados(json, DadosSerie.class);

            if (resultado != null && !resultado.getResults().isEmpty()) {
                DadosSerie dados = resultado.getResults().get(0);
                Serie serie = new Serie(dados);
                System.out.println("Salvando série '" + serie.getName() + "' no banco de dados.");
                serieRepository.save(serie);
                return serie;
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar série na web por nome: " + e.getMessage());
        }
        return null;
    }

    private List<DadosSerie> getTodasSeriesComPaginacao(String enderecoBase) {
        List<DadosSerie> todasAsSeries = new ArrayList<>();
        int paginaAtual = 1;
        int totalPaginas = 1;

        do {
            String enderecoComPagina = enderecoBase + "&page=" + paginaAtual;
            try {
                String json = consumoAPI.obterDados(enderecoComPagina);
                ResultadoAPI<DadosSerie> resultado = converteDados.obterListaDeDados(json, DadosSerie.class);
                if (resultado != null && resultado.getResults() != null) {
                    todasAsSeries.addAll(resultado.getResults());
                    totalPaginas = resultado.getTotalPages();
                    System.out.println("Buscando página " + paginaAtual + " de " + totalPaginas);
                } else {
                    break;
                }
                paginaAtual++;
            } catch (Exception e) {
                System.err.println("Erro ao buscar página " + paginaAtual + ": " + e.getMessage());
                break;
            }
        } while (paginaAtual <= totalPaginas);

        return todasAsSeries;
    }

    private SerieDTO converteDadosParaDTO(Serie serie) {
        if (serie == null) return null;
        return new SerieDTO(serie.getId(), serie.getName(), serie.getVote_average(),
                serie.getVote_count(), serie.getFirst_air_date(), serie.isAdult(),
                serie.getPopularity(), serie.getOriginal_language(), serie.getOverview());
    }

    private List<SerieDTO> converteDadosParaListaDTO(List<Serie> series) {
        return series.stream()
                .map(this::converteDadosParaDTO)
                .toList();
    }

    public List<SerieDTO> obterTop3SeriesMaisPopulares() {
        Pageable topTres = PageRequest.of(0, 3);
        List<Serie> topSeries = serieRepository.findMaisPopulares(topTres);
        return converteDadosParaListaDTO(topSeries);
    }
}