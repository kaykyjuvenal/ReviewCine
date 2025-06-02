package org.br.edu.ifsp.reviewcine;

import org.br.edu.ifsp.reviewcine.model.Serie;
import org.br.edu.ifsp.reviewcine.model.Filme;
import org.br.edu.ifsp.reviewcine.service.FilmeService;
import org.br.edu.ifsp.reviewcine.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class Main {
        private Scanner leitura = new Scanner(System.in);
        private final long idFilmePesquisado = 5;
        private final long idSeriePesquisado = 5;
        private final String API_KEY = "api_key=cd190993f189e0a225dc0799ddb4b9d1&";
        private final String ENDERECO_SERIE = "https://api.themoviedb.org/3/discover/tv?" + API_KEY + "&first_air_date.gte=2025-01-01&first_air_date.lte=2025-12-31";
        private String ENDERECO_FILME = "https://api.themoviedb.org/3/discover/movie?" + API_KEY + "&primary_release_year=2025";

        private final String ENDERECO_ELENCO_FILME = "https://api.themoviedb.org/3/movie/" + idFilmePesquisado + "/credits?" + API_KEY;
        private final String ENDERECO_ELENCO_SERIE = "https://api.themoviedb.org/3/tv/" + idSeriePesquisado + "/credits?" + API_KEY;
        private List<Filme> filmes = new ArrayList<>();
        @Autowired
        private FilmeService filmeService;
        @Autowired
        private SerieService serieService;


        public void menu() {
                var opcao = -1;
                while (opcao != 0) {
                        var menu = """
                                1 - Carregar dataBase de filmes no banco de dados
                                2 - Carregar um filme único.
                                3 - Mostrar banco de dados de filme
                                4 - Carregar dados de series no banco de dados  
                                5 - Carregar uma serie unica.
                                6 - Exibir database series.
                                """;
                        System.out.println(menu);
                        opcao = leitura.nextInt();
                        switch (opcao) {
                                case 1:
                                        filmeService.buscarFilmesWeb();
                                case 2:
                                        filmeService.buscarFilmeWeb();
                                case 3:
                                        filmes = filmeService.findAll();
                                        System.out.println(filmes.toString());

                                case 4: serieService.buscarSeriesWeb();

                                case 5: serieService.buscarSerieWeb(idFilmePesquisado);

                                case 6: List<Serie> series = serieService.findAll();
                                        System.out.println(series.toString());
                        }
                }

        }






}
