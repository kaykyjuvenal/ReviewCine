package org.br.edu.ifsp.reviewcine.model;

import jakarta.persistence.*;
import org.br.edu.ifsp.reviewcine.model.dados.DadosElenco;
import org.br.edu.ifsp.reviewcine.model.dados.DadosPessoa;
import org.br.edu.ifsp.reviewcine.model.dto.ElencoDTO;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "elencos")

public class Elenco {
    @Id
    private long id;
    @OneToMany
    private List<Pessoa> pessoas;

    public Elenco() {
    }

    public Elenco(int id) {
        this.id = id;
        this.pessoas = new ArrayList<>();
    }

    public Elenco(DadosElenco dadosElenco) {
        this.pessoas = listConvertDadosPessoasToPessoas(dadosElenco.pessoas());
        this.id = dadosElenco.id();    }

    public long getId() {
        return id;
    }
    public Elenco (ElencoDTO elencoDTO){
        this.pessoas = elencoDTO.pessoas();
        this.id = elencoDTO.id();
    }

   public List<Pessoa> getPessoas() {
       return pessoas;
   }
   public List<Pessoa> listConvertDadosPessoasToPessoas(List<DadosPessoa> dadosPessoas){
       return dadosPessoas.stream()
               .map(Pessoa::new)
               .toList();
   }

    @Override
    public String toString() {
        return "Elenco{" +
                "id=" + id +
                ", pessoas=" + pessoas.toString() +
                '}';
    }
}
