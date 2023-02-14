CREATE DATABASE finance_db_v1;

CREATE SCHEMA financas;

CREATE TABLE financas.usuario
(
  id bigserial NOT NULL PRIMARY KEY,
  nome character varying(150),
  email character varying(100),
  senha character varying(20),
  ativo boolean not null default true,
  data_criacao timestamp not null default current_timestamp,
  data_atualizacao timestamp,
  version bigserial NOT NULL
);

CREATE TABLE financas.lancamento
(
  id bigserial NOT NULL PRIMARY KEY ,
  descricao character varying(100) NOT NULL,
  dia integer NOT NULL,
  mes integer NOT NULL,
  ano integer NOT NULL,
  valor numeric(16,2),
  tipo character varying(20),
  status character varying(20),
  id_usuario bigint REFERENCES financas2.usuario (id),
  data_criacao timestamp not null default current_timestamp,
  data_atualizacao TIMESTAMP,
  version bigserial NOT NULL
);