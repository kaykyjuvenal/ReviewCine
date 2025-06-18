package org.br.edu.ifsp.reviewcine.Controller;

import org.br.edu.ifsp.reviewcine.model.dto.ElencoDTO;
import org.br.edu.ifsp.reviewcine.service.ElencoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/elencos") // Define o prefixo da URL para todos os endpoints de elenco
@CrossOrigin(origins = "*")

public class ElencoController {

    private final ElencoService elencoService;

    @Autowired
    public ElencoController(ElencoService elencoService) {
        this.elencoService = elencoService;
    }

    /**
     * Endpoint para buscar o elenco de um filme pelo nome do filme.
     * URL de Exemplo: GET /elencos/por-filme?nome=Titanic
     */
    @GetMapping("/por-filme")
    public ResponseEntity<ElencoDTO> obterElencoPorFilme(@RequestParam("nome") String nomeFilme) {
        ElencoDTO elenco = elencoService.obterPorFilme(nomeFilme);
        if (elenco != null) {
            return ResponseEntity.ok(elenco); // Retorna 200 OK com os dados do elenco
        }
        return ResponseEntity.notFound().build(); // Retorna 404 Not Found se não encontrar
    }

    /**
     * Endpoint para buscar o elenco de uma série pelo nome da série.
     * URL de Exemplo: GET /elencos/por-serie?nome=WandaVision
     */
    @GetMapping("/por-serie")
    public ResponseEntity<ElencoDTO> obterElencoPorSerie(@RequestParam("nome") String nomeSerie) {
        ElencoDTO elenco = elencoService.obterPorSerie(nomeSerie);
        if (elenco != null) {
            return ResponseEntity.ok(elenco);
        }
        return ResponseEntity.notFound().build();
    }
}