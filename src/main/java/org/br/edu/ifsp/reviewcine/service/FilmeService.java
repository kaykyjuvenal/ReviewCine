package org.br.edu.ifsp.reviewcine.service;

import org.br.edu.ifsp.reviewcine.model.Filme;
import org.br.edu.ifsp.reviewcine.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class FilmeService {
    @Autowired
    private FilmeRepository filmeRepository;

    public List<FilmeDTO> obterTodosOsFilmes(){return converteDados(filmeRepository.findAll());}
    public FilmeDTO converteDados(Filme filme){
        return new FilmeDTO(filme.isAdult(),filme.getId(), filme.getLanguage(),
                filme.getOverview(),filme.getPopularity(), filme.getRelease_date(), filme.getTitle(),
                filme.getVote_average(), filme.getVote_count());
    }
    public List<FilmeDTO> converteDados(List<Filme> filmes){
        return filmes.stream()
                .map(filme-> new FilmeDTO(filme.isAdult(), filme.getId()
                , filme.getLanguage(),filme.getOverview() , filme.getPopularity()
                , filme.getRelease_date(), filme.getTitle(), filme.getVote_average(),
                        filme.getVote_count())).toList();
    }
    public FilmeDTO obterPorId(long id){
        Optional<Filme> filme = filmeRepository.findById(id);
        if(filme.isPresent()){
            return converteDados(filme.get());
        }
        return null;
    }
    public void save(FilmeDTO filmeDTO){
        Optional<Filme> existente = filmeRepository.findById(filmeDTO.id());
        Filme filme = new Filme(filmeDTO);
        if (existente.isPresent()) {
            filmeRepository.save(filme); // faz update
        } else {
            filme.setId(null); // remove o ID para forçar INSERT
            filmeRepository.save(filme); // faz insert
        }
    }

    public FilmeRepository getFilmeRepository() {
        return filmeRepository;
    }

    public List<Filme> findAll(){
        return filmeRepository.findAll();
    }

}
