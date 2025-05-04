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



}
