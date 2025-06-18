package org.br.edu.ifsp.reviewcine.Controller;

import org.springframework.web.bind.annotation.GetMapping;


import org.br.edu.ifsp.reviewcine.model.dto.PessoaDTO;
import org.br.edu.ifsp.reviewcine.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/pessoas") // Define o prefixo da URL para todos os endpoints de pessoas
@CrossOrigin(origins = "*")

public class PessoaController {

    private final PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> obterPorId(@PathVariable Long id) {
        PessoaDTO pessoa = pessoaService.obterPorId(id);
        if (pessoa != null) {
            return ResponseEntity.ok(pessoa); // Retorna 200 OK com os dados
        }
        return ResponseEntity.notFound().build(); // Retorna 404 Not Found
    }


    @GetMapping("/obterPorNomeExato")
    public ResponseEntity<PessoaDTO> obterPorNomeExato(@RequestParam String nome) {
        PessoaDTO pessoa = pessoaService.obterPorNomeExato(nome);
        if (pessoa != null) {
            return ResponseEntity.ok(pessoa);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/obterPorParteDoNome")
    public List<PessoaDTO> obterPorParteDoNome(@RequestParam String nome) {
        return pessoaService.obterPorParteDoNome(nome);
    }

    @GetMapping("/obterPorPersonagem")
    public List<PessoaDTO> obterPorPersonagem(@RequestParam String personagem) {
        return pessoaService.obterPorPersonagem(personagem);
    }

    @GetMapping("/obterPorDepartamento")
    public List<PessoaDTO> obterPorDepartamento(@RequestParam String departamento) {
        return pessoaService.obterPorDepartamento(departamento);
    }

    @GetMapping("/obterPorDepartamentos")
    public List<PessoaDTO> obterPorListaDeDepartamentos(@RequestParam List<String> departamentos) {
        return pessoaService.obterPorListaDeDepartamentos(departamentos);
    }

    @GetMapping("/populares")
    public List<PessoaDTO> obterTopPessoasPopulares(@RequestParam(defaultValue = "6") int top) {
        return pessoaService.obterTopPessoasPopulares(top);
    }
}