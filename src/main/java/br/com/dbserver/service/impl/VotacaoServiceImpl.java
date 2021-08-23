package br.com.dbserver.service.impl;

import br.com.dbserver.model.Status;
import br.com.dbserver.model.ViaCpf;
import br.com.dbserver.repository.PautaRepository;
import br.com.dbserver.repository.VotacaoRepository;
import br.com.dbserver.service.VotacaoService;
import br.com.dbserver.service.client.ViaCpfClient;
import br.com.dbserver.service.dto.VotacaoDto;
import br.com.dbserver.service.dto.VotacaoPautaDto;
import br.com.dbserver.service.mapper.VotacaoMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@RequiredArgsConstructor
public class VotacaoServiceImpl implements VotacaoService {

    private final VotacaoRepository votacaoRepository;
    private final PautaRepository pautaRepository;
    private final VotacaoMapper votacaoMapper;

    private final ViaCpfClient viaCpfClient;

    @Override
    public VotacaoDto     save(VotacaoDto votacaoDto) {
        this.validate(votacaoDto);
        return this.votacaoMapper.toDto( this.votacaoRepository.save( this.votacaoMapper.toEntity(votacaoDto) ) );
    }

    @Override
    @Transactional(readOnly = true)
    public VotacaoPautaDto contabilizarVotos(Long idPauta) {
        return this.votacaoRepository.contabilizarVotos(idPauta);
    }

    private void validate(VotacaoDto votacaoDto) {
        if (this.verificarSessaoEncerrada(votacaoDto.getPauta().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Sessão para a pauta %d foi encerrada.", votacaoDto.getPauta().getId()));
        }

        if (!Status.ABLE_TO_VOTE.equals(this.verificarCpf(votacaoDto.getAssociado().getCpf()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("CPF %s inválido.", votacaoDto.getAssociado().getCpf()));
        }

        if (this.verificarAssociadoVotouPauta(votacaoDto.getPauta().getId(), votacaoDto.getAssociado().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Associado %d já votou na pauta %d.", votacaoDto.getAssociado().getId(), votacaoDto.getPauta().getId()));
        }
    }

    private boolean verificarSessaoEncerrada(Long idPauta) {
        return !this.pautaRepository.verificarSessaoEncerrada(idPauta);
    }

    private boolean verificarAssociadoVotouPauta(Long idPauta, Long idAssociado) {
        return this.votacaoRepository.countByPautaIdAndAssociadoId(idPauta, idAssociado) > 0;
    }

    private Status verificarCpf(String cpf) {
        try {
            ViaCpf viaCpf = this.viaCpfClient.consultarCpf(cpf);
            if (viaCpf != null)
                return viaCpf.getStatus();
            return null;
        } catch (FeignException e) {
            return null;
        }
    }
}
