package org.br.edu.ifsp.reviewcine.service;

import org.br.edu.ifsp.reviewcine.model.Elenco;
import org.br.edu.ifsp.reviewcine.model.Filme;
import org.br.edu.ifsp.reviewcine.model.Pessoa;
import org.br.edu.ifsp.reviewcine.model.Serie;
import org.br.edu.ifsp.reviewcine.model.dados.DadosElenco;
import org.br.edu.ifsp.reviewcine.model.dto.ElencoDTO;
import org.br.edu.ifsp.reviewcine.model.dto.SerieDTO;
import org.br.edu.ifsp.reviewcine.repository.ElencoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

            Elenco elenco = new Elenco(dadosElenco);

            for (Pessoa pessoa : elenco.getPessoas()){
                pessoaService.savePessoa(pessoa);
            };
            elencoRepository.save(elenco);

            return converteDadosParaDTO(elenco);
        } catch (Exception e) {
            System.err.println("Falha ao obter ou processar dados do elenco da API: " + e.getMessage());
            return null;
        }
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