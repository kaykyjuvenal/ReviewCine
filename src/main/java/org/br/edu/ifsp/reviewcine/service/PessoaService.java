package org.br.edu.ifsp.reviewcine.service;

import org.br.edu.ifsp.reviewcine.model.Pessoa;
import org.br.edu.ifsp.reviewcine.repository.ElencoRepository;
import org.br.edu.ifsp.reviewcine.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    public void savePessoa(Pessoa pessoa) {
        pessoaRepository.save(pessoa);
    }
}
