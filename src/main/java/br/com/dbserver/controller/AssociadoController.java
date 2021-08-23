package br.com.dbserver.controller;

import br.com.dbserver.service.AssociadoService;
import br.com.dbserver.service.dto.AssociadoDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/associado")
@RequiredArgsConstructor
public class AssociadoController {

    private final Logger log = LoggerFactory.getLogger(AssociadoController.class);

    private final AssociadoService associadoService;

    @GetMapping
    @Timed
    public ResponseEntity<Page<AssociadoDto>> findByDescricaoContainingIgnoreCase(@RequestParam(value = "nome", defaultValue = "") String nome, Pageable pageable) {
        log.debug("Recuperar uma coleção de associados : {}", nome);
        return ResponseEntity.ok(this.associadoService.findByNomeContainingIgnoreCase(nome, pageable));
    }

    @GetMapping("{id}")
    @Timed
    public ResponseEntity<AssociadoDto> findById(@PathVariable Long id) {
        log.debug("Recuperar uma associado : {}", id);
        return ResponseEntity.ok(this.associadoService.findById(id));
    }

    @PostMapping
    @Timed
    public ResponseEntity<AssociadoDto> save(@Valid @RequestBody AssociadoDto associadoDto) {
        log.debug("Cadastrar um novo associado : {}", associadoDto);
        return new ResponseEntity<AssociadoDto>(this.associadoService.save(associadoDto), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @Timed
    public ResponseEntity<AssociadoDto> update(@PathVariable Long id, @Valid @RequestBody AssociadoDto associadoDto) {
        log.debug("Alterar um associado : {}", associadoDto);
        return ResponseEntity.ok(this.associadoService.update(id, associadoDto));
    }

    @DeleteMapping("{id}")
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("Excluir um associado : {}", id);
        this.associadoService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}