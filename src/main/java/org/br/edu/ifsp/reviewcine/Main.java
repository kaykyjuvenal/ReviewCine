package org.br.edu.ifsp.reviewcine;

import org.br.edu.ifsp.reviewcine.service.ConsumoAPI;
import org.br.edu.ifsp.reviewcine.service.ConverteDados;
import org.springframework.context.i18n.SimpleTimeZoneAwareLocaleContext;

import java.util.Scanner;

public class Main {
        private Scanner leitura = new Scanner(System.in);
        private ConsumoAPI consumoAPI = new ConsumoAPI();
        private ConverteDados converteDados = new ConverteDados();
        private final String ENDERECO_SERIE = "https://api.themoviedb.org/3/discover/tv?";
        private final String ENDERECO_FILME = "https://api.themoviedb.org/3/discover/movie?";
        private final int idFilmePesquisado = 5;
        private final int idSeriePesquisado = 5;
        private final String API_KEY = "api_key=cd190993f189e0a225dc0799ddb4b9d1&";
        private final String ENDERECO_ELENCO_FILME =  "https://api.themoviedb.org/3/movie/" + idFilmePesquisado + "/credits?";
        private final String ENDERECO_ELENCO_SERIE =  "https://api.themoviedb.org/3/tv/" + idSeriePesquisado + "/credits?";

}
