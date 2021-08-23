package br.com.dbserver.service;

import br.com.dbserver.service.dto.PautaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PautaService {

    Page<PautaDto> findByDescricaoContainingIgnoreCase(String descricao, Pageable pageable);

    PautaDto findById(Long id);

    PautaDto save(PautaDto pautaDto);

    PautaDto update(Long id, PautaDto pautaDto);

    void abrirSessaoVotacao(Long id, Long minutos);

    void delete(Long id);
}
