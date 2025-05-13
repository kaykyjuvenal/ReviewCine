package org.br.edu.ifsp.reviewcine.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "elencos")

public class Elenco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int idFilme;
    private int idSerie;
    @OneToMany
    private List<Pessoa> pessoas;

    public Elenco() {
    }

    public Elenco(int idFilme, int idSerie) {
        this.idFilme = idFilme;
        this.idSerie = idSerie;
        List<Pessoa> pessoasElenco;
        pessoasElenco = new ArrayList<>();
        this.pessoas = pessoasElenco;
    }

    public Elenco(DadosElenco dadosElenco) {
        this.pessoas = dadosElenco.pessoas().stream().toList();
        this.idFilme = dadosElenco.idFilme();
        this.idSerie = dadosElenco.idSerie();
        this.id = Long.parseLong(UUID.randomUUID().toString().replaceAll("[^0-9]", ""));
    }

    public long getId() {
        return id;
    }

    public int getIdFilme() {
        return idFilme;
    }

    public int getIdSerie() {
        return idSerie;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }
}
