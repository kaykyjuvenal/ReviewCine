package org.br.edu.ifsp.reviewcine.service;

import org.br.edu.ifsp.reviewcine.Results.ResultadoAPI;
import org.br.edu.ifsp.reviewcine.model.Elenco;
import org.br.edu.ifsp.reviewcine.model.Elenco;
import org.br.edu.ifsp.reviewcine.model.Filme;
import org.br.edu.ifsp.reviewcine.model.Serie;
import org.br.edu.ifsp.reviewcine.model.dados.DadosElenco;
import org.br.edu.ifsp.reviewcine.model.dados.DadosFilme;
import org.br.edu.ifsp.reviewcine.model.dto.ElencoDTO;
import org.br.edu.ifsp.reviewcine.repository.ElencoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class ElencoService {
    @Autowired
    private ElencoRepository elencoRepository;

    private SerieService  serieService;
    private FilmeService filmeService;

    private Scanner scanner = new Scanner(System.in);
    long idElencoPesquisado = 1;
    private final String API_KEY = "api_key=cd190993f189e0a225dc0799ddb4b9d1&";

    private final String ENDERECO_ELENCO_FILME = "https://api.themoviedb.org/3/movie/" + idElencoPesquisado + "/credits?" + API_KEY;
    private final ConverteDados converteDados = new ConverteDados();
    private ConsumoAPI consumoAPI = new ConsumoAPI();


    public ElencoDTO obterPorId(long id){
        Optional<Elenco> elenco = elencoRepository.findById(id);
        if(elenco.isPresent()){
            return converteDados(elenco.get());
        }else {
            String ENDERECO_ELENCO_FILME = "https://api.themoviedb.org/3/movie/" + id + "/credits?" + API_KEY;


            var json = consumoAPI.obterDados(ENDERECO_ELENCO_FILME);
            System.out.println("JSON: " + json);
            Elenco elencoObtido = new Elenco(converteDados.obterDados(json, DadosElenco.class));
            System.out.println("Elenco: " + elencoObtido);

            try{
                System.out.println(elencoObtido);

                if (elencoObtido.getId() == id){

                    return new ElencoDTO(elencoObtido.getId(), elencoObtido.getPessoas());

                }
                else {
                    throw new NullPointerException();
                }
            }catch (Exception e){
                return null;
            }

        }
    }
    private ElencoDTO buscaElenco(Filme filmeObtido){
        String ENDERECO_ELENCO_FILME = "https://api.themoviedb.org/3/movie/" + filmeObtido.getId() + "/credits?" + API_KEY;
        var jsonElenco = consumoAPI.obterDados(ENDERECO_ELENCO_FILME);
        Elenco elenco = new Elenco(converteDados.obterDados(jsonElenco, DadosElenco.class));
        return new ElencoDTO(elenco.getId(),elenco.getPessoas());
    }
    private ElencoDTO obterPorFilme(String nomeFilme){
        Filme filme= filmeService.obterFilmePorNome(nomeFilme);
        if (filme != null){
            Optional<Elenco> elenco = elencoRepository.findById(filme.getId());
            if (elenco.isPresent()){
                return new ElencoDTO(elenco.get().getId(), elenco.get().getPessoas());
            }
            else{
                return buscaElenco(filme);
            }
        }
        else {
            String ENDERECO_FILME = "https://api.themoviedb.org/3/search/movie?"+ API_KEY + "query=${" + nomeFilme +"}";
            var json = consumoAPI.obterDados(ENDERECO_FILME);
            System.out.println("JSON: " + json);
            Filme filmeObtido  = new Filme(converteDados.obterDados(json, DadosFilme.class));
            return buscaElenco(filmeObtido);

        }

    }
    private ElencoDTO obterPorSerie(String nomeSerie){
        Serie  serie = serieService.obterSerie(nomeSerie);
        if (filme != null){
            Optional<Elenco> elenco = elencoRepository.findById(filme.getId());
            if (elenco.isPresent()){
                return new ElencoDTO(elenco.get().getId(), elenco.get().getPessoas());
            }
            else{
                return buscaElenco(filme);
            }
        }
        else {
            String ENDERECO_FILME = "https://api.themoviedb.org/3/search/movie?"+ API_KEY + "query=${" + nomeFilme +"}";
            var json = consumoAPI.obterDados(ENDERECO_FILME);
            System.out.println("JSON: " + json);
            Filme filmeObtido  = new Filme(converteDados.obterDados(json, DadosFilme.class));
            return buscaElenco(filmeObtido);

        }

    }

    private ElencoDTO converteDados(Elenco elenco) {
        return new ElencoDTO(elenco.getId(),elenco.getPessoas());
    }
    private void save(ElencoDTO elencoDTO) {
        elencoRepository.save(new Elenco(elencoDTO)); // faz update
    }
    public List<ElencoDTO> converteDados(List<Elenco> elencos){
        return elencos.stream()
                .map(elenco-> new ElencoDTO(elenco.getId(),elenco.getPessoas())).toList();
    }
}
