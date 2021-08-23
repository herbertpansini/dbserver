package br.com.dbserver.service;

import br.com.dbserver.service.dto.AssociadoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AssociadoService {

    Page<AssociadoDto> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    AssociadoDto findById(Long id);

    AssociadoDto save(AssociadoDto associadoDto);

    AssociadoDto update(Long id, AssociadoDto associadoDto);

    void delete(Long id);
}
