package br.com.dbserver.repository;

import br.com.dbserver.model.Votacao;
import br.com.dbserver.service.dto.VotacaoPautaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VotacaoRepository extends JpaRepository<Votacao, Long> {

    Long countByPautaIdAndAssociadoId(Long idPauta, Long idAssociado);

    @Query("SELECT new br.com.dbserver.service.dto.VotacaoPautaDto( " +
            "p.id, " +
            "p.descricao, " +
            "COUNT(v.id)) " +
            "FROM Votacao v " +
            "INNER JOIN v.pauta p " +
            "WHERE p.id = :idPauta " +
            "AND v.voto = 'S' " +
            "GROUP BY p.id, " +
            "p.descricao")
    VotacaoPautaDto contabilizarVotos(@Param("idPauta") Long idPauta);

}
