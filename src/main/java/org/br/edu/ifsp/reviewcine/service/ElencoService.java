package org.br.edu.ifsp.reviewcine.service;

import org.br.edu.ifsp.reviewcine.repository.ElencoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElencoService {
    @Autowired
    private ElencoRepository elencoRepository;
}
