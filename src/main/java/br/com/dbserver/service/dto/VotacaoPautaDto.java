package br.com.dbserver.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VotacaoPautaDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String descricao;
    private Long votos;
}
