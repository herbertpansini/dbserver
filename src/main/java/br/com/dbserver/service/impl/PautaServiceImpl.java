package br.com.dbserver.service.impl;

import br.com.dbserver.repository.PautaRepository;
import br.com.dbserver.service.PautaService;
import br.com.dbserver.service.dto.PautaDto;
import br.com.dbserver.service.dto.SessaoDto;
import br.com.dbserver.service.mapper.PautaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class PautaServiceImpl implements PautaService {

    private final PautaRepository pautaRepository;
    private final PautaMapper pautaMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<PautaDto> findByDescricaoContainingIgnoreCase(String descricao, Pageable pageable) {
        return this.pautaRepository.findByDescricaoContainingIgnoreCase(descricao, pageable).map(this.pautaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public PautaDto findById(Long id) {
        return this.pautaMapper.toDto(this.pautaRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NO_CONTENT, String.format("Pauta %d não encontrada", id))));
    }

    @Override
    public PautaDto save(PautaDto pautaDto) {
        return this.pautaMapper.toDto( this.pautaRepository.save( this.pautaMapper.toEntity(pautaDto) ) );
    }

    @Override
    public PautaDto update(Long id, PautaDto pautaDto) {
        PautaDto pautaUpdate = this.findById(id);
        BeanUtils.copyProperties(pautaDto, pautaUpdate, "id");
        return this.pautaMapper.toDto( this.pautaRepository.save( this.pautaMapper.toEntity(pautaUpdate) ) );
    }

    @Override
    public void abrirSessaoVotacao(Long id, Long minutos) {
        this.pautaRepository.abrirSessaoVotacao(id, new SessaoDto(LocalDateTime.now(), LocalDateTime.now().plusMinutes(minutos)));
    }

    @Override
    public void delete(Long id) {
        this.pautaRepository.deleteById(id);
    }
}
