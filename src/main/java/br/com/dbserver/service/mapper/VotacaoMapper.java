package br.com.dbserver.service.mapper;

import br.com.dbserver.model.Votacao;
import br.com.dbserver.service.dto.VotacaoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PautaMapper.class, AssociadoMapper.class})
public interface VotacaoMapper extends EntityMapper<VotacaoDto, Votacao> {
}
