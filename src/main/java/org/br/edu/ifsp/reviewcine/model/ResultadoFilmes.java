package org.br.edu.ifsp.reviewcine.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResultadoFilmes<T>{
    private int page;
    @JsonProperty("results")
    private List<T> results;
    @JsonProperty("total_pages")
    private int totalPages;
    @JsonProperty("total_results")
    private int totalResults;

    public int getPage() {
        return page;
    }

    public List<T> getResults() {
        return results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

}
