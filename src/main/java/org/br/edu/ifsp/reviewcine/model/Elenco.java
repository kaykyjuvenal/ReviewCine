package org.br.edu.ifsp.reviewcine.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "elencos")

public class Elenco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private int idFilme;
    @OneToOne
    private int idSerie;
    @ManyToMany
    private List<Pessoa> pessoas;
}
