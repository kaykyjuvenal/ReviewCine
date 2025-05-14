package org.br.edu.ifsp.reviewcine.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DadosFilme(@JsonAlias ("adult") Boolean adult,
                         @JsonAlias ("backdrop_path") String backdrop_path,
                         @JsonAlias ("genre_ids") List<Integer> genre_ids,

                         @JsonAlias ("vote_average") Integer vote_average,
                         @JsonAlias ("vote_count") Integer vote_count,
                         @JsonAlias ("first_air_date") String first_air_date,
                         @JsonAlias ("adult") Boolean adult,
                         @JsonAlias ("popularity") Double popularity,
                         @JsonAlias ("elencos")List<DadosElenco> elencos){
}
"adult": false,
        "backdrop_path": "/cJvUJEEQ86LSjl4gFLkYpdCJC96.jpg",
        "genre_ids": [
        10752,
        28
        ],
        "id": 1241436,
        "original_language": "en",
        "original_title": "Warfare",
        "overview": "A platoon of Navy SEALs embarks on a dangerous mission in Ramadi, Iraq, with the chaos and brotherhood of war retold through their memories of the event.",
        "popularity": 485.3223,
        "poster_path": "/j8tqBXwH2PxBPzbtO19BTF9Ukbf.jpg",
        "release_date": "2025-04-09",
        "title": "Warfare",
        "video": false,
        "vote_average": 7.1,
        "vote_count": 249