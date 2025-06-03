package org.br.edu.ifsp.reviewcine.Controller;

import org.br.edu.ifsp.reviewcine.model.Serie;
import org.br.edu.ifsp.reviewcine.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/series")
@RestController
public class SerieController {
    @Autowired
    private SerieService serieService;

    @GetMapping
    public List<Serie> GetAllSeries() {
        return serieService.findAll();
    }
   //
   //@GetMapping("/top3")
    //public List<Serie> GetTop3Series() {
     //   return serieService.getTop3;
    //}

}
