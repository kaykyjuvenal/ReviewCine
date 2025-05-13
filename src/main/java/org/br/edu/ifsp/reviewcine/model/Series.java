package org.br.edu.ifsp.reviewcine.model;

import jakarta.persistence.*;

@Entity
@Table(name = "series")
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double vote_average;
    private int vote_count;
    private String first_air_date;
    private boolean adult;
    private double popularity;
    @OneToOne
    private Elenco elenco;

    public Series(DadosSeries dadosSeries) {

            this.name = dadosSeries.nome();
            this.vote_average = dadosSeries.vote_average();
            this.vote_count = dadosSeries.vote_count();
            this.first_air_date = dadosSeries.first_air_date();
            this.adult = dadosSeries.adult();
            this.popularity = dadosSeries.popularity();
            this.elenco = new Elenco(dadosSeries.elenco());
    }

    public Series(int id, String name, double vote_average, int vote_count, String first_air_date, boolean adult, double popularity, Elenco elenco) {
        this.id = id;
        this.name = name;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.first_air_date = first_air_date;
        this.adult = adult;
        this.popularity = popularity;
        this.elenco = elenco;
    }

    public Series() {

    }
}
