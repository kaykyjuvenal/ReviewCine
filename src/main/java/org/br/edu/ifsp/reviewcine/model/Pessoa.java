package org.br.edu.ifsp.reviewcine.model;

import jakarta.persistence.*;
import org.br.edu.ifsp.reviewcine.model.dados.DadosPessoa;

@Entity
@Table(name = "pessoas")

public class Pessoa {
    @Id
    private long id;
    private String adult;
    private int gender;
    private String department;
    private String name;
    private String character;
    private int popularity;

    public Pessoa(){}

    public Pessoa(int id, String adult, int gender, String department, String name, int popularity, String character) {
        this.id = id;
        this.adult = adult;
        this.gender = gender;
        this.department = department;
        this.name = name;
        this.popularity = popularity;
        this.character = character;
    }

    public  Pessoa(DadosPessoa dadosPessoa){
        this.id = dadosPessoa.id();
        this.adult = dadosPessoa.adult();
        this.gender = dadosPessoa.gender();
        this.department = dadosPessoa.department();
        this.name = dadosPessoa.name();
        this.popularity = dadosPessoa.popularity();
        this.character = dadosPessoa.character();

    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", adult='" + adult + '\'' +
                ", gender='" + gender + '\'' +
                ", department='" + department + '\'' +
                ", name='" + name + '\'' +
                ", character='" + character + '\'' +
                ", popularity=" + popularity +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getAdult() {
        return adult;
    }

    public int getGender() {
        return gender;
    }

    public String getDepartment() {
        return department;
    }

    public String getCharacter() {
        return character;
    }

    public String getName() {
        return name;
    }

    public int getPopularity() {
        return popularity;
    }
}
