package org.br.edu.ifsp.reviewcine.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

@Entity
@Table(name = "pessoas")

public class Pessoa {
    @Id
    private int id;
    private String adult;
    private String gender;
    private String department;
    private String name;
    private String original_name;
    private double popularity;
    private int id_localizacao;

    public Pessoa(){}

    public Pessoa(String adult, String gender, int id, String name, String department, String original_name, double popularity, int id_localizacao) {
        this.id = id;
        this.adult = adult;
        this.gender = gender;

        this.name = name;
        this.department = department;
        this.original_name = original_name;
        this.popularity = popularity;
        this.id_localizacao = id_localizacao;
    }

    public  Pessoa(DadosPessoa dadosPessoa, int id_localizacao){

    }
}
