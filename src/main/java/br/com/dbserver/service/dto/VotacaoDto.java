package br.com.dbserver.service.dto;

import br.com.dbserver.model.Voto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VotacaoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    @NotNull
    private PautaDto pauta;
    @NotNull
    private AssociadoDto associado;
    @NotNull
    private Voto voto;
}
