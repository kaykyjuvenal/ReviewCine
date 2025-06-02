package org.br.edu.ifsp.reviewcine.model;

import jakarta.persistence.*;
import org.br.edu.ifsp.reviewcine.model.dados.DadosFilme;
import org.br.edu.ifsp.reviewcine.model.dto.FilmeDTO;

@Entity
@Table(name = "filmes")
public class Filme {
    @Id
    private long id;
    private boolean adult;
    private String language;
    @Column(length = 4000) // aumenta o limite da coluna para 4000 caracteres
    private String overview;
    private int popularity;
    private String release_date;
    private String title;
    private double vote_average;
    private int vote_count;

    //@OneToOne
    //private Elenco elenco;

    public Filme() {
    }

    public Filme(FilmeDTO filmeDTO) {
        this.adult = filmeDTO.adult();
        this.id = filmeDTO.id();
        this.language = filmeDTO.language();
        this.popularity = filmeDTO.popularity();
        this.release_date = filmeDTO.release_date();
        this.title = filmeDTO.title();
        this.vote_average = filmeDTO.vote_average();
        this.vote_count = filmeDTO.vote_count();
        this.overview = filmeDTO.overview();
    }


    public Filme(DadosFilme dadosFilme) {
        this.adult = dadosFilme.adult();
        this.id = dadosFilme.id();
        this.language = dadosFilme.original_language();
        this.popularity = dadosFilme.popularity();
        this.release_date = dadosFilme.release_date();
        this.title = dadosFilme.title();
        this.vote_average = dadosFilme.vote_average();
        this.vote_count = dadosFilme.vote_count();
        this.overview = dadosFilme.overview();
    }

    public boolean isAdult() {
        return adult;
    }

    public long getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public String getOverview() {
        return overview;
    }

    public int getPopularity() {
        return popularity;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getTitle() {
        return title;
    }

    public double getVote_average() {
        return vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    @Override
    public String toString() {
        return "Filme{" +
                "id=" + id +
                ", adult=" + adult +
                ", language='" + language + '\'' +
                ", overview='" + overview + '\'' +
                ", popularity=" + popularity +
                ", release_date='" + release_date + '\'' +
                ", title='" + title + '\'' +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                '}';
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId(Object o) {
    }
}

