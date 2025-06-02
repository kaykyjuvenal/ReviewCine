package org.br.edu.ifsp.reviewcine.model.dados;

import com.fasterxml.jackson.annotation.JsonAlias;

public class DadosPessoa {

    @JsonAlias("adult")String adult;
    @JsonAlias("gender") Integer  gender;
    @JsonAlias("id") Long id;
    @JsonAlias("know_for_department") String department;
    @JsonAlias("name") String name;
    @JsonAlias("character") String character;
    @JsonAlias("popularity") Double popularity;


}
