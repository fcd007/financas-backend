package br.com.code7.financasbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.code7.financasbackend.model.entity.Lancamentos;

public interface LancamentosRepository extends JpaRepository<Lancamentos, Long> {

}
