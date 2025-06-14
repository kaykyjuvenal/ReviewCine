package org.br.edu.ifsp.reviewcine.model;

import jakarta.persistence.*;
import org.br.edu.ifsp.reviewcine.model.dados.DadosSerie;
import org.br.edu.ifsp.reviewcine.model.dto.SerieDTO;

@Entity
@Table(name = "series")
public class Serie {
    @Id
    private long id;
    private String name;
    private double vote_average;
    private int vote_count;
    private String first_air_date;
    private boolean adult;
    private int popularity;
    private String original_language;
    @Column(length = 4000) // aumenta o limite da coluna para 4000 caracteres
    private String overview;


    public Serie(DadosSerie dadosSeries) {
        this.id = dadosSeries.id();
        this.name = dadosSeries.name();
        this.vote_average = dadosSeries.vote_average();
        this.vote_count = dadosSeries.vote_count();
        this.first_air_date = dadosSeries.first_air_date();
        this.adult = dadosSeries.adult();
        this.popularity = dadosSeries.popularity();
        this.original_language = dadosSeries.original_language();
        this.overview = dadosSeries.overview();
    }

    public Serie(SerieDTO serieDTO) {
        this.id = serieDTO.id();
        this.name = serieDTO.name();
        this.vote_average = serieDTO.vote_average();
        this.vote_count = serieDTO.vote_count();
        this.first_air_date = serieDTO.first_air_date();
        this.adult = serieDTO.adult();
        this.popularity = serieDTO.popularity();
        this.original_language = serieDTO.original_language();
        this.overview = serieDTO.overview();
    }



    public Serie() {

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getVote_average() {
        return vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public int getPopularity() {
        return popularity;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOverview() {
        return overview;
    }


    @Override
    public String toString() {
        return "Serie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                ", first_air_date='" + first_air_date + '\'' +
                ", adult=" + adult +
                ", popularity=" + popularity +
                ", original_language='" + original_language + '\'' +
                '}';
    }
}
