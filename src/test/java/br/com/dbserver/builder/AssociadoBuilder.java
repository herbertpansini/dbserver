package br.com.dbserver.builder;

import br.com.dbserver.model.Associado;
import br.com.dbserver.repository.AssociadoRepository;
import br.com.dbserver.service.dto.AssociadoDto;
import br.com.dbserver.service.mapper.AssociadoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssociadoBuilder {

    @Autowired
    private AssociadoRepository associadoRepository;

    @Autowired
    private AssociadoMapper associadoMapper;

    public AssociadoDto getAssociadoDto() {
        Associado associado = new Associado();
        associado.setCpf("02479643018");
        associado.setNome("Haldor");
        return this.associadoMapper.toDto(associado);
    }

    public AssociadoDto persistir(AssociadoDto associadoDto) {
        return this.associadoMapper.toDto(this.associadoRepository.save(this.associadoMapper.toEntity(associadoDto)));
    }

    public void excluir() {
        this.associadoRepository.deleteAll();
    }
}
