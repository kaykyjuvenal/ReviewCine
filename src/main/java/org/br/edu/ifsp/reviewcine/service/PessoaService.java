package org.br.edu.ifsp.reviewcine.service;

import org.br.edu.ifsp.reviewcine.Results.ResultadoAPI;
import org.br.edu.ifsp.reviewcine.model.Pessoa;
import org.br.edu.ifsp.reviewcine.model.dados.DadosPessoa; // Você precisará criar esta classe/record
import org.br.edu.ifsp.reviewcine.model.dto.PessoaDTO;
import org.br.edu.ifsp.reviewcine.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    // Adicionando dependências para consumo da API
    private final ConverteDados converteDados;
    private final ConsumoAPI consumoAPI;

    private final String API_KEY = "api_key=cd190993f189e0a225dc0799ddb4b9d1";

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository, ConverteDados converteDados, ConsumoAPI consumoAPI) {
        this.pessoaRepository = pessoaRepository;
        this.converteDados = converteDados;
        this.consumoAPI = consumoAPI;
    }

    public void savePessoa(Pessoa pessoa) {
        if (pessoa != null && !pessoaRepository.existsById(pessoa.getId())) {
            pessoaRepository.save(pessoa);
        }
    }

    /**
     * REFATORADO: Busca uma pessoa pelo ID. Se não encontrar localmente, busca na API do TMDB.
     */
    public PessoaDTO obterPorId(Long id) {
        return pessoaRepository.findById(id)
                .map(this::converteDadosParaDTO)
                .orElseGet(() -> converteDadosParaDTO(buscarPessoaNaWebPorId(id)));
    }

    /**
     * REFATORADO: Busca uma pessoa pelo nome exato. Se não encontrar localmente, busca na API.
     */
    public PessoaDTO obterPorNomeExato(String nome) {
        return pessoaRepository.findByNameIgnoreCase(nome)
                .map(this::converteDadosParaDTO)
                .orElseGet(() -> {
                    System.out.println("Pessoa '" + nome + "' não encontrada localmente. Buscando na web...");
                    List<Pessoa> pessoasDaWeb = buscarPessoasNaWebPorNome(nome);
                    // Retorna a primeira pessoa da lista da web, se houver
                    return pessoasDaWeb.isEmpty() ? null : converteDadosParaDTO(pessoasDaWeb.get(0));
                });
    }

    /**
     * REFATORADO: Busca pessoas por parte do nome. Se a busca local não retornar nada, busca na API.
     */
    public List<PessoaDTO> obterPorParteDoNome(String parteDoNome) {
        List<Pessoa> pessoasLocais = pessoaRepository.findByNameContainingIgnoreCase(parteDoNome);
        if (!pessoasLocais.isEmpty()) {
            return converteDadosParaListaDTO(pessoasLocais);
        } else {
            System.out.println("Nenhuma pessoa com '" + parteDoNome + "' encontrada localmente. Buscando na web...");
            List<Pessoa> pessoasDaWeb = buscarPessoasNaWebPorNome(parteDoNome);
            return converteDadosParaListaDTO(pessoasDaWeb);
        }
    }

    // --- Métodos que buscam apenas no banco de dados local ---
    // A API do TMDB não suporta busca de pessoas por estes critérios.

    public List<PessoaDTO> obterPorPersonagem(String nomePersonagem) {
        List<Pessoa> pessoas = pessoaRepository.findByCharacterContainingIgnoreCase(nomePersonagem);
        return converteDadosParaListaDTO(pessoas);
    }

    public List<PessoaDTO> obterPorDepartamento(String departamento) {
        List<Pessoa> pessoas = pessoaRepository.findByDepartment(departamento);
        return converteDadosParaListaDTO(pessoas);
    }

    public List<PessoaDTO> obterPorListaDeDepartamentos(Collection<String> departamentos) {
        List<Pessoa> pessoas = pessoaRepository.findByDepartmentIn(departamentos);
        return converteDadosParaListaDTO(pessoas);
    }

    public List<PessoaDTO> obterTopPessoasPopulares(int quantidade) {
        Pageable topN = PageRequest.of(0, quantidade);
        List<Pessoa> pessoas = pessoaRepository.findMaisPopulares(topN);
        return converteDadosParaListaDTO(pessoas);
    }

    // --- Métodos Privados de Busca na API ---

    private Pessoa buscarPessoaNaWebPorId(Long id) {
        String endereco = String.format("https://api.themoviedb.org/3/person/%d?%s&language=pt-BR", id, API_KEY);
        try {
            String json = consumoAPI.obterDados(endereco);
            DadosPessoa dadosPessoa = converteDados.obterDados(json, DadosPessoa.class);
            Pessoa pessoa = new Pessoa(dadosPessoa);
            savePessoa(pessoa); // Salva no banco para cache
            return pessoa;
        } catch (Exception e) {
            System.err.println("Erro ao buscar pessoa na web por ID: " + e.getMessage());
            return null;
        }
    }

    private List<Pessoa> buscarPessoasNaWebPorNome(String nome) {
        String endereco = String.format("https://api.themoviedb.org/3/search/person?%s&language=pt-BR&query=%s", API_KEY, URLEncoder.encode(nome, StandardCharsets.UTF_8));
        try {
            String json = consumoAPI.obterDados(endereco);
            ResultadoAPI<DadosPessoa> resultado = converteDados.obterListaDeDados(json, DadosPessoa.class);
            if (resultado != null && !resultado.getResults().isEmpty()) {
                List<Pessoa> pessoas = resultado.getResults().stream().map(Pessoa::new).toList();
                // Salva todas as pessoas encontradas
                pessoas.forEach(this::savePessoa);
                return pessoas;
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar pessoas na web por nome: " + e.getMessage());
        }
        return List.of(); // Retorna lista vazia se não encontrar nada
    }

    // --- Métodos de Conversão para DTO ---

    private PessoaDTO converteDadosParaDTO(Pessoa pessoa) {
        if (pessoa == null) return null;
        return new PessoaDTO(
                pessoa.getAdult(),
                pessoa.getGender(),
                pessoa.getId(),
                pessoa.getName(),
                pessoa.getCharacter(),
                pessoa.getDepartment(),
                pessoa.getPopularity()
                );
    }

    private List<PessoaDTO> converteDadosParaListaDTO(List<Pessoa> pessoas) {
        return pessoas.stream()
                .map(this::converteDadosParaDTO)
                .toList();
    }
}