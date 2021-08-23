package br.com.dbserver.builder;

import br.com.dbserver.model.Associado;
import br.com.dbserver.model.Pauta;
import br.com.dbserver.model.Votacao;
import br.com.dbserver.model.Voto;
import br.com.dbserver.repository.VotacaoRepository;
import br.com.dbserver.service.dto.VotacaoDto;
import br.com.dbserver.service.mapper.AssociadoMapper;
import br.com.dbserver.service.mapper.PautaMapper;
import br.com.dbserver.service.mapper.VotacaoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VotacaoBuilder {

    @Autowired
    private VotacaoRepository votacaoRepository;

    @Autowired
    private VotacaoMapper votacaoMapper;

    @Autowired
    private PautaMapper pautaMapper;

    @Autowired
    private AssociadoMapper associadoMapper;

    @Autowired
    private PautaBuilder pautaBuilder;

    @Autowired
    private AssociadoBuilder associadoBuilder;

    public VotacaoDto getVotacaoDto() {
        Votacao votacao = new Votacao();
        votacao.setPauta(this.getPauta());
        votacao.setAssociado(this.getAssociado());
        votacao.setVoto(Voto.S);
        return this.votacaoMapper.toDto(votacao);
    }

    private Pauta getPauta() {
        return this.pautaMapper.toEntity(this.pautaBuilder.persistir(this.pautaBuilder.getPautaDto()));
    }

    private Associado getAssociado() {
        return this.associadoMapper.toEntity(this.associadoBuilder.persistir(this.associadoBuilder.getAssociadoDto()));
    }

    public void excluir() {
        this.votacaoRepository.deleteAll();
        this.pautaBuilder.excluir();
        this.associadoBuilder.excluir();
    }
}
