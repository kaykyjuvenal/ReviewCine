package org.br.edu.ifsp.reviewcine.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "elencos")

public class Elenco {
    @Id
    private long id;
    //@OneToMany
    //private List<Pessoa> pessoas;

    public Elenco() {
    }

    public Elenco(int id) {
        this.id = id;
        List<Pessoa> pessoasElenco;
        pessoasElenco = new ArrayList<>();
        //this.pessoas = pessoasElenco;
    }

    public Elenco(DadosElenco dadosElenco) {
        //this.pessoas = dadosElenco.pessoas().stream().toList();
        this.id = dadosElenco.id();    }

    public long getId() {
        return id;
    }

   // public List<Pessoa> getPessoas() {
     //   return pessoas;
   // }
}
