package com.soulcode.goserviceapp.service;

import com.soulcode.goserviceapp.domain.Cliente;
import com.soulcode.goserviceapp.domain.Endereco;
import com.soulcode.goserviceapp.domain.Prestador;
import com.soulcode.goserviceapp.domain.Usuario;
import com.soulcode.goserviceapp.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Endereco findEnderecoById(Long id){
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        if(endereco.isPresent()){
            return endereco.get();
        }else{
            throw new RuntimeException("Endereço não encontrado");
        }
    }

    @Transactional
    public void updateEndereco(String logradouro, Integer numero, String complemento, String cidade, String uf, Long id, Endereco endereco){
        System.err.println("deu erro logo no início");
        if(endereco != null) {
            enderecoRepository.updateEndById(logradouro, numero, complemento, cidade, uf, id);
        }else{
            System.err.println("deu ruim no update :(");
            throw new RuntimeException("Endereço não encontrado");
        }
    }

}
