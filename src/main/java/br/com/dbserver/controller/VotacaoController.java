package br.com.dbserver.controller;

import br.com.dbserver.service.VotacaoService;
import br.com.dbserver.service.dto.VotacaoDto;
import br.com.dbserver.service.dto.VotacaoPautaDto;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/votacao")
@RequiredArgsConstructor
public class VotacaoController {

    private final Logger log = LoggerFactory.getLogger(VotacaoController.class);

    private final VotacaoService votacaoService;

    @PostMapping
    @Timed
    public ResponseEntity<VotacaoDto> save(@Valid @RequestBody VotacaoDto votacaoDto) {
        log.debug("Receber votos dos associados em pautas : {}", votacaoDto);
        return new ResponseEntity<VotacaoDto>(this.votacaoService.save(votacaoDto), HttpStatus.CREATED);
    }

    @GetMapping("/{idPauta}")
    @Timed
    public ResponseEntity<VotacaoPautaDto> contabilizarVotos(@PathVariable Long idPauta) {
        log.debug("Contabilizar os votos e retornar o resultado da votação na pauta : {}", idPauta);
        return ResponseEntity.ok(this.votacaoService.contabilizarVotos(idPauta));
    }
}
