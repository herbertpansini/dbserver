package br.com.dbserver.service;

import br.com.dbserver.service.dto.VotacaoDto;
import br.com.dbserver.service.dto.VotacaoPautaDto;

public interface VotacaoService {

    VotacaoDto save(VotacaoDto votacaoDto);

    VotacaoPautaDto contabilizarVotos(Long idPauta);
}
