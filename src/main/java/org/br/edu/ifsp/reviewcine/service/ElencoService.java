package org.br.edu.ifsp.reviewcine.service;

import org.br.edu.ifsp.reviewcine.Controller.PessoaController;
import org.br.edu.ifsp.reviewcine.model.Elenco;
import org.br.edu.ifsp.reviewcine.model.Filme;
import org.br.edu.ifsp.reviewcine.model.Pessoa;
import org.br.edu.ifsp.reviewcine.model.Serie;
import org.br.edu.ifsp.reviewcine.model.dados.DadosElenco;
import org.br.edu.ifsp.reviewcine.model.dto.ElencoDTO;
import org.br.edu.ifsp.reviewcine.model.dto.FilmeDTO;
import org.br.edu.ifsp.reviewcine.model.dto.SerieDTO;
import org.br.edu.ifsp.reviewcine.model.dto.adicaoElencoApiDTO;
import org.br.edu.ifsp.reviewcine.repository.ElencoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ElencoService {
    @Autowired
    private PessoaService pessoaService;

    private final ElencoRepository elencoRepository;
    private final SerieService serieService;
    private final FilmeService filmeService;
    private final ConverteDados converteDados;
    private final ConsumoAPI consumoAPI;

    private final String API_KEY = "api_key=cd190993f189e0a225dc0799ddb4b9d1";
    @Autowired
    private PessoaController pessoaController;

    @Autowired
    public ElencoService(ElencoRepository elencoRepository, SerieService serieService, FilmeService filmeService, ConverteDados converteDados, ConsumoAPI consumoAPI) {
        this.elencoRepository = elencoRepository;
        this.serieService = serieService;
        this.filmeService = filmeService;
        this.converteDados = converteDados;
        this.consumoAPI = consumoAPI;
    }

    /**
     * Obtém o elenco de um filme pelo nome.
     */
    public ElencoDTO obterPorFilme(String nomeFilme) {
        Filme filme = new Filme(filmeService.obterPorNome(nomeFilme));

        if (filme == null) {
            System.out.println("Filme '" + nomeFilme + "' não encontrado.");
            return null;
        }

        // Usa o novo método para garantir que a lista 'pessoas' seja carregada
        return elencoRepository.findByIdWithPessoas(filme.getId()) // <-- MUDANÇA AQUI
                .map(this::converteDadosParaDTO)
                .orElseGet(() -> buscaElencoNaWeb(filme));
    }

    // Aplique a mesma lógica para obterPorSerie
    public ElencoDTO obterPorSerie(String nomeSerie) {
        SerieDTO serie = serieService.obterPorNome(nomeSerie);

        if (serie == null) {
            System.out.println("Série '" + nomeSerie + "' não encontrada.");
            return null;
        }
        return elencoRepository.findByIdWithPessoas(serie.id()) // <-- MUDANÇA AQUI
                .map(this::converteDadosParaDTO)
                .orElseGet(() -> buscaElencoNaWeb(new Serie(serie)));
    }
    /**
     * Busca o elenco de um FILME na API externa, SEM FILTRO adicional.
     */
    private ElencoDTO buscaElencoNaWeb(Filme filme) {
        System.out.println("Buscando elenco para o filme: " + filme.getTitle());
        String endereco = String.format("https://api.themoviedb.org/3/movie/%d/credits?%s", filme.getId(), API_KEY);
        return obterElencoDaApi(endereco);
    }


    private ElencoDTO buscaElencoNaWeb(Serie serie) {
        System.out.println("Buscando elenco para a série: " + serie.getName());
        String endereco = String.format("https://api.themoviedb.org/3/tv/%d/credits?%s", serie.getId(), API_KEY);
        return obterElencoDaApi(endereco);
    }


    private ElencoDTO obterElencoDaApi(String endereco) {
        try {
            String jsonElenco = consumoAPI.obterDados(endereco);
            DadosElenco dadosElenco = converteDados.obterDados(jsonElenco, DadosElenco.class);

            if (dadosElenco == null) return null;

            // --- INÍCIO DA LÓGICA ANTI-DUPLICIDADE ---

            // 1. Verifica se um Elenco com este ID (que é o ID do filme/série) já existe
            Optional<Elenco> elencoExistente = elencoRepository.findById(dadosElenco.id());

            if (elencoExistente.isPresent()) {
                System.out.println("Elenco com ID " + dadosElenco.id() + " já existe no banco. Ignorando salvamento e retornando dados existentes.");
                // Se já existe, apenas retorna o DTO do elenco que já estava salvo.
                return converteDadosParaDTO(elencoExistente.get());
            }

            // --- FIM DA LÓGICA ANTI-DUPLICIDADE ---


            // Se o elenco não existe, aí sim continuamos com o processo de salvar.
            System.out.println("Elenco com ID " + dadosElenco.id() + " é novo. Salvando no banco...");
            Elenco elencoNovo = new Elenco(dadosElenco);

            // Salva cada pessoa do elenco (o savePessoa já previne duplicatas de pessoas)
            if (elencoNovo.getPessoas() != null) {
                for (Pessoa pessoa : elencoNovo.getPessoas()) {
                    pessoaService.savePessoa(pessoa);
                }
            }

            // Salva o novo elenco e suas relações
            try {
                elencoRepository.save(elencoNovo);
                return converteDadosParaDTO(elencoNovo);
            } catch (Exception e) {
                System.err.println("Falha ao obter ou processar dados do elenco da API: " + e.getMessage());
            }
            // Retorna o DTO do elenco que acabamos de salvar
            return converteDadosParaDTO(elencoNovo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void popularElencosDeTodaBaseViaApi() throws InterruptedException {
        System.out.println("====== INICIANDO ENRIQUECIMENTO DE DADOS DE ELENCO VIA API HTTP ======");

        try {
            // --- ETAPA 1: PROCESSAR FILMES ---
            System.out.println("\nBuscando lista de filmes do endpoint /filmes...");
            String urlFilmes = "http://localhost:8080/filmes";
            String jsonFilmes = consumoAPI.obterDados(urlFilmes);
            FilmeDTO[] filmesDTOs = converteDados.obterDados(jsonFilmes, FilmeDTO[].class);
            List<adicaoElencoApiDTO
                    > resumoFilmes = Arrays.stream(filmesDTOs)
                    .map(dto -> new adicaoElencoApiDTO
                            (dto.id(), dto.title()))
                    .toList();

            System.out.println(resumoFilmes.size() + " filmes encontrados. Processando elencos...");
            for (adicaoElencoApiDTO
                    filme : resumoFilmes) {
                System.out.println("-> Processando elenco para o filme: " + filme.titulo());
                // Chama o próprio método do serviço para processar
                this.obterPorFilme(filme.titulo());
                Thread.sleep(500); // Pausa para não sobrecarregar as APIs
            }
            System.out.println("--- Processamento de Filmes Concluído ---");


            // --- ETAPA 2: PROCESSAR SÉRIES ---
            System.out.println("\nBuscando lista de séries do endpoint /series...");
            String urlSeries = "http://localhost:8080/series";
            String jsonSeries = consumoAPI.obterDados(urlSeries);
            SerieDTO[] seriesDTOs = converteDados.obterDados(jsonSeries, SerieDTO[].class);
            List<adicaoElencoApiDTO> resumoSeries = Arrays.stream(seriesDTOs)
                    .map(dto -> new adicaoElencoApiDTO
                            (dto.id(), dto.name()))
                    .toList();

            System.out.println(resumoSeries.size() + " séries encontradas. Processando elencos...");
            for (adicaoElencoApiDTO serie : resumoSeries) {
                System.out.println("-> Processando elenco para a série: " + serie.titulo());
                this.obterPorSerie(serie.titulo());
                Thread.sleep(500);
            }
            System.out.println("--- Processamento de Séries Concluído ---");

        } catch (Exception e) {
            System.err.println("!! Ocorreu um erro durante o processamento em lote via API: " + e.getMessage());
            e.printStackTrace();
            // Se a exceção for de interrupção, restaura o status da thread
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("\n====== PROCESSAMENTO EM LOTE VIA API CONCLUÍDO ======");
    }

    private ElencoDTO converteDadosParaDTO(Elenco elenco) {
        if (elenco == null) return null;
        return new ElencoDTO(elenco.getId(), elenco.getPessoas());
    }

    public List<ElencoDTO> converteDadosParaListaDTO(List<Elenco> elencos) {
        return elencos.stream()
                .map(this::converteDadosParaDTO)
                .toList();
    }
}