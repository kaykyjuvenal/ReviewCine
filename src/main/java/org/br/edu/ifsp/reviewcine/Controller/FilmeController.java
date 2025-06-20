package org.br.edu.ifsp.reviewcine.Controller;

import org.br.edu.ifsp.reviewcine.model.Filme;
import org.br.edu.ifsp.reviewcine.model.dto.FilmeDTO;
import org.br.edu.ifsp.reviewcine.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filmes") // Define o prefixo da URL para todos os endpoints nesta classe
@CrossOrigin(origins = "*")

public class FilmeController {
    @Autowired
    private final FilmeService filmeService;

    // Injeção de dependência via construtor
    @Autowired
    public FilmeController(FilmeService filmeService) {
        this.filmeService = filmeService;
    }


    @GetMapping
    public List<FilmeDTO> obterTodosOsFilmes() {
        return filmeService.obterTodosOsFilmes();
    }


    @GetMapping("/{id}")
    public ResponseEntity<FilmeDTO> obterPorId(@PathVariable Long id) {
        FilmeDTO filme = filmeService.obterPorId(id);
        if (filme != null) {
            return ResponseEntity.ok(filme); // Retorna 200 OK com o filme no corpo
        }
        return ResponseEntity.notFound().build(); // Retorna 404 Not Found se não encontrar
    }

    @GetMapping("/obterPorNome")
    public ResponseEntity<FilmeDTO> obterPorNome(@RequestParam("titulo") String titulo) {
        FilmeDTO filme = filmeService.obterPorNome(titulo);
        if (filme != null) {
            return ResponseEntity.ok(filme);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/obterPorParte")
    public ResponseEntity<List<FilmeDTO>> obterPorParte(@RequestParam("nome") String nome) {
        List<FilmeDTO> filmes = filmeService.obterFilmesPorPalavraChave(nome);
        if(filmes.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(filmes);
        }
    }
    @GetMapping("/obterPorPopularidade")
    public List<FilmeDTO> obterPorPopularidade() {
        return filmeService.obterTodosOsFilmesPorPopularidade();
    }

    @GetMapping("/obterTop5FilmesPopulares")
    public List<FilmeDTO> obterTop5FilmesPopulares() {
        return filmeService.obterTop5FilmesPopulares();
    }


}