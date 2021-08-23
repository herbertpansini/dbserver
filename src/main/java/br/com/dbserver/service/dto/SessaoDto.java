package br.com.dbserver.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessaoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDateTime dataInicio;
    private LocalDateTime dataTermino;
}
