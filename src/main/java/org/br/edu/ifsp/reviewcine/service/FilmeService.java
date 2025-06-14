package org.br.edu.ifsp.reviewcine.service;

import org.br.edu.ifsp.reviewcine.model.Filme;
import org.br.edu.ifsp.reviewcine.Results.ResultadoAPI;
import org.br.edu.ifsp.reviewcine.model.dados.DadosFilme;
import org.br.edu.ifsp.reviewcine.model.dto.FilmeDTO;
import org.br.edu.ifsp.reviewcine.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FilmeService {
    @Autowired
    private FilmeRepository filmeRepository;

    private final int idFilmePesquisado = 5;
    @Autowired
    private ConverteDados converteDados;
    @Autowired
    private ConsumoAPI consumoAPI;

    private final String API_KEY = "api_key=cd190993f189e0a225dc0799ddb4b9d1&";
    private String ENDERECO_FILME = "https://api.themoviedb.org/3/discover/movie?" + API_KEY + "&primary_release_year=2025";
    private final String ENDERECO_FILME_UNICO = "https://api.themoviedb.org/3/movie/" + idFilmePesquisado + '?'+ API_KEY;



    public List<FilmeDTO> obterTodosOsFilmes(){return converteDados(filmeRepository.findAll());}
    public FilmeDTO converteDados(Filme filme){
        return new FilmeDTO(filme.isAdult(),filme.getId(), filme.getLanguage(),
                filme.getOverview(),filme.getPopularity(), filme.getRelease_date(), filme.getTitle(),
                filme.getVote_average(), filme.getVote_count());
    }
    public List<FilmeDTO> converteDados(List<Filme> filmes){
        return filmes.stream()
                .map(filme-> new FilmeDTO(filme.isAdult(), filme.getId()
                , filme.getLanguage(),filme.getOverview() , filme.getPopularity()
                , filme.getRelease_date(), filme.getTitle(), filme.getVote_average(),
                        filme.getVote_count())).toList();
    }

    public void save(FilmeDTO filmeDTO){
        Optional<Filme> existente = filmeRepository.findById(filmeDTO.id());
        Filme filme = new Filme(filmeDTO);
        if (existente.isPresent()) {
            filmeRepository.save(filme); // faz update
        }
    }
    public List<FilmeDTO> obterTop3FilmesPopulares() {
        // Cria um objeto de paginação: página 0 (a primeira), com 3 elementos.
        Pageable topTres = PageRequest.of(0, 3);

        List<Filme> topFilmes = filmeRepository.findMaisPopulares(topTres);
        return converteDados(topFilmes);
    }

    public List<Filme> findAll(){
        return filmeRepository.findAll();
    }
    private List<DadosFilme> getTodosFilme() {
        System.out.println("Coletando todos os filmes!");
        int paginaAtual = 1;
        int totalPaginas = 1;
        var todosFilmes = new ArrayList<DadosFilme>();

        // PONTO CHAVE: Esta URL de busca por nome NÃO TEM o filtro de ano.

        do {
            ENDERECO_FILME = ENDERECO_FILME + "&language=pt-BR"+ "&page=" + paginaAtual;
            try{
                var json = consumoAPI.obterDados(ENDERECO_FILME);
                System.out.println("JSON: " + json);
                ResultadoAPI<DadosFilme> resultado = converteDados.obterListaDeDados(json, DadosFilme.class);
                if (resultado != null){
                    todosFilmes.addAll(resultado.getResults());
                    totalPaginas = resultado.getTotalPages();
                }else{
                    break;
                }
                paginaAtual++;
            }
            catch(Exception e){
                e.printStackTrace();
                break;
            }

        } while (paginaAtual <= totalPaginas);
        return todosFilmes;
    }
    public void buscarFilmesWeb() {
        List<DadosFilme> filmesWeb = getTodosFilme();
        for (DadosFilme filme : filmesWeb) {
            System.out.println(filme.toString());
            FilmeDTO filmeDTO = converteDados(new Filme(filme));
            filmeRepository.save(new Filme(filmeDTO));
        }
        System.out.println("Filmes armazenados com sucesso no banco de dados!");

    }
    public void buscarFilmeWeb() {
        Filme filmeWeb = getUnicFilme();
        filmeRepository.save(filmeWeb);
        System.out.println("Filme: " + filmeWeb.toString());

    }
    public  Filme getUnicFilme(){
        var json2 = consumoAPI.obterDados(ENDERECO_FILME_UNICO);
        return new Filme(converteDados.obterDados(json2, DadosFilme.class));
    }
    public FilmeDTO obterPorNome(String title) {
        System.out.println("Buscando filme localmente pelo título: " + title);
        Optional<Filme> filmeLocal = filmeRepository.findByTitleContainingIgnoreCase(title);

        if (filmeLocal.isPresent()) {
            System.out.println("Filme encontrado no banco de dados local!");
            // Se encontrou, converte para DTO e retorna
            return converteDados(filmeLocal.get());
        } else {
            System.out.println("Filme não encontrado localmente. Acionando busca na API externa...");
            Filme filmeDaWeb = new Filme(Objects.requireNonNull(buscarFilmeNaWebPorNome(title)));
            System.out.println("Filme encontrado na web e salvo no banco!");
            // Se encontrou na web, converte para DTO e retorna
            return converteDados(filmeDaWeb);
        }
    }
    public FilmeDTO obterPorId(long id) {
        System.out.println("Buscando filme localmente pelo ID: " + id);
        Optional<Filme> filmeLocal = filmeRepository.findById(id);

        if (filmeLocal.isPresent()) {
            System.out.println("Filme encontrado no banco de dados local!");
            return converteDados(filmeLocal.get());
        } else {
            System.out.println("Filme não encontrado localmente. Acionando busca na API externa...");

            // --- Lógica da API diretamente aqui dentro ---
            String endereco = String.format("https://api.themoviedb.org/3/movie/%d?%s&language=pt-BR", id, API_KEY);

            try {
                String json = consumoAPI.obterDados(endereco);
                DadosFilme dadosFilme = converteDados.obterDados(json, DadosFilme.class);

                if (dadosFilme != null) {
                    Filme filmeDaWeb = new Filme(dadosFilme);

                    System.out.println("Filme encontrado na web e salvo no banco!");
                    filmeRepository.save(filmeDaWeb); // Salva para otimizar buscas futuras

                    return converteDados(filmeDaWeb); // Retorna o DTO do novo filme
                }
            } catch (Exception e) {
                System.err.println("Erro ao buscar filme na web por ID " + id + ": " + e.getMessage());
            }

            System.out.println("Filme com ID " + id + " não foi encontrado em nenhuma fonte.");
            return null;
        }
    }
    private FilmeDTO buscarFilmeNaWebPorNome(String nomeFilme) {
        try {
            String nomeCodificado = URLEncoder.encode(nomeFilme, StandardCharsets.UTF_8);

            // Endpoint para busca de FILMES (/search/movie)
            String endereco = String.format("https://api.themoviedb.org/3/search/movie?%s&language=pt-BR&query=%s", API_KEY, nomeCodificado);

            String json = consumoAPI.obterDados(endereco);
            // Usa ResultadoAPI com DadosFilme para desserializar a lista de resultados
            ResultadoAPI<DadosFilme> resultado = converteDados.obterListaDeDados(json, DadosFilme.class);

            if (resultado != null && !resultado.getResults().isEmpty()) {
                // Pega o primeiro resultado da lista (geralmente o mais relevante)
                DadosFilme dados = resultado.getResults().get(0);
                Filme filme = new Filme(dados);

                System.out.println("Salvando filme '" + filme.getTitle() + "' no banco de dados.");
                // Salva no repositório local
                filmeRepository.save(filme);
                return converteDados(filme);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar filme na web por nome: " + e.getMessage());
        }
        // Retorna null se não encontrar resultados ou se ocorrer um erro
        return null;
    }

}
