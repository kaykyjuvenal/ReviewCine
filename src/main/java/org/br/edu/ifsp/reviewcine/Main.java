package org.br.edu.ifsp.reviewcine;

import org.br.edu.ifsp.reviewcine.model.Serie;
import org.br.edu.ifsp.reviewcine.model.Filme;
import org.br.edu.ifsp.reviewcine.repository.ElencoRepository;
import org.br.edu.ifsp.reviewcine.repository.FilmeRepository;
import org.br.edu.ifsp.reviewcine.repository.SerieRepository;
import org.br.edu.ifsp.reviewcine.service.ElencoService;
import org.br.edu.ifsp.reviewcine.service.FilmeService;
import org.br.edu.ifsp.reviewcine.service.PessoaService;
import org.br.edu.ifsp.reviewcine.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Main implements CommandLineRunner {
        private Scanner leitura = new Scanner(System.in);
        private final long idFilmePesquisado = 5;

        @Autowired
        private FilmeService filmeService;
        @Autowired
        private SerieService serieService;
        @Autowired
        private ElencoService elencoService;
        @Autowired
        private PessoaService pessoaService;
        @Autowired
        private ElencoRepository elencoRepository;
        @Autowired
        private FilmeRepository filmeRepository;
        @Autowired
        private SerieRepository serieRepository;


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
                    7 - Obter um elenco (Lógica comentada).
                    8 - Consultas de Filme (Repository)
                    9 - Consultas de Série (Repository)
                    10 - Buscar Elenco por nome de Obra
                                    
                    0 - Sair
                    """;
                        System.out.println(menu);
                        opcao = leitura.nextInt();
                        leitura.nextLine(); // Consumir a nova linha pendente

                        switch (opcao) {
                                case 1:
                                        filmeService.buscarFilmesWeb();
                                        break; // << FIX ADICIONADO
                                case 2:
                                        filmeService.buscarFilmeWeb();
                                        break; // << FIX ADICIONADO
                                case 3:
                                        List<Filme> filmes = filmeService.findAll();
                                        System.out.println(filmes.toString());
                                        break; // << FIX ADICIONADO
                                case 4:
                                        serieService.buscarSeriesWeb();
                                        break; // << FIX ADICIONADO
                                case 5:
                                        serieService.buscarSerieWeb(idFilmePesquisado);
                                        break; // << FIX ADICIONADO
                                case 6:
                                        List<Serie> series = serieService.findAll();
                                        System.out.println(series.toString());
                                        break; // << FIX ADICIONADO
                                case 7:
                                        System.out.println("Lógica de salvar elenco comentada no código.");
                                        //Elenco elenco = new Elenco(elencoService.obterPorId(5));
                                        //for (Pessoa pessoa : elenco.getPessoas()){
                                        //       pessoaService.savePessoa(pessoa);
                                        //};
                                        //elencoRepository.save(elenco);
                                        break; // << FIX ADICIONADO
                                case 8:
                                        System.out.println(filmeRepository.findByTitleContainingIgnoreCase("thunderbolts*"));
                                        System.out.println("Find all By vote: " + filmeRepository.findAllByOrderByVote_averageDesc());
                                        System.out.println("Find all By Language: " + filmeRepository.findAllByLanguage("pt"));
                                        System.out.println("Find all By" + filmeRepository.findAllByPopularityDesc());
                                        break; // << FIX ADICIONADO
                                case 9:
                                        //System.out.println("Find all by Language" + serieRepository.findAllByOriginalLanguageContainingIgnoreCase("en"));
                                        //System.out.println("Find all by Popularity" + serieRepository.findAllByPopularityDesc());
                                        //System.out.println("Find all by Vote Avarage" +serieRepository.findAllByOrderByVoteAverageDesc());
                                        //System.out.println("Find all by FirstAirDateDesc" +serieRepository.findAllByOrderByFirstAirDateDesc());
                                        System.out.println("Find all by Name" +serieRepository.findByNameContainingIgnoreCase("Inve"));
                                        //System.out.println("Find all by Popularity" +serieRepository.findByNameIgnoreCase("Invencible"));
                                        break; // << FIX ADICIONADO
                                case 10:
                                        System.out.println(elencoService.obterPorSerie("WandaVision"));
                                        System.out.println(elencoService.obterPorSerie("Invincible"));
                                        System.out.println(elencoService.obterPorSerie("The Walking Dead"));
                                        System.out.println(elencoService.obterPorFilme("Thunderbolts*"));
                                        System.out.println(elencoService.obterPorFilme("Titanic"));
                                        break; // << FIX ADICIONADO
                        }
                }
        }

        // MÉTODO MAIN CORRIGIDO
        public static void main(String[] args) {
                SpringApplication.run(Main.class, args);
        }

        @Override
        public void run(String... args) throws Exception {
                menu();
        }
}