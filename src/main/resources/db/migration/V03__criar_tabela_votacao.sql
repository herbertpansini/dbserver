CREATE TABLE votacao (
	id bigserial NOT NULL,
	pauta_id int8 NOT NULL,
	associado_id int8 NOT NULL,
    voto CHAR(1) NOT NULL,
	CONSTRAINT pauta_associado_uk UNIQUE (pauta_id, associado_id),
	CONSTRAINT votacao_pkey PRIMARY KEY (id)
);

ALTER TABLE votacao ADD CONSTRAINT fk_votacao_pauta_id      FOREIGN KEY (pauta_id)     REFERENCES pauta(id);
ALTER TABLE votacao ADD CONSTRAINT fk_votacao_associado_id  FOREIGN KEY (associado_id) REFERENCES associado(id);