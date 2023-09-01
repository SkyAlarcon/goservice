package com.soulcode.goserviceapp.domain;

import com.soulcode.goserviceapp.domain.enums.StatusAgendamento;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Document(collection = "agendamentos")
public class AgendamentoLog {

    @Id
    private String id;

    private LocalDate data;

    private LocalDateTime dataHoraRegistro;

    private LocalTime hora;

    private StatusAgendamento statusAgendamento;

    private Long clienteId;

    private Long prestadorId;

    private Long servicoId;

    @Field(targetType = FieldType.DATE_TIME)
    private LocalDateTime dataLog = LocalDateTime.now();

    public AgendamentoLog() {
    }

    public AgendamentoLog(String id, LocalDate data, LocalDateTime dataHoraRegistro, LocalTime hora, StatusAgendamento statusAgendamento, Long clienteId, Long prestadorId, Long servicoId) {
        this.id = id;
        this.data = data;
        this.dataHoraRegistro = dataHoraRegistro;
        this.hora = hora;
        this.statusAgendamento = statusAgendamento;
        this.clienteId = clienteId;
        this.prestadorId = prestadorId;
        this.servicoId = servicoId;
    }

    public AgendamentoLog (Agendamento agendamento){
        this.data = agendamento.getData();
        this.dataHoraRegistro = agendamento.getDataHoraRegistro();
        this.hora = agendamento.getHora();
        this.statusAgendamento = agendamento.getStatusAgendamento();
        this.clienteId = agendamento.getCliente().getId();
        this.prestadorId = agendamento.getPrestador().getId();
        this.servicoId = agendamento.getServico().getId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalDateTime getDataHoraRegistro() {
        return dataHoraRegistro;
    }

    public void setDataHoraRegistro(LocalDateTime dataHoraRegistro) {
        this.dataHoraRegistro = dataHoraRegistro;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public StatusAgendamento getStatusAgendamento() {
        return statusAgendamento;
    }

    public void setStatusAgendamento(StatusAgendamento statusAgendamento) {
        this.statusAgendamento = statusAgendamento;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getPrestadorId() {
        return prestadorId;
    }

    public void setPrestadorId(Long prestadorId) {
        this.prestadorId = prestadorId;
    }

    public Long getServicoId() {
        return servicoId;
    }

    public void setServicoId(Long servicoId) {
        this.servicoId = servicoId;
    }

    public LocalDateTime getDataLog() {
        return dataLog;
    }

    public void setDataLog(LocalDateTime dataLog) {
        this.dataLog = dataLog;
    }
}
