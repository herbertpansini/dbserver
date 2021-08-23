package br.com.dbserver.service.mapper;

import br.com.dbserver.model.Associado;
import br.com.dbserver.service.dto.AssociadoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssociadoMapper extends EntityMapper<AssociadoDto, Associado> {

}
