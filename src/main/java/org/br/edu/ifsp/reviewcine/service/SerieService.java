package org.br.edu.ifsp.reviewcine.service;

import org.br.edu.ifsp.reviewcine.model.Serie;
import org.br.edu.ifsp.reviewcine.Results.ResultadoAPI;
import org.br.edu.ifsp.reviewcine.model.dados.DadosSerie;
import org.br.edu.ifsp.reviewcine.model.dto.SerieDTO;
import org.br.edu.ifsp.reviewcine.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SerieService {
    @Autowired
    private SerieRepository serieRepository;
    private final int idSeriePesquisado = 276253;

    private final String API_KEY = "api_key=cd190993f189e0a225dc0799ddb4b9d1&";
    private String ENDERECO_SERIE = "https://api.themoviedb.org/3/discover/tv?" + API_KEY + "&first_air_date.gte=2025-01-01&first_air_date.lte=2025-12-31";
    private final String ENDERECO_SERIE_UNICO = "https://api.themoviedb.org/3/movie/" + idSeriePesquisado + '?'+ API_KEY + "&first_air_date.gte=2025-01-01&first_air_date.lte=2025-12-31" ;

    private ConverteDados converteDados = new ConverteDados();
    private ConsumoAPI consumoAPI = new ConsumoAPI();

    public List<SerieDTO> obterTodosAsSeries(){return converteDados(serieRepository.findAll());}
    public SerieDTO converteDados(Serie serie){
        return new SerieDTO(serie.getId(), serie.getName(), serie.getVote_average(),
                serie.getVote_count(), serie.getFirst_air_date(),serie.isAdult() ,
                serie.getPopularity(), serie.getOriginal_language(), serie.getOverview());
    }
    public List<SerieDTO> converteDados(List<Serie> series){
        return series.stream()
                .map(serie-> new SerieDTO(serie.getId(), serie.getName(), serie.getVote_average(),
                        serie.getVote_count(), serie.getFirst_air_date(),serie.isAdult() ,
                        serie.getPopularity(), serie.getOriginal_language(), serie.getOverview())).toList();
    }
    public SerieDTO obterPorId(long id){
        Optional<Serie> serie = serieRepository.findById(id);
        return serie.map(this::converteDados).orElse(null);
    }
    public void save(SerieDTO serieDTO){
        Optional<Serie> existente = serieRepository.findById(serieDTO.id());
        Serie serie = new Serie(serieDTO);
        if (existente.isPresent()) {
            serieRepository.save(serie); // faz update
        }
    }

    public SerieRepository getSerieRepository() {
        return serieRepository;
    }

    public List<Serie> findAll(){
        return serieRepository.findAll();
    }
    private List<DadosSerie> getTodosSerie() {
        System.out.println("Coletando todos os series!");
        int paginaAtual = 1;
        int totalPaginas = 1;
        var todosSeries = new ArrayList<DadosSerie>();
        do {
            ENDERECO_SERIE = ENDERECO_SERIE + "&page=" + paginaAtual;
            try{
                var json = consumoAPI.obterDados(ENDERECO_SERIE);
                System.out.println("JSON: " + json);
                ResultadoAPI<DadosSerie> resultado = converteDados.obterListaDeDados(json, DadosSerie.class);
                if (resultado != null){
                    todosSeries.addAll(resultado.getResults());
                    totalPaginas = resultado.getTotalPages();
                }else{
                    break;
                }
                paginaAtual++;
            }
            catch(Exception e){
                e.printStackTrace();
                break;
            }

        } while (paginaAtual <= totalPaginas);
        return todosSeries;
    }
    public void buscarSeriesWeb() {
        List<DadosSerie> seriesWeb = getTodosSerie();
        for (DadosSerie serie : seriesWeb) {
            System.out.println(serie.toString());
            serieRepository.save(new Serie(serie));
        }
        System.out.println("Series armazenados com sucesso no banco de dados!");

    }
    public void buscarSerieWeb(long id) {
        Optional<Serie> serieWeb = serieRepository.findById(id);
        System.out.println("Serie: " + serieWeb.toString());

    }


}
