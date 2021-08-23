CREATE TABLE associado (
	id bigserial NOT NULL,
	nome VARCHAR(255) NOT NULL,
	cpf VARCHAR(11) NOT NULL,
	CONSTRAINT associado_cpf_uk UNIQUE (cpf),
	CONSTRAINT associado_pkey PRIMARY KEY (id)
);