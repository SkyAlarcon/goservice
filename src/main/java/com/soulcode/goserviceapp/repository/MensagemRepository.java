package com.soulcode.goserviceapp.repository;

import com.soulcode.goserviceapp.domain.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

    @Query(value =
            "SELECT m.*" +
            " FROM mensagens m" +
            " JOIN agendamentos a ON m.agendamento_id = a.id" +
            " WHERE a.id = ?" +
            " ORDER BY m.data_hora", nativeQuery = true)
    List<Mensagem> findMensagens(Long id);
}
