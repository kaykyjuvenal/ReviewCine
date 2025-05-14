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

    public Pessoa(int id, String profile_path, String character, String gender, String department, String artist_name, boolean adult, int id_localizacao, double popularity) {
        this.id = id;
        this.profile_path = profile_path;
        this.character = character;
        this.gender = gender;
        this.department = department;
        this.artist_name = artist_name;
        this.adult = adult;
        this.id_localizacao = id_localizacao;
        this.popularity = popularity;
    }
    public  Pessoa(DadosPessoa dadosPessoa, int id_localizacao){

    }
}
