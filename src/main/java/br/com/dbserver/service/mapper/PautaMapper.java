package br.com.dbserver.service.mapper;

import br.com.dbserver.model.Pauta;
import br.com.dbserver.service.dto.PautaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {SessaoMapper.class})
public interface PautaMapper extends EntityMapper<PautaDto, Pauta> {
}