package br.com.dbserver.controller;

import br.com.dbserver.service.PautaService;
import br.com.dbserver.service.dto.PautaDto;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/pauta")
@RequiredArgsConstructor
public class PautaController {

    private final Logger log = LoggerFactory.getLogger(PautaController.class);

    private final PautaService pautaService;

    @GetMapping
    @Timed
    public ResponseEntity<Page<PautaDto>> findByDescricaoContainingIgnoreCase(@RequestParam(value = "descricao", defaultValue = "") String descricao, Pageable pageable) {
        log.debug("Recuperar uma coleção de pautas : {}", descricao);
        return ResponseEntity.ok(this.pautaService.findByDescricaoContainingIgnoreCase(descricao, pageable));
    }

    @GetMapping("{id}")
    @Timed
    public ResponseEntity<PautaDto> findById(@PathVariable Long id) {
        log.debug("Recuperar uma pauta : {}", id);
        return ResponseEntity.ok(this.pautaService.findById(id));
    }

    @PostMapping
    @Timed
    public ResponseEntity<PautaDto> save(@Valid @RequestBody PautaDto pautaDto) {
        log.debug("Cadastrar uma nova pauta : {}", pautaDto);
        return new ResponseEntity<PautaDto>(this.pautaService.save(pautaDto), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @Timed
    public ResponseEntity<PautaDto> update(@PathVariable Long id, @Valid @RequestBody PautaDto pautaDto) {
        log.debug("Alterar uma pauta : {}", pautaDto);
        return ResponseEntity.ok(this.pautaService.update(id, pautaDto));
    }

    @PatchMapping("{id}")
    @Timed
    public ResponseEntity<Void> abrirSessaoVotacao(@PathVariable Long id, @RequestParam(value = "minutos", defaultValue = "1") Long minutos) {
        log.debug("Abrir uma sessão de votação em uma pauta : {}", id);
        this.pautaService.abrirSessaoVotacao(id, minutos);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("Excluir uma pauta : {}", id);
        this.pautaService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
