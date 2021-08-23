package br.com.dbserver.repository;

import br.com.dbserver.model.Pauta;
import br.com.dbserver.service.dto.SessaoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

    Page<Pauta> findByDescricaoContainingIgnoreCase(String descricao, Pageable pageable);

    @Modifying
    @Query("UPDATE Pauta p " +
            "SET p.sessao.dataInicio  = :#{#sessao.dataInicio}, " +
            "    p.sessao.dataTermino = :#{#sessao.dataTermino} " +
            "WHERE p.id = :id")
    void abrirSessaoVotacao(@Param("id") Long id, @Param("sessao") SessaoDto sessao);

    @Query("SELECT CASE WHEN COUNT(p.id) > 0 THEN " +
            "TRUE " +
            "ELSE " +
            "FALSE " +
            "END " +
            "FROM Pauta p " +
            "WHERE p.id = :id " +
            "AND CURRENT_TIMESTAMP() BETWEEN p.sessao.dataInicio AND p.sessao.dataTermino")
    boolean verificarSessaoEncerrada(@Param("id") Long id);
}
