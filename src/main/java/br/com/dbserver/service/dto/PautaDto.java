package br.com.dbserver.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PautaDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotBlank
    private String descricao;

    @Embedded
    private SessaoDto sessao;
}
