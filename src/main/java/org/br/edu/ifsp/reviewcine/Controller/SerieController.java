package org.br.edu.ifsp.reviewcine.Controller;

import org.br.edu.ifsp.reviewcine.model.dto.SerieDTO;
import org.br.edu.ifsp.reviewcine.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/series") // Define o prefixo da URL para todos os endpoints de séries
@CrossOrigin(origins = "*")
public class SerieController {

    private final SerieService serieService;

    @Autowired
    public SerieController(SerieService serieService) {
        this.serieService = serieService;
    }


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
    @GetMapping("/obterPorParte")
    public List<SerieDTO> obterPorParte(@RequestParam("keyword") String keyWord) {
        return serieService.obterSeriesPorPalavraChave(keyWord);
    }


    @GetMapping("/porPopularidade")
    public List<SerieDTO> obterTodasAsSeriesPorPopularidade() {
        return serieService.obterTodasAsSeries(); // Supondo que este método exista
    }
}
