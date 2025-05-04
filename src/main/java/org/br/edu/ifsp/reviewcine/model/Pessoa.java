package org.br.edu.ifsp.reviewcine.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pessoas")

public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String character;
    private String profile_path;
    private String gender;
    private String artist_name;
    private String department;
    private boolean adult;
    private double popularity;
    private int id_localizacao;
    public Pessoa(){}


}
