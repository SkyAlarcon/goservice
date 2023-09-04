package com.soulcode.goserviceapp.domain;

import com.soulcode.goserviceapp.domain.enums.Perfil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="mensagens")
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Um remetente deve ser selecionado")
    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario remetente;

    @NotNull(message = "Um destinatário deve ser selecionado")
    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario destinatario;

    @NotNull(message = "Mensagem não pode estar vazia")
    @Column(nullable = false, length = 2000)
    private String conteudo;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime dataHora;

    @NotNull(message = "Mensagem precisa ser relacionada a um agendamento")
    @ManyToOne
    @JoinColumn(nullable = false)
    private Agendamento agendamento;

    public Mensagem() {
    }

    public Mensagem(Long id, Usuario remetente, Usuario destinatario, String conteudo, LocalDateTime dataHora, Agendamento agendamento) {
        this.id = id;
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.conteudo = conteudo;
        this.dataHora = dataHora;
        this.agendamento = agendamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getRemetente() {
        return remetente;
    }

    public void setRemetente(Usuario remetente) {
        this.remetente = remetente;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Usuario destinatario) {
        this.destinatario = destinatario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }
}
