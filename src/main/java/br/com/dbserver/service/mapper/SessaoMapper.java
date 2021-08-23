package br.com.dbserver.service.mapper;

import br.com.dbserver.model.Sessao;
import br.com.dbserver.service.dto.SessaoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SessaoMapper extends EntityMapper<SessaoDto, Sessao> {
}
