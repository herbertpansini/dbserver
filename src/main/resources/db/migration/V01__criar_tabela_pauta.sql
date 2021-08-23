CREATE TABLE pauta (
	id bigserial NOT NULL,
	descricao VARCHAR(255) NOT NULL,
	data_inicio TIMESTAMP NULL,
    data_termino TIMESTAMP NULL,
	CONSTRAINT pauta_pkey PRIMARY KEY (id)
);