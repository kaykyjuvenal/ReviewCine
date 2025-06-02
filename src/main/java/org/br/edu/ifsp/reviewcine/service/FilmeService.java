package org.br.edu.ifsp.reviewcine.service;

import org.br.edu.ifsp.reviewcine.model.Filme;
import org.br.edu.ifsp.reviewcine.Results.ResultadoAPI;
import org.br.edu.ifsp.reviewcine.model.dados.DadosFilme;
import org.br.edu.ifsp.reviewcine.model.dto.FilmeDTO;
import org.br.edu.ifsp.reviewcine.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FilmeService {
    @Autowired
    private FilmeRepository filmeRepository;

    private final int idFilmePesquisado = 5;
    private final ConverteDados converteDados = new ConverteDados();
    private final String API_KEY = "api_key=cd190993f189e0a225dc0799ddb4b9d1&";
    private String ENDERECO_FILME = "https://api.themoviedb.org/3/discover/movie?" + API_KEY + "&primary_release_year=2025";
    private final String ENDERECO_FILME_UNICO = "https://api.themoviedb.org/3/movie/" + idFilmePesquisado + '?'+ API_KEY;
    private ConsumoAPI consumoAPI = new ConsumoAPI();



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
    public FilmeDTO obterPorId(long id){
        Optional<Filme> filme = filmeRepository.findById(id);
        if(filme.isPresent()){
            return converteDados(filme.get());
        }
        return null;
    }
    public void save(FilmeDTO filmeDTO){
        Optional<Filme> existente = filmeRepository.findById(filmeDTO.id());
        Filme filme = new Filme(filmeDTO);
        if (existente.isPresent()) {
            filmeRepository.save(filme); // faz update
        } else {
            filme.setId(null); // remove o ID para forçar INSERT
            filmeRepository.save(filme); // faz insert
        }
    }

    public FilmeRepository getFilmeRepository() {
        return filmeRepository;
    }

    public List<Filme> findAll(){
        return filmeRepository.findAll();
    }
    private List<DadosFilme> getTodosFilme() {
        System.out.println("Coletando todos os filmes!");
        int paginaAtual = 1;
        int totalPaginas = 1;
        var todosFilmes = new ArrayList<DadosFilme>();
        do {
            ENDERECO_FILME = ENDERECO_FILME + "&page=" + paginaAtual;
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
            this.save(filmeDTO);
        }
        System.out.println("Filmes armazenados com sucesso no banco de dados!");

    }
    public void buscarFilmeWeb() {
        Filme filmeWeb = getUnicFilme();
        this.save(converteDados(filmeWeb));
        System.out.println("Filme: " + filmeWeb.toString());

    }
    private Filme getUnicFilme(){
        var json2 = consumoAPI.obterDados(ENDERECO_FILME_UNICO);
        return new Filme(converteDados.obterDados(json2, DadosFilme.class));
    }
}
