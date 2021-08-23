package br.com.dbserver.builder;

import br.com.dbserver.model.Pauta;
import br.com.dbserver.model.Sessao;
import br.com.dbserver.repository.PautaRepository;
import br.com.dbserver.service.dto.PautaDto;
import br.com.dbserver.service.mapper.PautaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PautaBuilder {

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private PautaMapper pautaMapper;

    public PautaDto getPautaDto() {
        Pauta pauta = new Pauta();
        pauta.setDescricao("Pauta 01");
        pauta.setSessao(this.getSessao());
        return this.pautaMapper.toDto(pauta);
    }

    private Sessao getSessao() {
        Sessao sessao = new Sessao();
        sessao.setDataInicio(LocalDateTime.now());
        sessao.setDataTermino(LocalDateTime.now().plusMinutes(1L));
        return sessao;
    }

    public PautaDto persistir(PautaDto pautaDto) {
        return this.pautaMapper.toDto(pautaRepository.save(this.pautaMapper.toEntity(pautaDto)));
    }

    public void excluir() {
        this.pautaRepository.deleteAll();
    }
}
