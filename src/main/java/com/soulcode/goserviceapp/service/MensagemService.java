package com.soulcode.goserviceapp.service;

import com.soulcode.goserviceapp.domain.Agendamento;
import com.soulcode.goserviceapp.domain.Mensagem;
import com.soulcode.goserviceapp.domain.Usuario;
import com.soulcode.goserviceapp.repository.MensagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MensagemService {
    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired AgendamentoService agendamentoService;

    public List<Mensagem> findMensagens(Long id) {
        return mensagemRepository.findMensagens(id);
    }

    public Mensagem enviarMensagem(String conteudo, Long remententeId, Long destinatarioId, Long agendamentoId) {
        Mensagem mensagem = new Mensagem();
        Agendamento agendamento = agendamentoService.findById(agendamentoId);
        Usuario remetente = usuarioService.findById(remententeId);
        Usuario destinatario = usuarioService.findById(destinatarioId);
        LocalDateTime dataHora = LocalDateTime.now();
        mensagem.setAgendamento(agendamento);
        mensagem.setRemetente(remetente);
        mensagem.setDestinatario(destinatario);
        mensagem.setConteudo(conteudo);
        mensagem.setDataHora(dataHora);
        System.out.println(mensagem);
        return mensagemRepository.save(mensagem);
    }
}
