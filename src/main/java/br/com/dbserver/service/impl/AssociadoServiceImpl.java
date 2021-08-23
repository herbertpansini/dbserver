package br.com.dbserver.service.impl;

import br.com.dbserver.model.Associado;
import br.com.dbserver.repository.AssociadoRepository;
import br.com.dbserver.service.AssociadoService;
import br.com.dbserver.service.dto.AssociadoDto;
import br.com.dbserver.service.mapper.AssociadoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@RequiredArgsConstructor
public class AssociadoServiceImpl implements AssociadoService {

    private final AssociadoRepository associadoRepository;
    private final AssociadoMapper associadoMapper;

    @Override
    public Page<AssociadoDto> findByNomeContainingIgnoreCase(String nome, Pageable pageable) {
        return this.associadoRepository.findByNomeContainingIgnoreCase(nome, pageable).map(this.associadoMapper::toDto);
    }

    @Override
    public AssociadoDto findById(Long id) {
        return this.associadoMapper.toDto( this.associadoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NO_CONTENT, String.format("Associado %d não encontrado", id))) );
    }

    @Override
    public AssociadoDto save(AssociadoDto associadoDto) {
        this.validate(associadoDto);
        return this.associadoMapper.toDto( this.associadoRepository.save( this.associadoMapper.toEntity(associadoDto) ) );
    }

    @Override
    public AssociadoDto update(Long id, AssociadoDto associadoDto) {
        AssociadoDto associadoUpdate = this.findById(id);
        this.validate(associadoDto);
        BeanUtils.copyProperties(associadoDto, associadoUpdate, "id");
        return this.associadoMapper.toDto( this.associadoRepository.save( this.associadoMapper.toEntity(associadoUpdate) ) );
    }

    @Override
    public void delete(Long id) {
        this.associadoRepository.deleteById(id);
    }

    private void validate(AssociadoDto associadoDto) {
        if (this.validateCpf(associadoDto)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("CPF %s já cadastrado.", associadoDto.getCpf()));
        }
    }

    private boolean validateCpf(AssociadoDto associadoDto) {
        Associado associadoExist = this.associadoRepository.findByCpf(associadoDto.getCpf());
        return !(associadoExist == null || associadoExist.getId().equals(associadoDto.getId()));
    }
}