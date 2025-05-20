package org.br.edu.ifsp.reviewcine.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.hibernate.dialect.function.DB2SubstringFunction;

public class DadosPessoa {

    @JsonAlias("adult")String adult;
    @JsonAlias("gender") Integer  gender;
    @JsonAlias("id") Long id;
    @JsonAlias("know_for_department") String department;
    @JsonAlias("name") String name;
    @JsonAlias("original_name") String original_name;
    @JsonAlias("popularity") Double popularity;

}
