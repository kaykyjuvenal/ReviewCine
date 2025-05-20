package org.br.edu.ifsp.reviewcine;

import org.br.edu.ifsp.reviewcine.model.DadosFilme;
import org.br.edu.ifsp.reviewcine.model.Filme;
import org.br.edu.ifsp.reviewcine.model.ResultadoFilmes;
import org.br.edu.ifsp.reviewcine.repository.FilmeRepository;
import org.br.edu.ifsp.reviewcine.service.ConsumoAPI;
import org.br.edu.ifsp.reviewcine.service.ConverteDados;
import org.br.edu.ifsp.reviewcine.service.FilmeDTO;
import org.br.edu.ifsp.reviewcine.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.SimpleTimeZoneAwareLocaleContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.swing.plaf.synth.SynthListUI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
@Component
public class Main {
        private Scanner leitura = new Scanner(System.in);
        private ConsumoAPI consumoAPI = new ConsumoAPI();

        private final int idFilmePesquisado = 5;
        private final int idSeriePesquisado = 5;

        private ConverteDados converteDados = new ConverteDados();
        private final String API_KEY = "api_key=cd190993f189e0a225dc0799ddb4b9d1&";
        private final String ENDERECO_SERIE = "https://api.themoviedb.org/3/discover/tv?" + API_KEY + "&primary_release_year=2025";
        private String ENDERECO_FILME = "https://api.themoviedb.org/3/discover/movie?" + API_KEY + "&primary_release_year=2025";
        private final String ENDERECO_FILME_UNICO = "https://api.themoviedb.org/3/movie/" + idFilmePesquisado + '?'+ API_KEY;

        private final String ENDERECO_ELENCO_FILME = "https://api.themoviedb.org/3/movie/" + idFilmePesquisado + "/credits?" + API_KEY;
        private final String ENDERECO_ELENCO_SERIE = "https://api.themoviedb.org/3/tv/" + idSeriePesquisado + "/credits?" + API_KEY;
        private List<Filme> filmes = new ArrayList<>();
        @Autowired
        private FilmeService filmeService;

        public void menu() {
                var opcao = -1;
                while (opcao != 0) {
                        var menu = """
                                1 - Carregar dataBase
                                2 - Mostrar dados da database
                                3 - Mostrar banco de dados
                                """;
                        System.out.println(menu);
                        opcao = leitura.nextInt();
                        switch (opcao) {
                                case 1:
                                        buscarFilmesWeb();
                                case 2:
                                        buscarFilmeWeb();
                                case 3:
                                        filmes = filmeService.findAll();
                                        System.out.println(filmes.toString());
                        }
                }

        }

        private void buscarFilmesWeb() {
                List<DadosFilme> filmesWeb = getTodosFilme();
                System.out.println(filmesWeb.toString());
                for (DadosFilme filme : filmesWeb) {
                        System.out.println(filme.toString());
                        FilmeDTO filmeDTO = filmeService.converteDados(new Filme(filme));
                        filmeService.save(filmeDTO);
                }
                System.out.println("Filmes: " + filmes.toString());

        }
        private void buscarFilmeWeb() {
                Filme filmeWeb = getUnicFilme();
                System.out.println("Filme: " + filmeWeb.toString());

        }
        private Filme getUnicFilme(){
                var json2 = consumoAPI.obterDados(ENDERECO_FILME_UNICO);
                return new Filme(converteDados.obterDados(json2, DadosFilme.class));
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
                                ResultadoFilmes<DadosFilme> resultado = converteDados.obterListaDeDados(json, DadosFilme.class);
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

}
