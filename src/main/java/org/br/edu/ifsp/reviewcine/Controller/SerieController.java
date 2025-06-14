package org.br.edu.ifsp.reviewcine.controller;

import org.br.edu.ifsp.reviewcine.model.dto.SerieDTO;
import org.br.edu.ifsp.reviewcine.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/series") // Define o prefixo da URL para todos os endpoints de séries
public class SerieController {

    private final SerieService serieService;

    // Injeção de dependência via construtor
    @Autowired
    public SerieController(SerieService serieService) {
        this.serieService = serieService;
    }

    /**
     * Endpoint para buscar todas as séries salvas no banco de dados.
     * URL: GET /series
     */
    @GetMapping
    public List<SerieDTO> obterTodasAsSeries() {
        return serieService.obterTodasAsSeries();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SerieDTO> obterPorId(@PathVariable Long id) {
        SerieDTO serie = serieService.obterPorId(id);
        if (serie != null) {
            return ResponseEntity.ok(serie); // Retorna 200 OK com a série no corpo
        }
        return ResponseEntity.notFound().build(); // Retorna 404 Not Found se não encontrar
    }

    @GetMapping("/obterPorNome")
    public ResponseEntity<SerieDTO> obterPorNome(@RequestParam("nome") String nome) {
        // O método obterPorNome na SerieService retorna a entidade, então precisamos converter para DTO
        // Idealmente, o serviço já retornaria o DTO, mas podemos adaptar aqui.
        // Se seu serieService.obterPorNome já retorna DTO, esta conversão não é necessária.
        SerieDTO serie = serieService.obterPorNome(nome);
        if (serie != null) {
            return ResponseEntity.ok(serie);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/obterTop3SeriesPopulares")
    public List<SerieDTO> obterTop3SeriesPopulares() {
        return serieService.obterTop3SeriesMaisPopulares(); // Supondo que este método exista na SerieService
    }


    @GetMapping("/porPopularidade")
    public List<SerieDTO> obterTodasAsSeriesPorPopularidade() {
        return serieService.obterTodasAsSeries(); // Supondo que este método exista
    }
}
